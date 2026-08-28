package com.donat.expensetracker;

import com.donat.expensetracker.model.Category;
import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.repository.CategoryRepository;
import com.donat.expensetracker.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    public DataSeeder(CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) {
        // Saving categories
        Category groceries = new Category("Groceries");
        Category subscriptions = new Category("Subscriptions");

        categoryRepository.save(groceries);
        categoryRepository.save(subscriptions);

        // Saving expenses
        expenseRepository.save(new Expense(new BigDecimal("42.5"), "Migros", LocalDate.now(), groceries));
        expenseRepository.save(new Expense(new BigDecimal("11.1"), "Coffee", LocalDate.of(2026, 8, 20), subscriptions));

        // Reading back
        System.out.println("=== CATEGORIES ===");
        categoryRepository.findAll().forEach(category -> System.out.println(category.getId() + " " + category.getName()));

        System.out.println("=== EXPENSES ===");
        expenseRepository.findAll().forEach(expense -> System.out.println(expense.getAmount() + " " + expense.getDescription() + " " + expense.getDate()));
    }

}
