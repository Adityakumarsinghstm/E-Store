package com.aditya.electronic.store.services;

import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto, String productId);

    void delete(String productId);

    ProductDto get(String productId);

    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir);

    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    ProductDto updateProduct (String productId, String categoryId);

    PageableResponse<ProductDto> getAllofCategories(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);

}
