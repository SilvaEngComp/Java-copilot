package org.example.agentic;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@EnableAsync
@Service
public class AgenticMonitor {
    public enum Status { IDLE, RUNNING, ACTION_TAKEN, ERROR }

    private final AtomicReference<Status> status = new AtomicReference<>(Status.IDLE);
    private final AtomicReference<String> lastAction = new AtomicReference<>("");

    public Status getStatus() {
        return status.get();
    }
    public String getLastAction() {
        return lastAction.get();
    }

    @Async
    public CompletableFuture<Void> monitorInput(String input) {
        status.set(Status.RUNNING);
        try {
            // Simulate decision logic
            if (input != null && input.toLowerCase().contains("alert")) {
                performAction("ALERT_TRIGGERED");
            } else {
                performAction("NO_ACTION");
            }
            status.set(Status.ACTION_TAKEN);
        } catch (Exception e) {
            status.set(Status.ERROR);
            lastAction.set("ERROR: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    private void performAction(String action) {
        // Simulate an action (could be API call, DB update, etc.)
        lastAction.set(action + " at " + java.time.LocalDateTime.now());
        // Add real action logic here
    }
}

