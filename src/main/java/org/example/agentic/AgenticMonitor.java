package org.example.agentic;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

class AgentContext {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private AgenticMonitor.Status status;
    private String lastAction;
    public AgenticMonitor.Status getStatus() { return status; }
    public void setStatus(AgenticMonitor.Status status) { this.status = status; }
    public String getLastAction() { return lastAction; }
    public void setLastAction(String lastAction) { this.lastAction = lastAction; this.lastAction += " at " + formatNow(); }
    public static String formatNow() {
        return java.time.LocalDateTime.now().format(DATE_FORMATTER);
    }
}

interface DecisionEngine {
    Action decide(String description, double amount, AgentContext context);
}

class ThresholdDecisionEngine implements DecisionEngine {
    @Override
    public Action decide(String description, double amount, AgentContext context) {
        if (amount > 100) {
            return new AlertAction(description, amount);
        } else {
            return new NoOpAction(description, amount);
        }
    }
}

interface Action {
    void execute(AgentContext context);
}

class AlertAction implements Action {
    private final String description;
    private final double amount;
    public AlertAction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
    @Override
    public void execute(AgentContext context) {
        context.setLastAction("ALERT: Expense '" + description + "' exceeds 100 (" + amount + ")  " );
        context.setStatus(AgenticMonitor.Status.ACTION_TAKEN);
    }
}

class NoOpAction implements Action {
    private final String description;
    private final double amount;
    public NoOpAction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
    @Override
    public void execute(AgentContext context) {
        context.setLastAction("NO_ALERT: Expense '" + description + "' is within limit (" + amount + ") " );
        context.setStatus(AgenticMonitor.Status.ACTION_TAKEN);
    }
}

@EnableAsync
@Service
public class AgenticMonitor {
    public enum Status { IDLE, RUNNING, ACTION_TAKEN, ERROR }

    private final AtomicReference<AgentContext> context = new AtomicReference<>(new AgentContext());
    private final DecisionEngine decisionEngine = new ThresholdDecisionEngine();

    public Status getStatus() {
        return context.get().getStatus();
    }
    public String getLastAction() {
        return context.get().getLastAction();
    }

    @Async
    public CompletableFuture<Void> monitorExpense(String description, double amount) {
        AgentContext ctx = new AgentContext();
        ctx.setStatus(Status.RUNNING);
        try {
            Action action = decisionEngine.decide(description, amount, ctx);
            action.execute(ctx);
        } catch (Exception e) {
            ctx.setStatus(Status.ERROR);
            ctx.setLastAction("ERROR: " + e.getMessage());
        }
        context.set(ctx);
        return CompletableFuture.completedFuture(null);
    }
}
