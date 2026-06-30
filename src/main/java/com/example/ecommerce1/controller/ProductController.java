package com.example.ecommerce1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(){

        return ResponseEntity.noContent().build();
    }

}
