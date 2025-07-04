package com.comptamaroc.accounting.model;

import com.comptamaroc.core.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "journal_entry_lines")
public class JournalEntryLine extends BaseEntity {

    @NotNull(message = "Journal entry ID is required")
    @Column(name = "journal_entry_id", nullable = false)
    private UUID journalEntryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_entry_id", insertable = false, updatable = false)
    private JournalEntry journalEntry;

    @NotBlank(message = "Account code is required")
    @Size(max = 20, message = "Account code must not exceed 20 characters")
    @Column(name = "account_code", nullable = false, length = 20)
    private String accountCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_code", referencedColumnName = "account_code", insertable = false, updatable = false)
    private ChartOfAccounts account;

    @Size(max = 200, message = "Line description must not exceed 200 characters")
    @Column(name = "line_description", length = 200)
    private String lineDescription;

    @DecimalMin(value = "0.0", message = "Debit amount must be greater than or equal to 0")
    @Column(name = "debit_amount", precision = 15, scale = 2)
    private BigDecimal debitAmount = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", message = "Credit amount must be greater than or equal to 0")
    @Column(name = "credit_amount", precision = 15, scale = 2)
    private BigDecimal creditAmount = BigDecimal.ZERO;

    @Column(name = "line_order", nullable = false)
    private Integer lineOrder;

    @Size(max = 50, message = "Reference must not exceed 50 characters")
    @Column(name = "reference", length = 50)
    private String reference;

    // Constructors
    public JournalEntryLine() {}

    public JournalEntryLine(UUID journalEntryId, String accountCode, String lineDescription, 
                           BigDecimal debitAmount, BigDecimal creditAmount, Integer lineOrder) {
        this.journalEntryId = journalEntryId;
        this.accountCode = accountCode;
        this.lineDescription = lineDescription;
        this.debitAmount = debitAmount != null ? debitAmount : BigDecimal.ZERO;
        this.creditAmount = creditAmount != null ? creditAmount : BigDecimal.ZERO;
        this.lineOrder = lineOrder;
    }

    // Getters and setters
    public UUID getJournalEntryId() { return journalEntryId; }
    public void setJournalEntryId(UUID journalEntryId) { this.journalEntryId = journalEntryId; }

    public JournalEntry getJournalEntry() { return journalEntry; }
    public void setJournalEntry(JournalEntry journalEntry) { this.journalEntry = journalEntry; }

    public String getAccountCode() { return accountCode; }
    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }

    public ChartOfAccounts getAccount() { return account; }
    public void setAccount(ChartOfAccounts account) { this.account = account; }

    public String getLineDescription() { return lineDescription; }
    public void setLineDescription(String lineDescription) { this.lineDescription = lineDescription; }

    public BigDecimal getDebitAmount() { return debitAmount; }
    public void setDebitAmount(BigDecimal debitAmount) { this.debitAmount = debitAmount; }

    public BigDecimal getCreditAmount() { return creditAmount; }
    public void setCreditAmount(BigDecimal creditAmount) { this.creditAmount = creditAmount; }

    public Integer getLineOrder() { return lineOrder; }
    public void setLineOrder(Integer lineOrder) { this.lineOrder = lineOrder; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    // Helper methods
    public BigDecimal getAmount() {
        return debitAmount.compareTo(BigDecimal.ZERO) > 0 ? debitAmount : creditAmount;
    }

    public boolean isDebit() {
        return debitAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isCredit() {
        return creditAmount.compareTo(BigDecimal.ZERO) > 0;
    }
}
