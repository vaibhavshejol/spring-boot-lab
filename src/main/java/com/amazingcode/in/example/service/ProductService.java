package com.amazingcode.in.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.amazingcode.in.example.request.ProductRequest;
import com.amazingcode.in.example.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    Page<ProductResponse> getAllProducts(Pageable pageable);
    ProductResponse getProduct(Long id);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
}