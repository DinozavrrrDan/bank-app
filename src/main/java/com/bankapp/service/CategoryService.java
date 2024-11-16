package com.bankapp.service;

import com.bankapp.enteties.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category saveCategory(Category category);
    void deleteCategory(String id);
    List<Category> getAllCategories(Long userId);
}
