package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.CategoryRequest;
import com.example.ecommerce1.dto.CategoryResponse;
import com.example.ecommerce1.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.createCategory(categoryRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CategoryResponse> getProductsByCategory(@RequestParam Long categoryId){
        CategoryResponse products = categoryService.getProducts(categoryId);
        return ResponseEntity.ok(products);
    }

}
