package com.example.ecommerce1.repository;

import com.example.ecommerce1.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
