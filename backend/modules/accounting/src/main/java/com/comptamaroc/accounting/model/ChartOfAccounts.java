package com.comptamaroc.accounting.model;

import com.comptamaroc.core.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "chart_of_accounts")
public class ChartOfAccounts extends BaseEntity {

    @NotBlank(message = "Account code is required")
    @Size(max = 20, message = "Account code must not exceed 20 characters")
    @Column(name = "account_code", nullable = false, unique = true, length = 20)
    private String accountCode;

    @NotBlank(message = "Account name is required")
    @Size(max = 100, message = "Account name must not exceed 100 characters")
    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @NotNull(message = "Account type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @NotNull(message = "Account category is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "account_category", nullable = false)
    private AccountCategory accountCategory;

    @Size(max = 20, message = "Parent account code must not exceed 20 characters")
    @Column(name = "parent_account_code", length = 20)
    private String parentAccountCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Lob
    @Column(name = "description")
    private String description;

    // Morocco specific - PCMN (Plan Comptable Marocain Normalisé)
    @Size(max = 10, message = "PCMN code must not exceed 10 characters")
    @Column(name = "pcmn_code", length = 10)
    private String pcmnCode;

    // Constructors
    public ChartOfAccounts() {}

    public ChartOfAccounts(String accountCode, String accountName, AccountType accountType, AccountCategory accountCategory) {
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.accountType = accountType;
        this.accountCategory = accountCategory;
        this.isActive = true;
    }

    // Getters and setters
    public String getAccountCode() { return accountCode; }
    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public AccountCategory getAccountCategory() { return accountCategory; }
    public void setAccountCategory(AccountCategory accountCategory) { this.accountCategory = accountCategory; }

    public String getParentAccountCode() { return parentAccountCode; }
    public void setParentAccountCode(String parentAccountCode) { this.parentAccountCode = parentAccountCode; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPcmnCode() { return pcmnCode; }
    public void setPcmnCode(String pcmnCode) { this.pcmnCode = pcmnCode; }

    // Enums
    public enum AccountType {
        ASSET,      // Actif
        LIABILITY,  // Passif
        EQUITY,     // Capitaux propres
        REVENUE,    // Produits
        EXPENSE     // Charges
    }

    public enum AccountCategory {
        // Assets
        CURRENT_ASSETS,         // Actif circulant
        FIXED_ASSETS,          // Actif immobilisé
        CASH_AND_EQUIVALENTS,  // Trésorerie
        
        // Liabilities
        CURRENT_LIABILITIES,   // Passif circulant
        LONG_TERM_LIABILITIES, // Passif non circulant
        
        // Equity
        CAPITAL,               // Capital
        RESERVES,              // Réserves
        RETAINED_EARNINGS,     // Report à nouveau
        
        // Revenue
        OPERATING_REVENUE,     // Produits d'exploitation
        FINANCIAL_REVENUE,     // Produits financiers
        EXCEPTIONAL_REVENUE,   // Produits exceptionnels
        
        // Expenses
        OPERATING_EXPENSES,    // Charges d'exploitation
        FINANCIAL_EXPENSES,    // Charges financières
        EXCEPTIONAL_EXPENSES,  // Charges exceptionnelles
        TAX_EXPENSES          // Impôts et taxes
    }
}
