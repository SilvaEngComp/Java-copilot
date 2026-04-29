package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTrackerService {
    @Autowired
    private ExpenseRepository repository;

    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    public List<Expense> getExpenses() {
        return repository.findAll();
    }

    public double getTotal() {
        return repository.findAll().stream().mapToDouble(Expense::getAmount).sum();
    }

    public void clear() {
        repository.deleteAll();
    }
}
