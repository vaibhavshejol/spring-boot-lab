package com.amazingcode.in.example.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.amazingcode.in.example.entity.Category;
import com.amazingcode.in.example.request.CategoryRequest;
import com.amazingcode.in.example.response.CategoryResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category categoryRequestToCategoryEntity(CategoryRequest categoryRequest);

    CategoryResponse categoryEntityToCategoryResponse(Category category);

    List<CategoryResponse> categoryEntityListToCategoryResponseList(List<Category> categories);
    
}
