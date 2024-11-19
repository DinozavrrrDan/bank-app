package com.bankapp.unit;

import com.bankapp.enteties.Category;
import com.bankapp.repository.CategoryRepository;
import com.bankapp.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void testSaveCategory_ShouldSaveAndReturnCategory() {
        Category categoryToSave = new Category();
        Category savedCategory = new Category();
        savedCategory.setId(1L);

        when(categoryRepository.save(categoryToSave)).thenReturn(savedCategory);

        Category result = categoryService.saveCategory(categoryToSave);

        assertThat(result)
                .as("Тело ответа при создании категории не равно ожидаемому")
                .isEqualTo(savedCategory);
        verify(categoryRepository, times(1)).save(categoryToSave);
    }

    @Test
    void testDeleteCategory_ShouldDeleteCategory() {
        Long categoryId = 1L;
        categoryService.deleteCategory(String.valueOf(categoryId));

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testGetAllCategories_ShouldReturnListOfCategories() {
        Long userId = 1L;
        List<Category> mockCategories = Arrays.asList(new Category().setDescription("123"), new Category());
        when(categoryRepository.findAllByUserId(userId)).thenReturn(mockCategories);

        List<Category> result = categoryService.getAllCategories(userId);

        assertThat(result)
                .as("Полученные категории не соответствуют ожидаемым")
                .isEqualTo(mockCategories);
        verify(categoryRepository, times(1)).findAllByUserId(userId);
    }
}
