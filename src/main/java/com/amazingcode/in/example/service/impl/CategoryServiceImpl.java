package com.amazingcode.in.example.service.impl;

import com.amazingcode.in.example.constant.enums.CategoryResponseMessage;
import com.amazingcode.in.example.entity.Category;
import com.amazingcode.in.example.exception.AlreadyPresentException;
import com.amazingcode.in.example.exception.NotPresentException;
import com.amazingcode.in.example.mapper.CategoryMapper;
import com.amazingcode.in.example.repository.CategoryRepository;
import com.amazingcode.in.example.request.CategoryRequest;
import com.amazingcode.in.example.response.CategoryResponse;
import com.amazingcode.in.example.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private static final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        LOG.info("Initializing CategoryServiceImpl");
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        LOG.info("Attempting to create category with name: {}", categoryRequest.getCategoryName());
        boolean isCategoryPresent = categoryRepository.existsByCategoryName(categoryRequest.getCategoryName());
        if (isCategoryPresent) {
            LOG.warn("Category creation failed - category with name '{}' already exists", categoryRequest.getCategoryName());
            throw new AlreadyPresentException(CategoryResponseMessage.CATEGORY_ALREADY_EXISTS.getMessage(categoryRequest.getCategoryName()));
        }

        Category category = categoryMapper.categoryRequestToCategoryEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        LOG.info("Successfully created category with ID: {}", savedCategory.getCategoryId());

        return categoryMapper.categoryEntityToCategoryResponse(savedCategory);
    }

    @Override
    public Page<CategoryResponse> getAllCategory(Pageable pageable) {
        LOG.info("Fetching categories page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Category> existsCategories = categoryRepository.findAll(pageable);
        if (existsCategories.isEmpty()) {
            LOG.warn("No categories found in the database");
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_PRESENT.getMessage());
        }

        Page<CategoryResponse> categoryResponses = existsCategories.map(categoryMapper::categoryEntityToCategoryResponse);
        LOG.info("Retrieved {} categories, total elements: {}", categoryResponses.getNumberOfElements(), categoryResponses.getTotalElements());
        return categoryResponses;
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        LOG.info("Attempting to fetch category with ID: {}", id);
        Optional<Category> existCategory = categoryRepository.findById(id);
        if (existCategory.isEmpty()) {
            LOG.warn("Category fetch failed - no category found with ID: {}", id);
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_FOUND.getMessage(id));
        }

        CategoryResponse response = categoryMapper.categoryEntityToCategoryResponse(existCategory.get());
        LOG.info("Successfully retrieved category: {}", response.getCategoryName());
        return response;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        LOG.info("Attempting to update category with ID: {}", id);
        Optional<Category> existCategory = categoryRepository.findById(id);
        if (existCategory.isEmpty()) {
            LOG.warn("Category update failed - no category found with ID: {}", id);
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_FOUND.getMessage(id));
        }

        Category category = categoryMapper.categoryRequestToCategoryEntity(categoryRequest);
        category.setCategoryId(id);
        Category updatedCategory = categoryRepository.save(category);

        CategoryResponse response = categoryMapper.categoryEntityToCategoryResponse(updatedCategory);
        LOG.info("Successfully updated category with ID: {}, new name: {}", id, response.getCategoryName());
        return response;
    }

    @Override
    public void deleteCategory(Long id) {
        LOG.info("Attempting to delete category with ID: {}", id);
        Optional<Category> existCategory = categoryRepository.findById(id);
        if (existCategory.isEmpty()) {
            LOG.warn("Category deletion failed - no category found with ID: {}", id);
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_FOUND.getMessage(id));
        }
        categoryRepository.deleteById(id);
        LOG.info("Successfully deleted category with ID: {}", id);
    }
}
