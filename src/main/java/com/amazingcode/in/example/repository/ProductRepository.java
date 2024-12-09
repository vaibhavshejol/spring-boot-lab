package com.amazingcode.in.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazingcode.in.example.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByProductName(String productName);

}