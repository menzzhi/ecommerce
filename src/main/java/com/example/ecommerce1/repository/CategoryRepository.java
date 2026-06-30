package com.example.ecommerce1.repository;

import com.example.ecommerce1.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
