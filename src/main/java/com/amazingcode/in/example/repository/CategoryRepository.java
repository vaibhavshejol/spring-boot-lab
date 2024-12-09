package com.amazingcode.in.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazingcode.in.example.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByCategoryName(String categoryName);

}