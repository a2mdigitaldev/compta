package com.comptamaroc.dashboard.controller;

import com.comptamaroc.dashboard.service.DashboardService;
import com.comptamaroc.core.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        try {
            Map<String, Object> stats = dashboardService.getDashboardStats();
            return ResponseEntity.ok(ApiResponse.success("Dashboard statistics retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error retrieving dashboard statistics", e.getMessage()));
        }
    }

    @GetMapping("/activity")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRecentActivity() {
        try {
            Map<String, Object> activity = dashboardService.getRecentActivity();
            return ResponseEntity.ok(ApiResponse.success("Recent activity retrieved successfully", activity));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error retrieving recent activity", e.getMessage()));
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<Map<String, BigDecimal>>> getMonthlyRevenue() {
        try {
            Map<String, BigDecimal> revenue = dashboardService.getMonthlyRevenue();
            return ResponseEntity.ok(ApiResponse.success("Monthly revenue data retrieved successfully", revenue));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error retrieving monthly revenue", e.getMessage()));
        }
    }

    @GetMapping("/financial-summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getFinancialSummary() {
        try {
            Map<String, Object> summary = dashboardService.getFinancialSummary();
            return ResponseEntity.ok(ApiResponse.success("Financial summary retrieved successfully", summary));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error retrieving financial summary", e.getMessage()));
        }
    }

    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardOverview() {
        try {
            Map<String, Object> overview = Map.of(
                "stats", dashboardService.getDashboardStats(),
                "activity", dashboardService.getRecentActivity(),
                "financialSummary", dashboardService.getFinancialSummary()
            );
            return ResponseEntity.ok(ApiResponse.success("Dashboard overview retrieved successfully", overview));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error retrieving dashboard overview", e.getMessage()));
        }
    }
}
