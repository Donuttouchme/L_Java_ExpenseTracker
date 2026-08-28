package com.donat.expensetracker.controller;

import com.donat.expensetracker.dto.CategorySummary;
import com.donat.expensetracker.dto.ExpenseRequest;
import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService _expenseService){
        expenseService = _expenseService;
    }

    @GetMapping
    public List<Expense> getAllExpenses(@RequestParam(required = false) Long categoryId){
        if (categoryId == null){
            return expenseService.getAllExpenses();
        }
        else{
            return expenseService.getExpensesByCategoryId(categoryId);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById( @PathVariable Long id){
        return expenseService.findById(id)
                .map(expense -> ResponseEntity.ok(expense))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest){
        Expense saved = expenseService.createExpense(expenseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        if (expenseService.existsById(id)) {
            expenseService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/summary")
    public List<CategorySummary> getCategorySummery(){
        return expenseService.getExpensesByCategory();
    }

}
