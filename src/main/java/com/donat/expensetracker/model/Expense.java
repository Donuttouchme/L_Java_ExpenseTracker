package com.donat.expensetracker.model;

import jakarta.persistence.*;

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

    @ManyToOne
    private Category category;

    public Expense(){}

    public Expense(BigDecimal _amount, String _description, LocalDate _date, Category _category){
        amount = _amount;
        description = _description;
        date = _date;
        category = _category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long _id){
        id = _id;
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

    public void setCategory(Category _category){
        category = _category;
    }

    public Category getCategory(){
        return category;
    }
}
