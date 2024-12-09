package com.amazingcode.in.example.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.amazingcode.in.example.entity.Product;
import com.amazingcode.in.example.request.ProductRequest;
import com.amazingcode.in.example.response.ProductResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "productId", ignore = true)
    @Mapping(source = "categoryId", target = "category.categoryId")
    Product productRequestToProductEntity(ProductRequest productRequest);

    @Mapping(source = "category", target = "category")
    ProductResponse productEntityToProductResponse(Product product);

    List<ProductResponse> productEntityListToProductResponseList(List<Product> products);
}