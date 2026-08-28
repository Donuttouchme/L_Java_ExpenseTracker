package com.donat.expensetracker.repository;

import com.donat.expensetracker.dto.CategorySummary;
import com.donat.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    List<Expense> findByCategoryId(Long categoryId);
    @Query("SELECT new com.donat.expensetracker.dto.CategorySummary(e.category.name, SUM(e.amount)) " + "FROM Expense e GROUP BY e.category.name")
    List<CategorySummary> getCategorySummaries();
}
