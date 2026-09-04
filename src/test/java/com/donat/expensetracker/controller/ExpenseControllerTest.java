package com.donat.expensetracker.controller;

import com.donat.expensetracker.dto.ExpenseRequest;
import com.donat.expensetracker.exception.CategoryNotFoundException;
import com.donat.expensetracker.model.Category;
import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.service.ExpenseService;
import com.donat.expensetracker.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    ExpenseService expenseService;

    @MockitoBean
    JwtService jwtService;

    @Test
    void getExpenseById_returns404WhenNotFound () throws Exception{
        // Given: Category 999 will not be found
        when(expenseService.findById(999L)).thenReturn(Optional.empty());

        // When + Then
        mockMvc.perform(get("/api/expenses/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createExpense_returns400WhenWrongCategoryId() throws Exception{
        // Given: Category id 999 is non-existent
        when(expenseService.createExpense(any(ExpenseRequest.class))).thenThrow(new CategoryNotFoundException(999L));

        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"amount\": 25.00, \"description\": \"Ghost\", \"date\": \"2026-08-28\", \"categoryId\": 999 }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createExpense_returns201WhenGoodCategoryId() throws Exception{
        when(expenseService.createExpense(any(ExpenseRequest.class))).thenReturn(
                new Expense(new BigDecimal("25.00"), "Ghost", LocalDate.of(2026,8,28),new Category("Food")));

        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"amount\": 25.00, \"description\": \"Ghost\", \"date\": \"2026-08-28\", \"categoryId\": 1 }"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{ \"amount\": 25.00, \"description\": \"Ghost\", \"date\": \"2026-08-28\" }"));
    }

    @Test
    void deleteExpense_existsByIdReturns204()throws Exception{
        when(expenseService.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/expenses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteExpense_notExistsByIdReturns404()throws Exception{
        when(expenseService.existsById(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/expenses/999"))
                .andExpect(status().isNotFound());
        verify(expenseService, never()).deleteById(anyLong());
    }

    @Test
    void getAllExpenses_withoutParameterCallsGetAllExpenses() throws Exception{
        mockMvc.perform(get("/api/expenses"))
                        .andExpect(status().isOk());

        verify(expenseService).getAllExpenses();
        verify(expenseService, never()).getExpensesByCategoryId(anyLong());
    }

    @Test
    void getAllExpenses_withParameterCallsGetExpensesById() throws Exception{
        mockMvc.perform(get("/api/expenses").param("categoryId", "1"))
                .andExpect(status().isOk());

        verify(expenseService).getExpensesByCategoryId(1L);
        verify(expenseService, never()).getAllExpenses();
    }

    @Test
    void getExpenseById_returns200WhenFound()throws Exception{
        when(expenseService.findById(1L)).thenReturn(Optional.of(
                new Expense(new BigDecimal("25.00"), "Ghost", LocalDate.of(2026, 8, 28), new Category("Food"))));
        mockMvc.perform(get("/api/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"amount\": 25.00, \"description\": \"Ghost\", \"date\": \"2026-08-28\" }"));
    }
}
