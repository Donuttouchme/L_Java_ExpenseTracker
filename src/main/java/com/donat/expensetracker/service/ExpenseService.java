package com.donat.expensetracker.service;

import com.donat.expensetracker.dto.ExpenseRequest;
import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService (ExpenseRepository _expenseRepository){
        expenseRepository = _expenseRepository;
    }

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Optional<Expense> findById(Long id){
        return expenseRepository.findById(id);
    }

    public Expense createExpense(ExpenseRequest expenseRequest){
        Expense expense = new Expense(expenseRequest.getAmount(),expenseRequest.getDescription(),expenseRequest.getDate());
        return expenseRepository.save(expense);
    }

    public boolean existsById (Long id){
        return expenseRepository.existsById(id);
    }

    public void deleteById(Long id){
        expenseRepository.deleteById(id);
    }
}
