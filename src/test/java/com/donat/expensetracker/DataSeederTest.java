package com.donat.expensetracker;

import com.donat.expensetracker.repository.CategoryRepository;
import com.donat.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataSeederTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DataSeeder dataSeeder;

    @Test
    void run_doesNotSeedWhenDataAlreadyExists(){
        when(categoryRepository.count()).thenReturn(2L);
        when(expenseRepository.count()).thenReturn(2L);

        dataSeeder.run();

        verify(categoryRepository, never()).save(any());
        verify(expenseRepository, never()).save(any());
    }
}
