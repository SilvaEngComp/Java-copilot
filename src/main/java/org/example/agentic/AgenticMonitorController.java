package org.example.agentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agentic")
public class AgenticMonitorController {
    @Autowired
    private AgenticMonitor monitor;

    @PostMapping("/monitor")
    public String monitor(@RequestParam String input) {
        monitor.monitorInput(input);
        return "Monitoring started for input: " + input;
    }

    @GetMapping("/status")
    public String status() {
        return "Status: " + monitor.getStatus() + ", Last Action: " + monitor.getLastAction();
    }
}

