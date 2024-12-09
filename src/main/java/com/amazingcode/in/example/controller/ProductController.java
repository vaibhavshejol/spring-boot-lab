package com.amazingcode.in.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amazingcode.in.example.constant.enums.ProductResponseMessage;
import com.amazingcode.in.example.request.ProductRequest;
import com.amazingcode.in.example.response.ProductResponse;
import com.amazingcode.in.example.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        LOG.info("Initializing ProductController");
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        LOG.info("Received request to create product: {}", productRequest);
        ProductResponse createdProduct = productService.createProduct(productRequest);
        LOG.info("Successfully created product with ID: {}", createdProduct.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        LOG.info("Fetching products page={}, size={}", page, size);
        Page<ProductResponse> products = productService.getAllProducts(PageRequest.of(page, size));
        LOG.info("Retrieved {} products, total elements: {}", products.getNumberOfElements(), products.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        LOG.info("Fetching product with ID: {}", id);
        ProductResponse product = productService.getProduct(id);
        LOG.info("Successfully retrieved product: {}", product);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody ProductRequest productRequest) {
        LOG.info("Updating product with ID: {}", id);
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        LOG.info("Successfully updated product: {}", updatedProduct);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        LOG.info("Attempting to delete product with ID: {}", id);
        productService.deleteProduct(id);
        LOG.info("Successfully deleted product with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ProductResponseMessage.PRODUCT_SUCCESSFULLY_DELETED.getMessage(id));
    }
}
