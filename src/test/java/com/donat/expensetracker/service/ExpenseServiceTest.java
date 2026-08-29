package com.donat.expensetracker.service;

import com.donat.expensetracker.dto.ExpenseRequest;
import com.donat.expensetracker.exception.CategoryNotFoundException;
import com.donat.expensetracker.repository.CategoryRepository;
import com.donat.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        when(expenseService.existsById(1L)).thenReturn(true);

        // When
        boolean result = expenseService.existsById(1L);

        // Then: id 1 will return true
        assertTrue(result);
    }

}
