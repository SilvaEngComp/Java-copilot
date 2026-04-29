package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExpenseController {
    @Autowired
    private ExpenseTrackerService service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("expenses", service.getExpenses());
        model.addAttribute("total", service.getTotal());
        return "index";
    }

    @PostMapping("/add")
    public String addExpense(@RequestParam String description, @RequestParam double amount) {
        service.addExpense(new Expense(description, amount));
        return "redirect:/";
    }
}

