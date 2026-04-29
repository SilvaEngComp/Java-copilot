package org.example.agentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agentic")
public class AgenticMonitorController {
    @Autowired
    private AgenticMonitor monitor;

    // New endpoint for monitoring an expense
    @PostMapping("/monitor-expense")
    public String monitorExpense(@RequestParam String description, @RequestParam double amount) {
        monitor.monitorExpense(description, amount);
        return "Monitoring started for expense: '" + description + "' with amount: " + amount;
    }


    @GetMapping("/status")
    public String status() {
        return "Status: " + monitor.getStatus() + ", Last Action: " + monitor.getLastAction();
    }
}
