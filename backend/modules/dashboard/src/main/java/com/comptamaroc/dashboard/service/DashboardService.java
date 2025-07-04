package com.comptamaroc.dashboard.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Mock data for now - these would be calculated from actual data
        stats.put("totalClients", 125);
        stats.put("totalInvoices", 450);
        stats.put("monthlyRevenue", new BigDecimal("75000.00"));
        stats.put("pendingInvoices", 23);
        stats.put("overdueInvoices", 5);
        stats.put("totalProducts", 89);
        stats.put("lowStockProducts", 12);
        stats.put("thisMonthSales", new BigDecimal("25000.00"));
        stats.put("lastUpdated", LocalDate.now());
        
        return stats;
    }

    public Map<String, Object> getRecentActivity() {
        Map<String, Object> activity = new HashMap<>();
        
        // Mock recent activity data
        activity.put("recentInvoices", 5);
        activity.put("recentClients", 3);
        activity.put("recentPayments", 8);
        activity.put("lastLogin", LocalDate.now());
        
        return activity;
    }

    public Map<String, BigDecimal> getMonthlyRevenue() {
        Map<String, BigDecimal> monthlyData = new HashMap<>();
        
        // Mock monthly revenue data for the last 6 months
        monthlyData.put("January", new BigDecimal("65000.00"));
        monthlyData.put("February", new BigDecimal("72000.00"));
        monthlyData.put("March", new BigDecimal("68000.00"));
        monthlyData.put("April", new BigDecimal("75000.00"));
        monthlyData.put("May", new BigDecimal("82000.00"));
        monthlyData.put("June", new BigDecimal("75000.00"));
        
        return monthlyData;
    }

    public Map<String, Object> getFinancialSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        summary.put("totalRevenue", new BigDecimal("450000.00"));
        summary.put("totalExpenses", new BigDecimal("320000.00"));
        summary.put("netProfit", new BigDecimal("130000.00"));
        summary.put("profitMargin", "28.9%");
        summary.put("taxesOwed", new BigDecimal("26000.00"));
        summary.put("cashFlow", new BigDecimal("45000.00"));
        
        return summary;
    }
}
