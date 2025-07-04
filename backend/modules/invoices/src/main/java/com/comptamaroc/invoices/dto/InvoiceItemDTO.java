package com.comptamaroc.invoices.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class InvoiceItemDTO {
    
    private Long id;
    
    private Long productId;
    private String productName;
    private String productSku;
    
    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    
    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private BigDecimal quantity;
    
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", message = "Unit price must be greater than or equal to 0")
    private BigDecimal unitPrice;
    
    @NotNull(message = "VAT rate is required")
    @DecimalMin(value = "0.0", message = "VAT rate must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "VAT rate must be less than or equal to 100")
    private BigDecimal vatRate;
    
    private BigDecimal lineTotal;
    private BigDecimal vatAmount;
    private String unit;
    
    // Constructors
    public InvoiceItemDTO() {}
    
    public InvoiceItemDTO(String description, BigDecimal quantity, BigDecimal unitPrice, BigDecimal vatRate) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.vatRate = vatRate;
        calculateAmounts();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        calculateAmounts();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        calculateAmounts();
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
        calculateAmounts();
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Business methods
    private void calculateAmounts() {
        if (quantity != null && unitPrice != null) {
            this.lineTotal = quantity.multiply(unitPrice);
            
            if (vatRate != null) {
                this.vatAmount = lineTotal.multiply(vatRate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
            } else {
                this.vatAmount = BigDecimal.ZERO;
            }
        } else {
            this.lineTotal = BigDecimal.ZERO;
            this.vatAmount = BigDecimal.ZERO;
        }
    }

    public BigDecimal getTotalWithVat() {
        return lineTotal != null && vatAmount != null ? lineTotal.add(vatAmount) : BigDecimal.ZERO;
    }
}
