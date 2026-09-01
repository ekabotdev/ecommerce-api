package com.ekabotdev.ecommerce.category.controller;


import com.ekabotdev.ecommerce.category.dto.CategoryResponse;
import com.ekabotdev.ecommerce.category.dto.CreateCategoryRequest;
import com.ekabotdev.ecommerce.category.dto.UpdateCategoryRequest;
import com.ekabotdev.ecommerce.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
      this.categoryService = categoryService;
  }

  @PostMapping
    public ResponseEntity<CategoryResponse> createCategory
          (@Valid @RequestBody CreateCategoryRequest request) {

      CategoryResponse response = categoryService.createCategory(request);

      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(response);
  }

  @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategories(Pageable pageable) {
      return ResponseEntity.ok(
              categoryService.getCategories(pageable)
      );
  }

  @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
      return ResponseEntity.ok(
              categoryService.getCategory(id)
      );
  }

  @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory
          (@PathVariable Long id,
           @Valid @RequestBody UpdateCategoryRequest request) {
      return ResponseEntity.ok(categoryService.updateCategory(id,request)
      );
  }

  @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id) {
      categoryService.deleteCategory(id);
      return ResponseEntity.noContent().build();
  }
}
