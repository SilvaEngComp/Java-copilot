package org.example;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTracker {
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(String description, double amount) {
        expenses.add(new Expense(description, amount));
    }

    public double getTotal() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public void listExpenses() {
        for (Expense e : expenses) {
            System.out.println(e.getDescription() + ": R$" + e.getAmount());
        }
    }
}

