package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.ProductRequest;
import com.example.ecommerce1.dto.ProductResponse;
import com.example.ecommerce1.dto.ProductUpdate;
import com.example.ecommerce1.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return ResponseEntity.ok("Seu produto foi criado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam int page,
                                                                @RequestParam int items){
        Page<ProductResponse> allProducts = productService.getAllProducts(page, items);
        return ResponseEntity.ok(allProducts);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Seu produto foi deletado com sucesso!");
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> updateProductInformation(@RequestBody ProductUpdate productUpdate,
                                                           @RequestParam Long productId){
        productService.updateProduct(productId, productUpdate);
        return ResponseEntity.ok("Seu produto foi atualizado com sucesso!");
    }
}
