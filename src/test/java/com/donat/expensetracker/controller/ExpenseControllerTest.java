package com.donat.expensetracker.controller;

import com.donat.expensetracker.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    ExpenseService expenseService;

    @Test
    void getExpenseById_returns404WhenNotFound () throws Exception{
        // Given: Category 999 will not be found
        when(expenseService.findById(999L)).thenReturn(Optional.empty());

        // When + Then
        mockMvc.perform(get("/api/expenses/999"))
                .andExpect(status().isNotFound());
    }

}
