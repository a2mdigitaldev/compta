package com.comptamaroc.products.repository;

import com.comptamaroc.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findBySku(String sku);
    
    Optional<Product> findByBarcode(String barcode);
    
    List<Product> findByIsActiveTrue();
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.isActive = true")
    List<Product> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.isActive = true")
    List<Product> findActiveProductsByCategory(@Param("category") String category);
    
    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.isActive = true")
    List<Product> findActiveProductsByBrand(@Param("brand") String brand);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= p.minStockLevel AND p.isActive = true")
    List<Product> findLowStockProducts();
    
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.isActive = true")
    List<Product> findProductsByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.category IS NOT NULL AND p.isActive = true ORDER BY p.category")
    List<String> findAllCategories();
    
    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL AND p.isActive = true ORDER BY p.brand")
    List<String> findAllBrands();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.isActive = true")
    long countActiveProducts();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.stockQuantity <= p.minStockLevel AND p.isActive = true")
    long countLowStockProducts();
    
    @Query("SELECT SUM(p.stockQuantity * p.cost) FROM Product p WHERE p.isActive = true")
    BigDecimal calculateTotalInventoryValue();
    
    boolean existsBySku(String sku);
    
    boolean existsByBarcode(String barcode);
}
