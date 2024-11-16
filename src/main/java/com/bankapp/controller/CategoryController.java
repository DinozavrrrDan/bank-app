package com.bankapp.controller;

import com.bankapp.enteties.Account;
import com.bankapp.enteties.Category;
import com.bankapp.service.AccountService;
import com.bankapp.service.AuthService;
import com.bankapp.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api")
public class CategoryController {

    private final CategoryService categoryService;
    private final AccountService accountService;
    private final AuthService authService;

    public CategoryController(CategoryService categoryService, AccountService accountService, AuthService authService) {
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.authService = authService;
    }

    @GetMapping("category")
    public ResponseEntity<List<Category>> getCategories() {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        return new ResponseEntity<>(categoryService.getAllCategories(account.getId()), HttpStatus.OK);
    }

    @PostMapping("category")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Account account = accountService.findByLogin(authService.getAuthInfo().getPrincipal().toString());
        category.setUserId(account.getId());
        category.setDateAdded(LocalDate.now());
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}