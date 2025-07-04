package com.comptamaroc.products.controller;

import com.comptamaroc.products.model.Product;
import com.comptamaroc.products.repository.ProductRepository;
import com.comptamaroc.core.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Product>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<Product> products = productRepository.findAll(pageable);
            return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving products", e.getMessage()));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Product>>> getAllActiveProducts() {
        try {
            List<Product> products = productRepository.findByIsActiveTrue();
            return ResponseEntity.ok(ApiResponse.success("Active products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving active products", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        try {
            return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(ApiResponse.success("Product found", product)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product not found", "No product found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving product", e.getMessage()));
        }
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<Product>> getProductBySku(@PathVariable String sku) {
        try {
            return productRepository.findBySku(sku)
                .map(product -> ResponseEntity.ok(ApiResponse.success("Product found", product)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product not found", "No product found with SKU: " + sku)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving product", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProducts(@RequestParam String name) {
        try {
            List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
            return ResponseEntity.ok(ApiResponse.success("Search completed successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error searching products", e.getMessage()));
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productRepository.findActiveProductsByCategory(category);
            return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving products by category", e.getMessage()));
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<Product>>> getLowStockProducts() {
        try {
            List<Product> products = productRepository.findLowStockProducts();
            return ResponseEntity.ok(ApiResponse.success("Low stock products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving low stock products", e.getMessage()));
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> getAllCategories() {
        try {
            List<String> categories = productRepository.findAllCategories();
            return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving categories", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody Product product) {
        try {
            if (productRepository.existsBySku(product.getSku())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create product", "SKU already exists: " + product.getSku()));
            }
            
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", savedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error creating product", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable Long id, 
            @Valid @RequestBody Product productDetails) {
        try {
            return productRepository.findById(id)
                .map(existingProduct -> {
                    // Check if SKU is being changed and if it already exists
                    if (!existingProduct.getSku().equals(productDetails.getSku()) && 
                        productRepository.existsBySku(productDetails.getSku())) {
                        throw new RuntimeException("SKU already exists: " + productDetails.getSku());
                    }
                    
                    updateProductFields(existingProduct, productDetails);
                    Product updatedProduct = productRepository.save(existingProduct);
                    return ResponseEntity.ok(ApiResponse.success("Product updated successfully", updatedProduct));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product not found", "No product found with id: " + id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Failed to update product", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error updating product", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Product>> updateStock(
            @PathVariable Long id, 
            @RequestParam Integer quantity,
            @RequestParam(defaultValue = "SET") String operation) {
        try {
            return productRepository.findById(id)
                .map(product -> {
                    switch (operation.toUpperCase()) {
                        case "ADD":
                            product.setStockQuantity(product.getStockQuantity() + quantity);
                            break;
                        case "SUBTRACT":
                            int newQuantity = product.getStockQuantity() - quantity;
                            if (newQuantity < 0) {
                                throw new RuntimeException("Insufficient stock. Available: " + product.getStockQuantity());
                            }
                            product.setStockQuantity(newQuantity);
                            break;
                        case "SET":
                        default:
                            if (quantity < 0) {
                                throw new RuntimeException("Stock quantity cannot be negative");
                            }
                            product.setStockQuantity(quantity);
                            break;
                    }
                    
                    Product updatedProduct = productRepository.save(product);
                    return ResponseEntity.ok(ApiResponse.success("Stock updated successfully", updatedProduct));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product not found", "No product found with id: " + id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Failed to update stock", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error updating stock", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        try {
            if (!productRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Product not found", "No product found with id: " + id));
            }
            
            productRepository.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Product deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error deleting product", e.getMessage()));
        }
    }

    private void updateProductFields(Product existingProduct, Product productDetails) {
        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            existingProduct.setDescription(productDetails.getDescription());
        }
        if (productDetails.getSku() != null) {
            existingProduct.setSku(productDetails.getSku());
        }
        if (productDetails.getPrice() != null) {
            existingProduct.setPrice(productDetails.getPrice());
        }
        if (productDetails.getCost() != null) {
            existingProduct.setCost(productDetails.getCost());
        }
        if (productDetails.getStockQuantity() != null) {
            existingProduct.setStockQuantity(productDetails.getStockQuantity());
        }
        if (productDetails.getMinStockLevel() != null) {
            existingProduct.setMinStockLevel(productDetails.getMinStockLevel());
        }
        if (productDetails.getUnit() != null) {
            existingProduct.setUnit(productDetails.getUnit());
        }
        if (productDetails.getCategory() != null) {
            existingProduct.setCategory(productDetails.getCategory());
        }
        if (productDetails.getBrand() != null) {
            existingProduct.setBrand(productDetails.getBrand());
        }
        if (productDetails.getBarcode() != null) {
            existingProduct.setBarcode(productDetails.getBarcode());
        }
        if (productDetails.getVatRate() != null) {
            existingProduct.setVatRate(productDetails.getVatRate());
        }
        if (productDetails.getIsActive() != null) {
            existingProduct.setIsActive(productDetails.getIsActive());
        }
        if (productDetails.getIsService() != null) {
            existingProduct.setIsService(productDetails.getIsService());
        }
        if (productDetails.getNotes() != null) {
            existingProduct.setNotes(productDetails.getNotes());
        }
    }
}
