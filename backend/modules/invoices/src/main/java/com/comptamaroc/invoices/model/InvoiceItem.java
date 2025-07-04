package com.comptamaroc.invoices.model;

import com.comptamaroc.core.model.BaseEntity;
import com.comptamaroc.products.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank(message = "Item description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Quantity must have at most 8 integer digits and 2 decimal places")
    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", message = "Unit price must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Unit price must have at most 10 integer digits and 2 decimal places")
    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @NotNull(message = "Line total is required")
    @DecimalMin(value = "0.0", message = "Line total must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Line total must have at most 10 integer digits and 2 decimal places")
    @Column(name = "line_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal lineTotal;

    @NotNull(message = "VAT rate is required")
    @DecimalMin(value = "0.0", message = "VAT rate must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "VAT rate must be less than or equal to 100")
    @Digits(integer = 3, fraction = 2, message = "VAT rate must have at most 3 integer digits and 2 decimal places")
    @Column(name = "vat_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal vatRate;

    @NotNull(message = "VAT amount is required")
    @DecimalMin(value = "0.0", message = "VAT amount must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "VAT amount must have at most 10 integer digits and 2 decimal places")
    @Column(name = "vat_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal vatAmount;

    @Size(max = 20, message = "Unit must not exceed 20 characters")
    @Column(name = "unit", length = 20)
    private String unit;

    // Constructors
    public InvoiceItem() {}

    public InvoiceItem(String description, BigDecimal quantity, BigDecimal unitPrice, BigDecimal vatRate) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.vatRate = vatRate;
        // Calculate amounts manually in constructor to avoid overridable method call
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

    // Getters and Setters
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.description = product.getName();
            this.unitPrice = product.getPrice();
            this.vatRate = product.getVatRate();
            this.unit = product.getUnit();
            calculateAmounts();
        }
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

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
        calculateAmounts();
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
    public void calculateAmounts() {
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
        return lineTotal.add(vatAmount);
    }
}
