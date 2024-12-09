package com.amazingcode.in.example.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.amazingcode.in.example.constant.enums.ProductResponseMessage;
import com.amazingcode.in.example.entity.Product;
import com.amazingcode.in.example.exception.AlreadyPresentException;
import com.amazingcode.in.example.exception.NotPresentException;
import com.amazingcode.in.example.mapper.ProductMapper;
import com.amazingcode.in.example.repository.ProductRepository;
import com.amazingcode.in.example.request.ProductRequest;
import com.amazingcode.in.example.response.ProductResponse;
import com.amazingcode.in.example.service.ProductService;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        boolean isProductPresent = productRepository.existsByProductName(productRequest.getProductName());
        if (isProductPresent) {
            throw new AlreadyPresentException(ProductResponseMessage.PRODUCT_ALREADY_EXISTS.getMessage(productRequest.getProductName()));
        }
        Product product = productMapper.productRequestToProductEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        return productMapper.productEntityToProductResponse(savedProduct);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        if (productsPage.isEmpty()) {
            throw new NotPresentException(ProductResponseMessage.PRODUCTS_NOT_PRESENT.getMessage());
        }
        return productsPage.map(productMapper::productEntityToProductResponse);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Optional<Product> existProduct = productRepository.findById(id);
        if (existProduct.isEmpty()) {
            throw new NotPresentException(ProductResponseMessage.PRODUCT_NOT_FOUND.getMessage(id));
        }
        return productMapper.productEntityToProductResponse(existProduct.get());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> existProduct = productRepository.findById(id);
        if (existProduct.isEmpty()) {
            throw new NotPresentException(ProductResponseMessage.PRODUCT_NOT_FOUND.getMessage(id));
        }
        Product product = productMapper.productRequestToProductEntity(productRequest);
        product.setProductId(id);
        Product updatedProduct = productRepository.save(product);
        return productMapper.productEntityToProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> existProduct = productRepository.findById(id);
        if (existProduct.isEmpty()) {
            throw new NotPresentException(ProductResponseMessage.PRODUCT_NOT_FOUND.getMessage(id));
        }
        productRepository.deleteById(id);
    }
}
