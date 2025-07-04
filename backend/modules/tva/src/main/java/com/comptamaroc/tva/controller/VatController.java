package com.comptamaroc.tva.controller;

import com.comptamaroc.core.dto.ApiResponse;
import com.comptamaroc.tva.service.VatCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/vat")
public class VatController {

    private final VatCalculationService vatCalculationService;

    @Autowired
    public VatController(VatCalculationService vatCalculationService) {
        this.vatCalculationService = vatCalculationService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ApiResponse<VatCalculationService.VatCalculationResult>> calculateVat(
            @RequestParam BigDecimal amount,
            @RequestParam String businessType,
            @RequestParam String productType) {
        
        VatCalculationService.VatCalculationResult result = 
            vatCalculationService.calculateMoroccanVat(amount, businessType, productType);
        
        return ResponseEntity.ok(ApiResponse.success("VAT calculated successfully", result));
    }

    @PostMapping("/calculate-simple")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateSimpleVat(
            @RequestParam BigDecimal amount,
            @RequestParam BigDecimal vatRate) {
        
        BigDecimal vatAmount = vatCalculationService.calculateVatAmount(amount, vatRate);
        return ResponseEntity.ok(ApiResponse.success("VAT amount calculated", vatAmount));
    }

    @PostMapping("/quarterly-return")
    public ResponseEntity<ApiResponse<VatCalculationService.QuarterlyVatReturn>> calculateQuarterlyReturn(
            @RequestParam BigDecimal salesVat,
            @RequestParam BigDecimal purchaseVat) {
        
        VatCalculationService.QuarterlyVatReturn result = 
            vatCalculationService.calculateQuarterlyReturn(salesVat, purchaseVat);
        
        return ResponseEntity.ok(ApiResponse.success("Quarterly VAT return calculated", result));
    }

    @GetMapping("/exemption-check")
    public ResponseEntity<ApiResponse<Boolean>> checkVatExemption(@RequestParam BigDecimal annualTurnover) {
        boolean isExempt = vatCalculationService.isVatExempt(annualTurnover);
        return ResponseEntity.ok(ApiResponse.success("VAT exemption status checked", isExempt));
    }
}
