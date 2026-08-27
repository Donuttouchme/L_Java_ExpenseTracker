package com.donat.expensetracker.controller;

import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.repository.ExpenseRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Expense> getExpenseById(@PathVariable Long id){
        return expenseRepository.findById(id);
    }
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense){
        return expenseRepository.save(expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id){
        expenseRepository.deleteById(id);
    }
}
