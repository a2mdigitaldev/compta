package com.comptamaroc.tva.service;

import com.comptamaroc.tva.model.VatRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class VatCalculationService {

    // Standard Moroccan VAT rates (as of 2024)
    private static final BigDecimal STANDARD_RATE = new BigDecimal("20.00");      // 20%
    private static final BigDecimal REDUCED_RATE_1 = new BigDecimal("14.00");     // 14%
    private static final BigDecimal REDUCED_RATE_2 = new BigDecimal("10.00");     // 10%
    private static final BigDecimal REDUCED_RATE_3 = new BigDecimal("7.00");      // 7%
    private static final BigDecimal SUPER_REDUCED_RATE = new BigDecimal("5.50");  // 5.5%
    private static final BigDecimal ZERO_RATE = BigDecimal.ZERO;

    /**
     * Calculate VAT amount from base amount and VAT rate
     */
    public BigDecimal calculateVatAmount(BigDecimal baseAmount, BigDecimal vatRate) {
        if (baseAmount == null || vatRate == null) {
            return BigDecimal.ZERO;
        }
        
        return baseAmount.multiply(vatRate)
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate VAT amount using VAT type
     */
    public BigDecimal calculateVatAmount(BigDecimal baseAmount, VatRate.VatType vatType) {
        BigDecimal rate = getVatRateByType(vatType);
        return calculateVatAmount(baseAmount, rate);
    }

    /**
     * Calculate total amount including VAT
     */
    public BigDecimal calculateTotalWithVat(BigDecimal baseAmount, BigDecimal vatRate) {
        BigDecimal vatAmount = calculateVatAmount(baseAmount, vatRate);
        return baseAmount.add(vatAmount);
    }

    /**
     * Calculate total amount including VAT using VAT type
     */
    public BigDecimal calculateTotalWithVat(BigDecimal baseAmount, VatRate.VatType vatType) {
        BigDecimal rate = getVatRateByType(vatType);
        return calculateTotalWithVat(baseAmount, rate);
    }

    /**
     * Calculate base amount from total amount including VAT
     */
    public BigDecimal calculateBaseAmountFromTotal(BigDecimal totalAmount, BigDecimal vatRate) {
        if (totalAmount == null || vatRate == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal divisor = BigDecimal.ONE.add(vatRate.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
        return totalAmount.divide(divisor, 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate VAT amount from total amount including VAT
     */
    public BigDecimal calculateVatAmountFromTotal(BigDecimal totalAmount, BigDecimal vatRate) {
        BigDecimal baseAmount = calculateBaseAmountFromTotal(totalAmount, vatRate);
        return totalAmount.subtract(baseAmount);
    }

    /**
     * Get VAT rate by type (standard Moroccan rates)
     */
    public BigDecimal getVatRateByType(VatRate.VatType vatType) {
        switch (vatType) {
            case STANDARD:
                return STANDARD_RATE;
            case REDUCED_1:
                return REDUCED_RATE_1;
            case REDUCED_2:
                return REDUCED_RATE_2;
            case REDUCED_3:
                return REDUCED_RATE_3;
            case SUPER_REDUCED:
                return SUPER_REDUCED_RATE;
            case EXEMPT:
            case ZERO_RATE:
            default:
                return ZERO_RATE;
        }
    }

    /**
     * Check if transaction is VAT exempt based on amount and business type
     */
    public boolean isVatExempt(BigDecimal annualTurnover) {
        // Morocco: Small businesses with annual turnover under 500,000 MAD are VAT exempt
        BigDecimal exemptionThreshold = new BigDecimal("500000");
        return annualTurnover.compareTo(exemptionThreshold) < 0;
    }

    /**
     * Calculate VAT for different business scenarios in Morocco
     */
    public VatCalculationResult calculateMoroccanVat(BigDecimal amount, String businessType, String productType) {
        VatRate.VatType vatType = determineVatType(businessType, productType);
        BigDecimal vatRate = getVatRateByType(vatType);
        BigDecimal vatAmount = calculateVatAmount(amount, vatRate);
        BigDecimal totalAmount = amount.add(vatAmount);

        return new VatCalculationResult(amount, vatAmount, totalAmount, vatRate, vatType);
    }

    /**
     * Determine VAT type based on business and product type
     */
    private VatRate.VatType determineVatType(String businessType, String productType) {
        // Simplified logic - in real implementation this would be more complex
        if (productType != null) {
            switch (productType.toLowerCase()) {
                case "food_basic":
                case "bread":
                case "milk":
                    return VatRate.VatType.EXEMPT;
                case "food_processed":
                    return VatRate.VatType.REDUCED_2;
                case "medicine":
                case "books":
                    return VatRate.VatType.REDUCED_3;
                case "hotel":
                case "restaurant":
                    return VatRate.VatType.REDUCED_1;
                case "luxury":
                case "alcohol":
                case "tobacco":
                    return VatRate.VatType.STANDARD;
                default:
                    return VatRate.VatType.STANDARD;
            }
        }
        return VatRate.VatType.STANDARD;
    }

    /**
     * Calculate quarterly VAT return for Morocco
     */
    public QuarterlyVatReturn calculateQuarterlyReturn(BigDecimal salesVat, BigDecimal purchaseVat) {
        BigDecimal netVat = salesVat.subtract(purchaseVat);
        boolean isRefundDue = netVat.compareTo(BigDecimal.ZERO) < 0;
        BigDecimal paymentDue = isRefundDue ? BigDecimal.ZERO : netVat;
        BigDecimal refundDue = isRefundDue ? netVat.abs() : BigDecimal.ZERO;

        return new QuarterlyVatReturn(salesVat, purchaseVat, netVat, paymentDue, refundDue);
    }

    // Inner classes for results
    public static class VatCalculationResult {
        private final BigDecimal baseAmount;
        private final BigDecimal vatAmount;
        private final BigDecimal totalAmount;
        private final BigDecimal vatRate;
        private final VatRate.VatType vatType;

        public VatCalculationResult(BigDecimal baseAmount, BigDecimal vatAmount, BigDecimal totalAmount, 
                                  BigDecimal vatRate, VatRate.VatType vatType) {
            this.baseAmount = baseAmount;
            this.vatAmount = vatAmount;
            this.totalAmount = totalAmount;
            this.vatRate = vatRate;
            this.vatType = vatType;
        }

        // Getters
        public BigDecimal getBaseAmount() { return baseAmount; }
        public BigDecimal getVatAmount() { return vatAmount; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public BigDecimal getVatRate() { return vatRate; }
        public VatRate.VatType getVatType() { return vatType; }
    }

    public static class QuarterlyVatReturn {
        private final BigDecimal salesVat;
        private final BigDecimal purchaseVat;
        private final BigDecimal netVat;
        private final BigDecimal paymentDue;
        private final BigDecimal refundDue;

        public QuarterlyVatReturn(BigDecimal salesVat, BigDecimal purchaseVat, BigDecimal netVat, 
                                BigDecimal paymentDue, BigDecimal refundDue) {
            this.salesVat = salesVat;
            this.purchaseVat = purchaseVat;
            this.netVat = netVat;
            this.paymentDue = paymentDue;
            this.refundDue = refundDue;
        }

        // Getters
        public BigDecimal getSalesVat() { return salesVat; }
        public BigDecimal getPurchaseVat() { return purchaseVat; }
        public BigDecimal getNetVat() { return netVat; }
        public BigDecimal getPaymentDue() { return paymentDue; }
        public BigDecimal getRefundDue() { return refundDue; }
    }
}
