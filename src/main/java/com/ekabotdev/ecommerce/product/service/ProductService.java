package com.ekabotdev.ecommerce.product.service;


import com.ekabotdev.ecommerce.category.entity.Category;
import com.ekabotdev.ecommerce.category.exception.CategoryNotFoundException;
import com.ekabotdev.ecommerce.category.repository.CategoryRepository;
import com.ekabotdev.ecommerce.product.dto.CreateProductRequest;
import com.ekabotdev.ecommerce.product.dto.ProductResponse;
import com.ekabotdev.ecommerce.product.dto.UpdateProductRequest;
import com.ekabotdev.ecommerce.product.entity.Product;
import com.ekabotdev.ecommerce.product.enums.ProductStatus;
import com.ekabotdev.ecommerce.product.exception.ProductNotFoundException;
import com.ekabotdev.ecommerce.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException
                ("Category with id " + request.getCategoryId() + " not found"));

        Product product = new Product();
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getQuantity());
        product.setStatus(
                request.getQuantity() >0
                ? ProductStatus.ACTIVE
                        : ProductStatus.OUT_OF_STOCK
        );
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return  toResponse(savedProduct);

    }
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getStatus(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::toResponse);
    }
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ProductNotFoundException("Product with id " + id + " not found"));
        return toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() ->
        new ProductNotFoundException("Product with  id " + id + " not found"));


        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new CategoryNotFoundException("Category with id " + id + " not found"));

        product.setName(request.getName().trim());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);


        product.setStatus(
                request.getStockQuantity() > 0
                ?ProductStatus.ACTIVE
                        : ProductStatus.OUT_OF_STOCK
        );
        return toResponse(product);
    }


    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ProductNotFoundException("Product with  id " + id + " not found"));

        productRepository.delete(product);
    }
}
