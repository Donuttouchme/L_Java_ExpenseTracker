package com.donat.expensetracker.service;

import com.donat.expensetracker.dto.ExpenseRequest;
import com.donat.expensetracker.exception.CategoryNotFoundException;
import com.donat.expensetracker.model.Category;
import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.repository.CategoryRepository;
import com.donat.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void createExpense_throwsWhenCategoryNotFound(){
        // Given: Category 999 does not exist
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        ExpenseRequest request = new ExpenseRequest();
        request.setCategoryId(999L);

        // Then: creating will throw an exception
        assertThrows(CategoryNotFoundException.class,()-> expenseService.createExpense(request));

    }

    @Test
    void existsById_returnsTrueWhenRepositoryReturnsTrue(){
        // Given: Category id 1 will return true
        when(expenseRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = expenseService.existsById(1L);

        // Then: id 1 will return true
        assertTrue(result);
    }

    @Test
    void createExpense_doesServiceRequestsRepositoryWhenCorrect(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category("Test")));

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setCategoryId(1L);

        expenseService.createExpense(expenseRequest);

        verify(expenseRepository).save(any(Expense.class));
    }

    @Test
    void createExpense_mapsRequestFieldsToExpense(){
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category("Food")));

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setCategoryId(1L);
        expenseRequest.setAmount(new BigDecimal(20));
        expenseRequest.setDate(LocalDate.of(2026,7,6));
        expenseRequest.setDescription("Tuna");

        expenseService.createExpense(expenseRequest);

        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);
        verify(expenseRepository).save(captor.capture());
        Expense saved = captor.getValue();
        assertEquals(expenseRequest.getAmount(), saved.getAmount());
        assertEquals(expenseRequest.getDescription(), saved.getDescription());
        assertEquals(expenseRequest.getDate(), saved.getDate());
        assertEquals("Food", saved.getCategory().getName());
    }

    // Delegation check
    void deleteById_checkingDelegation(){
        expenseService.deleteById(1L);

        verify(expenseRepository).deleteById(1L);
    }

    void findById_checkingDelegation(){
        Expense expense = new Expense();
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        Optional<Expense> result =expenseService.findById(1L);

        assertEquals(Optional.of(expense), result);
    }

}
