package com.comptamaroc.ai.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SmartRecommendationService {

    /**
     * Generate financial recommendations based on business data
     */
    public List<Recommendation> generateFinancialRecommendations(FinancialData financialData) {
        List<Recommendation> recommendations = new ArrayList<>();

        // Cash flow recommendations
        if (financialData.getCashFlow().compareTo(BigDecimal.ZERO) < 0) {
            recommendations.add(new Recommendation(
                RecommendationType.CASH_FLOW,
                "Negative Cash Flow Alert",
                "Your cash flow is negative. Consider improving collections or reducing expenses.",
                RecommendationPriority.HIGH
            ));
        }

        // Profitability recommendations
        BigDecimal profitMargin = calculateProfitMargin(financialData);
        if (profitMargin.compareTo(new BigDecimal("10")) < 0) {
            recommendations.add(new Recommendation(
                RecommendationType.PROFITABILITY,
                "Low Profit Margin",
                "Your profit margin is below 10%. Consider reviewing pricing or reducing costs.",
                RecommendationPriority.MEDIUM
            ));
        }

        // Tax optimization recommendations
        if (financialData.getAnnualRevenue().compareTo(new BigDecimal("500000")) > 0) {
            recommendations.add(new Recommendation(
                RecommendationType.TAX_OPTIMIZATION,
                "VAT Registration Required",
                "Your annual revenue exceeds 500,000 MAD. VAT registration is mandatory in Morocco.",
                RecommendationPriority.HIGH
            ));
        }

        // Expense optimization
        BigDecimal expenseRatio = calculateExpenseRatio(financialData);
        if (expenseRatio.compareTo(new BigDecimal("80")) > 0) {
            recommendations.add(new Recommendation(
                RecommendationType.EXPENSE_OPTIMIZATION,
                "High Expense Ratio",
                "Your expenses are over 80% of revenue. Review cost structure for optimization opportunities.",
                RecommendationPriority.MEDIUM
            ));
        }

        return recommendations;
    }

    /**
     * Generate invoice recommendations
     */
    public List<Recommendation> generateInvoiceRecommendations(InvoiceData invoiceData) {
        List<Recommendation> recommendations = new ArrayList<>();

        // Overdue invoices
        if (invoiceData.getOverdueAmount().compareTo(BigDecimal.ZERO) > 0) {
            recommendations.add(new Recommendation(
                RecommendationType.COLLECTIONS,
                "Overdue Invoices Detected",
                String.format("You have %.2f MAD in overdue invoices. Consider sending payment reminders.", 
                            invoiceData.getOverdueAmount()),
                RecommendationPriority.HIGH
            ));
        }

        // Payment terms
        if (invoiceData.getAveragePaymentDays() > 45) {
            recommendations.add(new Recommendation(
                RecommendationType.PAYMENT_TERMS,
                "Long Payment Cycles",
                "Average payment cycle is over 45 days. Consider offering early payment discounts.",
                RecommendationPriority.MEDIUM
            ));
        }

        return recommendations;
    }

    /**
     * Generate inventory recommendations
     */
    public List<Recommendation> generateInventoryRecommendations(InventoryData inventoryData) {
        List<Recommendation> recommendations = new ArrayList<>();

        // Low stock alerts
        if (inventoryData.getLowStockItems() > 0) {
            recommendations.add(new Recommendation(
                RecommendationType.INVENTORY,
                "Low Stock Alert",
                String.format("%d items are below minimum stock level. Consider reordering.", 
                            inventoryData.getLowStockItems()),
                RecommendationPriority.MEDIUM
            ));
        }

        // Slow-moving inventory
        if (inventoryData.getSlowMovingValue().compareTo(BigDecimal.ZERO) > 0) {
            recommendations.add(new Recommendation(
                RecommendationType.INVENTORY,
                "Slow-Moving Inventory",
                String.format("%.2f MAD worth of slow-moving inventory detected. Consider promotions or discounts.", 
                            inventoryData.getSlowMovingValue()),
                RecommendationPriority.LOW
            ));
        }

        return recommendations;
    }

    /**
     * Calculate profit margin percentage
     */
    private BigDecimal calculateProfitMargin(FinancialData data) {
        if (data.getRevenue().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return data.getProfit().multiply(new BigDecimal("100")).divide(data.getRevenue(), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculate expense ratio percentage
     */
    private BigDecimal calculateExpenseRatio(FinancialData data) {
        if (data.getRevenue().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return data.getExpenses().multiply(new BigDecimal("100")).divide(data.getRevenue(), 2, BigDecimal.ROUND_HALF_UP);
    }

    // Data classes
    public static class FinancialData {
        private final BigDecimal revenue;
        private final BigDecimal expenses;
        private final BigDecimal profit;
        private final BigDecimal cashFlow;
        private final BigDecimal annualRevenue;

        public FinancialData(BigDecimal revenue, BigDecimal expenses, BigDecimal profit, 
                           BigDecimal cashFlow, BigDecimal annualRevenue) {
            this.revenue = revenue;
            this.expenses = expenses;
            this.profit = profit;
            this.cashFlow = cashFlow;
            this.annualRevenue = annualRevenue;
        }

        // Getters
        public BigDecimal getRevenue() { return revenue; }
        public BigDecimal getExpenses() { return expenses; }
        public BigDecimal getProfit() { return profit; }
        public BigDecimal getCashFlow() { return cashFlow; }
        public BigDecimal getAnnualRevenue() { return annualRevenue; }
    }

    public static class InvoiceData {
        private final BigDecimal overdueAmount;
        private final int averagePaymentDays;
        private final int totalInvoices;

        public InvoiceData(BigDecimal overdueAmount, int averagePaymentDays, int totalInvoices) {
            this.overdueAmount = overdueAmount;
            this.averagePaymentDays = averagePaymentDays;
            this.totalInvoices = totalInvoices;
        }

        // Getters
        public BigDecimal getOverdueAmount() { return overdueAmount; }
        public int getAveragePaymentDays() { return averagePaymentDays; }
        public int getTotalInvoices() { return totalInvoices; }
    }

    public static class InventoryData {
        private final int lowStockItems;
        private final BigDecimal slowMovingValue;
        private final int totalItems;

        public InventoryData(int lowStockItems, BigDecimal slowMovingValue, int totalItems) {
            this.lowStockItems = lowStockItems;
            this.slowMovingValue = slowMovingValue;
            this.totalItems = totalItems;
        }

        // Getters
        public int getLowStockItems() { return lowStockItems; }
        public BigDecimal getSlowMovingValue() { return slowMovingValue; }
        public int getTotalItems() { return totalItems; }
    }

    public static class Recommendation {
        private final RecommendationType type;
        private final String title;
        private final String description;
        private final RecommendationPriority priority;
        private final LocalDate createdAt;

        public Recommendation(RecommendationType type, String title, String description, RecommendationPriority priority) {
            this.type = type;
            this.title = title;
            this.description = description;
            this.priority = priority;
            this.createdAt = LocalDate.now();
        }

        // Getters
        public RecommendationType getType() { return type; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public RecommendationPriority getPriority() { return priority; }
        public LocalDate getCreatedAt() { return createdAt; }
    }

    public enum RecommendationType {
        CASH_FLOW,
        PROFITABILITY,
        TAX_OPTIMIZATION,
        EXPENSE_OPTIMIZATION,
        COLLECTIONS,
        PAYMENT_TERMS,
        INVENTORY
    }

    public enum RecommendationPriority {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
