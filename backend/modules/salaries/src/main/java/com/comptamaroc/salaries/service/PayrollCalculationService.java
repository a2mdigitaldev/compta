package com.comptamaroc.salaries.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PayrollCalculationService {

    // Moroccan social security rates (as of 2024)
    private static final BigDecimal CNSS_EMPLOYEE_RATE = new BigDecimal("4.48");      // 4.48%
    private static final BigDecimal CNSS_EMPLOYER_RATE = new BigDecimal("16.98");     // 16.98%
    private static final BigDecimal AMO_EMPLOYEE_RATE = new BigDecimal("2.26");       // 2.26%
    private static final BigDecimal AMO_EMPLOYER_RATE = new BigDecimal("3.96");       // 3.96%
    private static final BigDecimal FORMATION_RATE = new BigDecimal("1.60");          // 1.60% (employer only)
    
    // Income tax rates (IR) - Progressive tax brackets
    private static final BigDecimal TAX_FREE_THRESHOLD = new BigDecimal("2500");      // 2,500 MAD/month
    private static final BigDecimal[] TAX_BRACKETS = {
        new BigDecimal("2500"),   // 0% up to 2,500 MAD
        new BigDecimal("4166"),   // 10% from 2,501 to 4,166 MAD
        new BigDecimal("5000"),   // 20% from 4,167 to 5,000 MAD
        new BigDecimal("6666"),   // 30% from 5,001 to 6,666 MAD
        new BigDecimal("15000"),  // 34% from 6,667 to 15,000 MAD
        new BigDecimal("120000")  // 38% from 15,001 to 120,000 MAD
        // 40% above 120,000 MAD
    };
    
    private static final BigDecimal[] TAX_RATES = {
        BigDecimal.ZERO,             // 0%
        new BigDecimal("10"),        // 10%
        new BigDecimal("20"),        // 20%
        new BigDecimal("30"),        // 30%
        new BigDecimal("34"),        // 34%
        new BigDecimal("38"),        // 38%
        new BigDecimal("40")         // 40%
    };

    /**
     * Calculate CNSS (Social Security) contribution for employee
     */
    public BigDecimal calculateCnssEmployee(BigDecimal grossSalary) {
        return grossSalary.multiply(CNSS_EMPLOYEE_RATE)
                         .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate CNSS (Social Security) contribution for employer
     */
    public BigDecimal calculateCnssEmployer(BigDecimal grossSalary) {
        return grossSalary.multiply(CNSS_EMPLOYER_RATE)
                         .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate AMO (Medical Insurance) contribution for employee
     */
    public BigDecimal calculateAmoEmployee(BigDecimal grossSalary) {
        return grossSalary.multiply(AMO_EMPLOYEE_RATE)
                         .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate AMO (Medical Insurance) contribution for employer
     */
    public BigDecimal calculateAmoEmployer(BigDecimal grossSalary) {
        return grossSalary.multiply(AMO_EMPLOYER_RATE)
                         .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate Formation Professionnelle (employer only)
     */
    public BigDecimal calculateFormationProfessionnelle(BigDecimal grossSalary) {
        return grossSalary.multiply(FORMATION_RATE)
                         .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate total employee deductions
     */
    public BigDecimal calculateTotalEmployeeDeductions(BigDecimal grossSalary) {
        BigDecimal cnss = calculateCnssEmployee(grossSalary);
        BigDecimal amo = calculateAmoEmployee(grossSalary);
        return cnss.add(amo);
    }

    /**
     * Calculate total employer contributions
     */
    public BigDecimal calculateTotalEmployerContributions(BigDecimal grossSalary) {
        BigDecimal cnss = calculateCnssEmployer(grossSalary);
        BigDecimal amo = calculateAmoEmployer(grossSalary);
        BigDecimal formation = calculateFormationProfessionnelle(grossSalary);
        return cnss.add(amo).add(formation);
    }

    /**
     * Calculate taxable income (after social security deductions)
     */
    public BigDecimal calculateTaxableIncome(BigDecimal grossSalary) {
        BigDecimal deductions = calculateTotalEmployeeDeductions(grossSalary);
        return grossSalary.subtract(deductions);
    }

    /**
     * Calculate income tax (IR) using Moroccan progressive tax system
     */
    public BigDecimal calculateIncomeTax(BigDecimal taxableIncome) {
        if (taxableIncome.compareTo(TAX_FREE_THRESHOLD) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal remainingIncome = taxableIncome;
        BigDecimal previousBracket = BigDecimal.ZERO;

        for (int i = 0; i < TAX_BRACKETS.length && remainingIncome.compareTo(BigDecimal.ZERO) > 0; i++) {
            BigDecimal currentBracket = TAX_BRACKETS[i];
            BigDecimal bracketWidth = currentBracket.subtract(previousBracket);
            BigDecimal taxableAtThisBracket = remainingIncome.min(bracketWidth);
            
            if (i > 0) { // Skip the first bracket (0% rate)
                BigDecimal taxAtBracket = taxableAtThisBracket.multiply(TAX_RATES[i])
                                                             .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                tax = tax.add(taxAtBracket);
            }
            
            remainingIncome = remainingIncome.subtract(taxableAtThisBracket);
            previousBracket = currentBracket;
        }

        // Apply the highest rate to any remaining income
        if (remainingIncome.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal taxAtHighestRate = remainingIncome.multiply(TAX_RATES[TAX_RATES.length - 1])
                                                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            tax = tax.add(taxAtHighestRate);
        }

        return tax;
    }

    /**
     * Calculate net salary
     */
    public BigDecimal calculateNetSalary(BigDecimal grossSalary) {
        BigDecimal socialSecurityDeductions = calculateTotalEmployeeDeductions(grossSalary);
        BigDecimal taxableIncome = grossSalary.subtract(socialSecurityDeductions);
        BigDecimal incomeTax = calculateIncomeTax(taxableIncome);
        
        return grossSalary.subtract(socialSecurityDeductions).subtract(incomeTax);
    }

    /**
     * Calculate complete payroll for an employee
     */
    public PayrollResult calculatePayroll(BigDecimal grossSalary) {
        BigDecimal cnssEmployee = calculateCnssEmployee(grossSalary);
        BigDecimal amoEmployee = calculateAmoEmployee(grossSalary);
        BigDecimal totalEmployeeDeductions = cnssEmployee.add(amoEmployee);
        
        BigDecimal taxableIncome = grossSalary.subtract(totalEmployeeDeductions);
        BigDecimal incomeTax = calculateIncomeTax(taxableIncome);
        BigDecimal netSalary = grossSalary.subtract(totalEmployeeDeductions).subtract(incomeTax);
        
        BigDecimal cnssEmployer = calculateCnssEmployer(grossSalary);
        BigDecimal amoEmployer = calculateAmoEmployer(grossSalary);
        BigDecimal formation = calculateFormationProfessionnelle(grossSalary);
        BigDecimal totalEmployerContributions = cnssEmployer.add(amoEmployer).add(formation);
        
        BigDecimal totalCost = grossSalary.add(totalEmployerContributions);

        return new PayrollResult(
            grossSalary, netSalary, totalCost,
            cnssEmployee, amoEmployee, incomeTax,
            cnssEmployer, amoEmployer, formation,
            totalEmployeeDeductions, totalEmployerContributions
        );
    }

    // Result class
    public static class PayrollResult {
        private final BigDecimal grossSalary;
        private final BigDecimal netSalary;
        private final BigDecimal totalCost;
        private final BigDecimal cnssEmployee;
        private final BigDecimal amoEmployee;
        private final BigDecimal incomeTax;
        private final BigDecimal cnssEmployer;
        private final BigDecimal amoEmployer;
        private final BigDecimal formation;
        private final BigDecimal totalEmployeeDeductions;
        private final BigDecimal totalEmployerContributions;

        public PayrollResult(BigDecimal grossSalary, BigDecimal netSalary, BigDecimal totalCost,
                           BigDecimal cnssEmployee, BigDecimal amoEmployee, BigDecimal incomeTax,
                           BigDecimal cnssEmployer, BigDecimal amoEmployer, BigDecimal formation,
                           BigDecimal totalEmployeeDeductions, BigDecimal totalEmployerContributions) {
            this.grossSalary = grossSalary;
            this.netSalary = netSalary;
            this.totalCost = totalCost;
            this.cnssEmployee = cnssEmployee;
            this.amoEmployee = amoEmployee;
            this.incomeTax = incomeTax;
            this.cnssEmployer = cnssEmployer;
            this.amoEmployer = amoEmployer;
            this.formation = formation;
            this.totalEmployeeDeductions = totalEmployeeDeductions;
            this.totalEmployerContributions = totalEmployerContributions;
        }

        // Getters
        public BigDecimal getGrossSalary() { return grossSalary; }
        public BigDecimal getNetSalary() { return netSalary; }
        public BigDecimal getTotalCost() { return totalCost; }
        public BigDecimal getCnssEmployee() { return cnssEmployee; }
        public BigDecimal getAmoEmployee() { return amoEmployee; }
        public BigDecimal getIncomeTax() { return incomeTax; }
        public BigDecimal getCnssEmployer() { return cnssEmployer; }
        public BigDecimal getAmoEmployer() { return amoEmployer; }
        public BigDecimal getFormation() { return formation; }
        public BigDecimal getTotalEmployeeDeductions() { return totalEmployeeDeductions; }
        public BigDecimal getTotalEmployerContributions() { return totalEmployerContributions; }
    }
}
