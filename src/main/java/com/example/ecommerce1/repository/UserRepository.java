package com.example.ecommerce1.repository;

import com.example.ecommerce1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
