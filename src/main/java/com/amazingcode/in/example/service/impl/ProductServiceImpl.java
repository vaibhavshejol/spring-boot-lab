package com.amazingcode.in.example.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    public ProductServiceImpl(ProductRepository productRepository) {
        LOG.info("Initializing ProductServiceImpl");
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        LOG.info("Attempting to create product with name: {}", productRequest.getProductName());
        boolean isProductPresent = productRepository.existsByProductName(productRequest.getProductName());
        if (isProductPresent) {
            LOG.warn("Product creation failed - product with name '{}' already exists", productRequest.getProductName());
            throw new AlreadyPresentException(ProductResponseMessage.PRODUCT_ALREADY_EXISTS.getMessage(productRequest.getProductName()));
        }
        Product product = productMapper.productRequestToProductEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        LOG.info("Successfully created product with ID: {}", savedProduct.getProductId());
        return productMapper.productEntityToProductResponse(savedProduct);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        LOG.info("Fetching products page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> productsPage = productRepository.findAll(pageable);
        if (productsPage.isEmpty()) {
            LOG.warn("No products found in the database");
            throw new NotPresentException(ProductResponseMessage.PRODUCTS_NOT_PRESENT.getMessage());
        }
        LOG.info("Retrieved {} products, total elements: {}", 
            productsPage.getNumberOfElements(), productsPage.getTotalElements());
        return productsPage.map(productMapper::productEntityToProductResponse);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        LOG.info("Attempting to fetch product with ID: {}", id);
        Optional<Product> existProduct = productRepository.findById(id);
        if (existProduct.isEmpty()) {
            LOG.warn("Product fetch failed - no product found with ID: {}", id);
            throw new NotPresentException(ProductResponseMessage.PRODUCT_NOT_FOUND.getMessage(id));
        }
        LOG.info("Successfully retrieved product: {}", existProduct.get().getProductName());
        return productMapper.productEntityToProductResponse(existProduct.get());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        LOG.info("Attempting to update product with ID: {}", id);
        Optional<Product> existProduct = productRepository.findById(id);
        if (existProduct.isEmpty()) {
            LOG.warn("Product update failed - no product found with ID: {}", id);
            throw new NotPresentException(ProductResponseMessage.PRODUCT_NOT_FOUND.getMessage(id));
        }
        Product product = productMapper.productRequestToProductEntity(productRequest);
        product.setProductId(id);
        Product updatedProduct = productRepository.save(product);
        LOG.info("Successfully updated product with ID: {}", id);
        return productMapper.productEntityToProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        LOG.info("Attempting to delete product with ID: {}", id);
        Optional<Product> existProduct = productRepository.findById(id);
        if (existProduct.isEmpty()) {
            LOG.warn("Product deletion failed - no product found with ID: {}", id);
            throw new NotPresentException(ProductResponseMessage.PRODUCT_NOT_FOUND.getMessage(id));
        }
        productRepository.deleteById(id);
        LOG.info("Successfully deleted product with ID: {}", id);
    }
}
