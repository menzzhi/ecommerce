package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Category;
import com.example.ecommerce1.domain.Product;
import com.example.ecommerce1.dto.ProductRequest;
import com.example.ecommerce1.repository.CategoryRepository;
import com.example.ecommerce1.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.categoriaId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Product product = new Product(
                category,
                productRequest.nome(),
                productRequest.descricao(),
                productRequest.preco(),
                productRequest.quantidadeEstoque(),
                productRequest.ativo()
        );

        productRepository.save(product);
    }
}
