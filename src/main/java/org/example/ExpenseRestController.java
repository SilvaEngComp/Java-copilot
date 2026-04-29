package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {
    @Autowired
    private ExpenseTrackerService service;

    @GetMapping
    public List<Expense> getExpenses() {
        return service.getExpenses();
    }

    @PostMapping
    public void addExpense(@RequestBody Expense expense) {
        service.addExpense(expense);
    }

    @GetMapping("/total")
    public double getTotal() {
        return service.getTotal();
    }

    @DeleteMapping
    public void clearExpenses() {
        service.clear();
    }
}

