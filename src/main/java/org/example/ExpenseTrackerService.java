package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Expense updateExpense(Long id, Expense updated) {
        Optional<Expense> opt = repository.findById(id);
        if (opt.isPresent()) {
            Expense exp = opt.get();
            exp.setDescription(updated.getDescription());
            exp.setAmount(updated.getAmount());
            return repository.save(exp);
        }
        return null;
    }
}
