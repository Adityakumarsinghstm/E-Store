package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.dtos.CategoryDto;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.entities.Category;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.helper.Helper;
import com.aditya.electronic.store.repositories.CategoryRepository;
import com.aditya.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
   // @Autowired
   // CategoryService categoryService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = dtoToEntity(categoryDto);
        Category savedCategory =  categoryRepository.save(category);
        CategoryDto categoryDto1 = entityToDto(savedCategory);
        return categoryDto1;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
       Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not found with this categoryId !!"));
       category.setTitle(categoryDto.getTitle());
       category.setDescription(categoryDto.getDescription());
       category.setCoverImage(categoryDto.getCoverImage());
       Category updatedCategory = categoryRepository.save(category);
       CategoryDto categoryDto1 = entityToDto(updatedCategory);
        return categoryDto;
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not found with this categoryId !!"));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }


    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not found with this categoryId !!"));
        CategoryDto categoryDto = entityToDto(category);
        return categoryDto;
    }

    private CategoryDto entityToDto(Category savedCategory)
    {
        CategoryDto categoryDto = mapper.map(savedCategory,CategoryDto.class);
        return categoryDto;
    }

    private Category dtoToEntity(CategoryDto categoryDto)
    {
       Category category = mapper.map(categoryDto,Category.class);
       return category;
    }
}
