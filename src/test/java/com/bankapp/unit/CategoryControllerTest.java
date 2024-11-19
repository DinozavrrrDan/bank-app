package com.bankapp.unit;

import com.bankapp.controller.CategoryController;
import com.bankapp.enteties.Account;
import com.bankapp.enteties.Category;
import com.bankapp.enteties.jwt.JwtAuthentication;
import com.bankapp.service.AccountService;
import com.bankapp.service.AuthService;
import com.bankapp.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AccountService accountService;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryController = new CategoryController(categoryService, accountService, authService);
    }

    @Test
    void testGetCategories_ShouldReturnCategories() {
        Account mockAccount = new Account();
        mockAccount.setId(1L);

        List<Category> mockCategories = Arrays.asList(new Category(), new Category());

        JwtAuthentication mockAuth = mock(JwtAuthentication.class);
        when(mockAuth.getPrincipal()).thenReturn("user");

        when(authService.getAuthInfo()).thenReturn(mockAuth);
        when(accountService.findByLogin("user")).thenReturn(mockAccount);
        when(categoryService.getAllCategories(1L)).thenReturn(mockCategories);

        ResponseEntity<List<Category>> response = categoryController.getCategories();

        assertThat(response.getStatusCode())
                .as("Получили неверный статус код при получении объектов (ожидали 200)")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .as("Тело ответа при получении категорий не равно ожидаемому")
                .isEqualTo(mockCategories);
        verify(accountService, times(1)).findByLogin("user");
        verify(categoryService, times(1)).getAllCategories(1L);
    }

    @Test
    void testSaveCategory_ShouldSaveAndReturnCategory() {
        Account mockAccount = new Account();
        mockAccount.setId(1L);

        Category categoryToSave = new Category();
        categoryToSave.setDescription("Test Category");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setUserId(1L);
        savedCategory.setDescription("Test Category");
        savedCategory.setDateAdded(LocalDate.now());

        JwtAuthentication mockAuth = mock(JwtAuthentication.class);
        when(mockAuth.getPrincipal()).thenReturn("user");

        when(authService.getAuthInfo()).thenReturn(mockAuth);
        when(accountService.findByLogin("user")).thenReturn(mockAccount);
        when(categoryService.saveCategory(categoryToSave)).thenReturn(savedCategory);

        ResponseEntity<Category> response = categoryController.saveCategory(categoryToSave);

        assertThat(response.getStatusCode())
                .as("Получили неверный статус код при сохранении объекта (ожидали 201)")
                .isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody())
                .as("Тело ответа при создании категории не равно ожидаемому")
                .isEqualTo(savedCategory);
        verify(categoryService, times(1)).saveCategory(categoryToSave);
    }

    @Test
    void testDeleteCategory_ShouldDeleteCategory() {
        String categoryId = "1";

        ResponseEntity<?> response = categoryController.deleteCategory(categoryId);

        assertThat(response.getStatusCode())
                .as("Неверный статус код (ожидали 200)")
                .isEqualTo(HttpStatus.OK);
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}
