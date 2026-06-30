package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Category;
import com.example.ecommerce1.dto.CategoryRequest;
import com.example.ecommerce1.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(CategoryRequest categoryRequest){
        Category category = new Category(categoryRequest.nome());
        categoryRepository.save(category);
    }
}
