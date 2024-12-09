package com.amazingcode.in.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.amazingcode.in.example.request.CategoryRequest;
import com.amazingcode.in.example.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    Page<CategoryResponse> getAllCategory(Pageable pageable);
    CategoryResponse getCategory(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
}