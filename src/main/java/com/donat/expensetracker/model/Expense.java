package com.donat.expensetracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String description;
    private LocalDate date;

    public Expense(){}

    public Expense(BigDecimal amount, String description, LocalDate date){
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }
}
