package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.Category;
import com.example.ecommerce1.domain.Product;
import com.example.ecommerce1.dto.ProductRequest;
import com.example.ecommerce1.dto.ProductResponse;
import com.example.ecommerce1.dto.ProductUpdate;
import com.example.ecommerce1.repository.CategoryRepository;
import com.example.ecommerce1.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

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
                BigDecimal.valueOf(productRequest.preco()),
                productRequest.quantidadeEstoque(),
                productRequest.ativo()
        );

        productRepository.save(product);
    }

    public Page<ProductResponse> getAllProducts(int page, int items) {
        return productRepository.findAll(PageRequest.of(page, items)).map(p -> new ProductResponse(
                p.getNome(),
                p.getDescricao(),
                p.getPreco().doubleValue(),
                p.getQuantidadeEstoque())
        );
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        productRepository.delete(product);
    }

    public void updateProduct(Long productId, ProductUpdate productUpdate) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!productUpdate.nome().isEmpty()){
            product.setNome(productUpdate.nome());
        }

        if (!productUpdate.descricao().isEmpty()){
            product.setDescricao(productUpdate.descricao());
        }

        if (productUpdate.preco() != null){
            product.setPreco(BigDecimal.valueOf(productUpdate.preco()));
        }

        if (productUpdate.quantidadeEstoque() != null){
            product.setQuantidadeEstoque(productUpdate.quantidadeEstoque());
        }

        productRepository.save(product);
    }
}
