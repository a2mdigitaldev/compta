package com.comptamaroc.products.model;

import com.comptamaroc.core.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    @NotBlank(message = "SKU is required")
    @Size(max = 50, message = "SKU must not exceed 50 characters")
    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 10 integer digits and 2 decimal places")
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", message = "Cost must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Cost must have at most 10 integer digits and 2 decimal places")
    @Column(name = "cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity must be greater than or equal to 0")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Min(value = 0, message = "Minimum stock level must be greater than or equal to 0")
    @Column(name = "min_stock_level", columnDefinition = "INTEGER DEFAULT 0")
    private Integer minStockLevel = 0;

    @NotBlank(message = "Unit is required")
    @Size(max = 20, message = "Unit must not exceed 20 characters")
    @Column(name = "unit", nullable = false, length = 20)
    private String unit; // e.g., "pcs", "kg", "liter", "m2"

    @Size(max = 50, message = "Category must not exceed 50 characters")
    @Column(name = "category", length = 50)
    private String category;

    @Size(max = 50, message = "Brand must not exceed 50 characters")
    @Column(name = "brand", length = 50)
    private String brand;

    @Size(max = 100, message = "Barcode must not exceed 100 characters")
    @Column(name = "barcode", unique = true, length = 100)
    private String barcode;

    @DecimalMin(value = "0.0", message = "VAT rate must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "VAT rate must be less than or equal to 100")
    @Digits(integer = 3, fraction = 2, message = "VAT rate must have at most 3 integer digits and 2 decimal places")
    @Column(name = "vat_rate", precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 20.00")
    private BigDecimal vatRate = new BigDecimal("20.00"); // Default 20% VAT for Morocco

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    @Column(name = "is_service", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isService = false;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // Constructors
    public Product() {}

    public Product(String name, String sku, BigDecimal price, BigDecimal cost, Integer stockQuantity, String unit) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.cost = cost;
        this.stockQuantity = stockQuantity;
        this.unit = unit;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(Integer minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsService() {
        return isService;
    }

    public void setIsService(Boolean isService) {
        this.isService = isService;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Business methods
    public boolean isLowStock() {
        return stockQuantity <= minStockLevel;
    }

    public BigDecimal getMargin() {
        if (cost == null || price == null || cost.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return price.subtract(cost);
    }

    public BigDecimal getMarginPercentage() {
        if (cost == null || price == null || cost.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return getMargin().divide(cost, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
    }
}
