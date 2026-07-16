package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Category;
import com.example.ecommerce1.dto.CategoryRequest;
import com.example.ecommerce1.dto.CategoryResponse;
import com.example.ecommerce1.dto.ProductResponse;
import com.example.ecommerce1.repository.CategoryRepository;
import com.example.ecommerce1.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(CategoryRequest categoryRequest){
        categoryRepository.save(new Category(categoryRequest.nome()));
    }

    public CategoryResponse getProducts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado categoria na base de dados."));

        return new CategoryResponse(
                category.getNome(),
                category.getProducts().stream().map(
                        p -> new ProductResponse(
                                p.getNome(),
                                p.getDescricao(),
                                p.getPreco().doubleValue(),
                                p.getQuantidadeEstoque()
                        )
                ).toList()
        );
    }
}
