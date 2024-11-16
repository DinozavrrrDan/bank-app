package com.bankapp.service.impl;

import com.bankapp.enteties.Category;
import com.bankapp.repository.CategoryRepository;
import com.bankapp.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(Long.getLong(id));
    }

    @Override
    public List<Category> getAllCategories(Long userId) {
        return categoryRepository.findAllByUserId(userId);
    }
}
