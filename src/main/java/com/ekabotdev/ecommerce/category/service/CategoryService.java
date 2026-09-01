package com.ekabotdev.ecommerce.category.service;


import com.ekabotdev.ecommerce.category.dto.CategoryResponse;
import com.ekabotdev.ecommerce.category.dto.UpdateCategoryRequest;
import com.ekabotdev.ecommerce.category.entity.Category;
import com.ekabotdev.ecommerce.category.dto.CreateCategoryRequest;
import com.ekabotdev.ecommerce.category.exception.CategoryAlreadyExistsException;
import com.ekabotdev.ecommerce.category.exception.CategoryNotFoundException;
import com.ekabotdev.ecommerce.category.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    public CategoryRepository categoryRepository;

    public  CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        String name = request.getName().trim();

        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new CategoryAlreadyExistsException(
                    "Category name " + name + " already exists!"
            );
        }
        Category category = new Category();
        category.setName(name);

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponse(
                savedCategory.getId(),
                savedCategory.getName(),
                savedCategory.getCreatedAt(),
                savedCategory.getUpdatedAt()
        );

    }
    @Transactional(readOnly = true)
    public CategoryResponse getCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(() ->
        new CategoryNotFoundException("Category with id " + id + " not found!")
        );
        return toResponse(category);
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }

    public Page<CategoryResponse> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException("Category with id " + id + " not found!")
        );


        String name = request.getName().trim();
        if(categoryRepository.existsByNameIgnoreCaseAndIdNot(name,id)) {
            throw new CategoryAlreadyExistsException("Category '" + name + "' already exists!"
            );
        }
        category.setName(name);
        return  toResponse(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
         new CategoryNotFoundException("Category with id " + id + " not found!")
        );
        categoryRepository.delete(category);

    }
}