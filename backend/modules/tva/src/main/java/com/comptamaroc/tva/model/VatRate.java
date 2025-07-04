package com.comptamaroc.tva.model;

import com.comptamaroc.core.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vat_rates")
public class VatRate extends BaseEntity {

    @NotBlank(message = "VAT rate name is required")
    @Size(max = 50, message = "VAT rate name must not exceed 50 characters")
    @Column(name = "rate_name", nullable = false, length = 50)
    private String rateName;

    @NotNull(message = "VAT rate percentage is required")
    @DecimalMin(value = "0.0", message = "VAT rate must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "VAT rate must be less than or equal to 100")
    @Column(name = "rate_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal ratePercentage;

    @NotNull(message = "VAT type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "vat_type", nullable = false)
    private VatType vatType;

    @NotNull(message = "Effective date is required")
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    @Column(name = "description", length = 200)
    private String description;

    // Moroccan specific
    @Size(max = 20, message = "Tax code must not exceed 20 characters")
    @Column(name = "tax_code", length = 20)
    private String taxCode;

    // Constructors
    public VatRate() {}

    public VatRate(String rateName, BigDecimal ratePercentage, VatType vatType, LocalDate effectiveDate) {
        this.rateName = rateName;
        this.ratePercentage = ratePercentage;
        this.vatType = vatType;
        this.effectiveDate = effectiveDate;
        this.isActive = true;
    }

    // Getters and setters
    public String getRateName() { return rateName; }
    public void setRateName(String rateName) { this.rateName = rateName; }

    public BigDecimal getRatePercentage() { return ratePercentage; }
    public void setRatePercentage(BigDecimal ratePercentage) { this.ratePercentage = ratePercentage; }

    public VatType getVatType() { return vatType; }
    public void setVatType(VatType vatType) { this.vatType = vatType; }

    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDate effectiveDate) { this.effectiveDate = effectiveDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTaxCode() { return taxCode; }
    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    // Enums for Moroccan VAT system
    public enum VatType {
        STANDARD,    // Taux normal (20%)
        REDUCED_1,   // Taux réduit 1 (14%)
        REDUCED_2,   // Taux réduit 2 (10%)
        REDUCED_3,   // Taux réduit 3 (7%)
        SUPER_REDUCED, // Taux super réduit (5.5%)
        EXEMPT,      // Exonéré
        ZERO_RATE    // Taux zéro
    }
}
