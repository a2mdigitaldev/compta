package com.comptamaroc.accounting.model;

import com.comptamaroc.core.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "journal_entries")
public class JournalEntry extends BaseEntity {

    @NotBlank(message = "Journal entry number is required")
    @Size(max = 50, message = "Journal entry number must not exceed 50 characters")
    @Column(name = "entry_number", nullable = false, unique = true, length = 50)
    private String entryNumber;

    @NotNull(message = "Entry date is required")
    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    @Column(name = "description", length = 200)
    private String description;

    @Size(max = 50, message = "Reference must not exceed 50 characters")
    @Column(name = "reference", length = 50)
    private String reference;

    @NotNull(message = "Entry type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false)
    private EntryType entryType;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntryStatus status = EntryStatus.DRAFT;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Size(max = 100, message = "Created by must not exceed 100 characters")
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "posted_date")
    private LocalDate postedDate;

    @Size(max = 100, message = "Posted by must not exceed 100 characters")
    @Column(name = "posted_by", length = 100)
    private String postedBy;

    // Constructors
    public JournalEntry() {}

    public JournalEntry(String entryNumber, LocalDate entryDate, String description, EntryType entryType, BigDecimal totalAmount) {
        this.entryNumber = entryNumber;
        this.entryDate = entryDate;
        this.description = description;
        this.entryType = entryType;
        this.totalAmount = totalAmount;
        this.status = EntryStatus.DRAFT;
    }

    // Getters and setters
    public String getEntryNumber() { return entryNumber; }
    public void setEntryNumber(String entryNumber) { this.entryNumber = entryNumber; }

    public LocalDate getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public EntryType getEntryType() { return entryType; }
    public void setEntryType(EntryType entryType) { this.entryType = entryType; }

    public EntryStatus getStatus() { return status; }
    public void setStatus(EntryStatus status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }

    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }

    // Enums
    public enum EntryType {
        SALES,           // Ventes
        PURCHASES,       // Achats
        PAYMENTS,        // Paiements
        RECEIPTS,        // Recettes
        ADJUSTMENTS,     // Ajustements
        OPENING_BALANCE, // Balance d'ouverture
        CLOSING,         // Clôture
        TRANSFER,        // Virement
        DEPRECIATION,    // Amortissement
        PROVISION        // Provision
    }

    public enum EntryStatus {
        DRAFT,      // Brouillon
        POSTED,     // Comptabilisé
        CANCELLED   // Annulé
    }
}
