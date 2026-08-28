package com.donat.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate date;
    @NotNull
    private Long categoryId;

    public ExpenseRequest(BigDecimal _amount, String _description, LocalDate _date, Long _categoryId){
        amount = _amount;
        description = _description;
        date = _date;
        categoryId = _categoryId;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal _amount){
        amount = _amount;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String _description){
        description = _description;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate _date){
        date = _date;
    }

    public Long getCategoryId(){
        return categoryId;
    }
    public void setCategoryId(Long _categoryId){
        categoryId = _categoryId;
    }
}
