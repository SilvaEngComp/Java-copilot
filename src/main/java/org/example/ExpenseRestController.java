package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {
    @Autowired
    private ExpenseTrackerService service;
    @Autowired
    private org.example.agentic.AgenticMonitor agenticMonitor;

    @GetMapping
    public List<Expense> getExpenses() {
        return service.getExpenses();
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        // Call the agentic monitor asynchronously when adding an expense
        agenticMonitor.monitorExpense(expense.getDescription(), expense.getAmount());
        return service.addExpense(expense);
    }

    @GetMapping("/total")
    public double getTotal() {
        return service.getTotal();
    }

    @DeleteMapping
    public void clearExpenses() {
        service.clear();
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return service.updateExpense(id, expense);
    }
}
