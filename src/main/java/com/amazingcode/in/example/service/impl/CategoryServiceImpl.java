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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        boolean isCategoryPresent = categoryRepository.existsByCategoryName(categoryRequest.getCategoryName());
        if (isCategoryPresent) {
            throw new AlreadyPresentException(CategoryResponseMessage.CATEGORY_ALREADY_EXISTS.getMessage(categoryRequest.getCategoryName()));
        }

        Category category = categoryMapper.categoryRequestToCategoryEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.categoryEntityToCategoryResponse(savedCategory);
    }

    @Override
    public Page<CategoryResponse> getAllCategory(Pageable pageable) {
        Page<Category> existsCategories = categoryRepository.findAll(pageable);
        if (existsCategories.isEmpty()) {
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_PRESENT.getMessage());
        }

        return existsCategories.map(categoryMapper::categoryEntityToCategoryResponse);
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        Optional<Category> existCategory = categoryRepository.findById(id);
        if (existCategory.isEmpty()) {
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_FOUND.getMessage(id));
        }

        return categoryMapper.categoryEntityToCategoryResponse(existCategory.get());
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Optional<Category> existCategory = categoryRepository.findById(id);
        if (existCategory.isEmpty()) {
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_FOUND.getMessage(id));
        }

        Category category = categoryMapper.categoryRequestToCategoryEntity(categoryRequest);
        category.setCategoryId(id);
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.categoryEntityToCategoryResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> existCategory = categoryRepository.findById(id);
        if (existCategory.isEmpty()) {
            throw new NotPresentException(CategoryResponseMessage.CATEGORY_NOT_FOUND.getMessage(id));
        }
        categoryRepository.deleteById(id);
    }
}
