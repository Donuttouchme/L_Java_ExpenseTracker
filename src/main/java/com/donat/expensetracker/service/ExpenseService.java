package com.donat.expensetracker.service;

import com.donat.expensetracker.dto.CategorySummary;
import com.donat.expensetracker.dto.ExpenseRequest;
import com.donat.expensetracker.exception.CategoryNotFoundException;
import com.donat.expensetracker.model.Category;
import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.repository.CategoryRepository;
import com.donat.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository _expenseRepository, CategoryRepository _categoryRepository) {
        expenseRepository = _expenseRepository;
        categoryRepository = _categoryRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense createExpense(ExpenseRequest expenseRequest) {
        Category _category = categoryRepository.findById(expenseRequest.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(expenseRequest.getCategoryId()));
        Expense expense = new Expense(expenseRequest.getAmount(), expenseRequest.getDescription(), expenseRequest.getDate(), _category);
        return expenseRepository.save(expense);
    }

    public boolean existsById(Long id) {
        return expenseRepository.existsById(id);
    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategoryId(Long categoryId){
        return expenseRepository.findByCategoryId(categoryId);
    }

   public List<CategorySummary> getExpensesByCategory(){
        return expenseRepository.getCategorySummaries();
   }
}
