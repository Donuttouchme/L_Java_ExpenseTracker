package com.donat.expensetracker;

import com.donat.expensetracker.model.Category;
import com.donat.expensetracker.model.Expense;
import com.donat.expensetracker.model.User;
import com.donat.expensetracker.repository.CategoryRepository;
import com.donat.expensetracker.repository.ExpenseRepository;
import com.donat.expensetracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(CategoryRepository _categoryRepository, ExpenseRepository _expenseRepository, UserRepository _userRepository, PasswordEncoder _passwordEncoder) {
        categoryRepository = _categoryRepository;
        expenseRepository = _expenseRepository;
        userRepository = _userRepository;
        passwordEncoder = _passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Saving categories
        Category groceries = categoryRepository.findByName("Groceries")
                .orElseGet(() -> categoryRepository.save(new Category("Groceries")));
        Category subscriptions = categoryRepository.findByName("Subscriptions")
                .orElseGet(() -> categoryRepository.save(new Category("Subscriptions")));

        // Saving expenses
        if (expenseRepository.count() == 0) {
            expenseRepository.save(new Expense(new BigDecimal("42.5"), "Migros", LocalDate.now(), groceries));
            expenseRepository.save(new Expense(new BigDecimal("11.1"), "Coffee", LocalDate.of(2026, 8, 20), subscriptions));
        }

        if (userRepository.count() == 0) {
            userRepository.save(new User("test_user", passwordEncoder.encode("test_password"), "USER"));
        }
    }

}
