package com.aditya.electronic.store.services;

import com.aditya.electronic.store.dtos.CategoryDto;
import com.aditya.electronic.store.dtos.PageableResponse;
import org.springframework.stereotype.Service;


public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update (CategoryDto categoryDto, String categoryId);

    void delete(String categoryId);

    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    CategoryDto get(String categoryId);
}
