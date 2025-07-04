package com.comptamaroc.invoices.dto;

import com.comptamaroc.invoices.model.Invoice;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceDTO {
    
    private Long id;
    
    @NotBlank(message = "Invoice number is required")
    @Size(max = 50, message = "Invoice number must not exceed 50 characters")
    private String invoiceNumber;
    
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    private String clientName;
    private String clientEmail;
    
    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;
    
    private LocalDate dueDate;
    
    @NotNull(message = "Status is required")
    private Invoice.InvoiceStatus status;
    
    @NotNull(message = "Subtotal is required")
    @DecimalMin(value = "0.0", message = "Subtotal must be greater than or equal to 0")
    private BigDecimal subtotal;
    
    @NotNull(message = "VAT amount is required")
    @DecimalMin(value = "0.0", message = "VAT amount must be greater than or equal to 0")
    private BigDecimal vatAmount;
    
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", message = "Total amount must be greater than or equal to 0")
    private BigDecimal totalAmount;
    
    @DecimalMin(value = "0.0", message = "Paid amount must be greater than or equal to 0")
    private BigDecimal paidAmount = BigDecimal.ZERO;
    
    private String notes;
    
    private List<InvoiceItemDTO> items;
    
    // Constructors
    public InvoiceDTO() {}
    
    public InvoiceDTO(Long id, String invoiceNumber, Long clientId, String clientName, 
                     LocalDate invoiceDate, Invoice.InvoiceStatus status, BigDecimal totalAmount) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.clientId = clientId;
        this.clientName = clientName;
        this.invoiceDate = invoiceDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Invoice.InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(Invoice.InvoiceStatus status) {
        this.status = status;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<InvoiceItemDTO> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemDTO> items) {
        this.items = items;
    }

    // Business methods
    public BigDecimal getBalanceDue() {
        return totalAmount.subtract(paidAmount != null ? paidAmount : BigDecimal.ZERO);
    }

    public boolean isFullyPaid() {
        return paidAmount != null && paidAmount.compareTo(totalAmount) >= 0;
    }

    public boolean isOverdue() {
        return dueDate != null && LocalDate.now().isAfter(dueDate) && !isFullyPaid();
    }
}
