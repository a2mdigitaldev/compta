package com.comptamaroc.suppliers.service;

import com.comptamaroc.suppliers.dto.SupplierRequest;
import com.comptamaroc.suppliers.dto.SupplierResponse;
import com.comptamaroc.suppliers.model.Supplier;
import com.comptamaroc.suppliers.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierResponse createSupplier(SupplierRequest request) {
        // Check if company name already exists
        if (supplierRepository.existsByCompanyNameIgnoreCase(request.getCompanyName())) {
            throw new RuntimeException("Supplier with company name '" + request.getCompanyName() + "' already exists");
        }

        // Check if email already exists
        if (request.getEmail() != null && !request.getEmail().isEmpty() && 
            supplierRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Supplier with email '" + request.getEmail() + "' already exists");
        }

        // Check if tax ID already exists
        if (request.getTaxId() != null && !request.getTaxId().isEmpty() && 
            supplierRepository.existsByTaxId(request.getTaxId())) {
            throw new RuntimeException("Supplier with tax ID '" + request.getTaxId() + "' already exists");
        }

        Supplier supplier = new Supplier();
        supplier.setCompanyName(request.getCompanyName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setPostalCode(request.getPostalCode());
        supplier.setCountry(request.getCountry());
        supplier.setTaxId(request.getTaxId());
        supplier.setNotes(request.getNotes());
        supplier.setIsActive(true);

        Supplier savedSupplier = supplierRepository.save(supplier);
        return mapToResponse(savedSupplier);
    }

    @Transactional(readOnly = true)
    public SupplierResponse getSupplierById(UUID id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        return mapToResponse(supplier);
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> getActiveSuppliers() {
        return supplierRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> searchSuppliers(String keyword) {
        return supplierRepository.searchSuppliers(keyword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SupplierResponse> getSuppliersPageable(Pageable pageable) {
        Page<Supplier> suppliers = supplierRepository.findAll(pageable);
        return suppliers.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<SupplierResponse> searchSuppliersPageable(String keyword, Pageable pageable) {
        Page<Supplier> suppliers = supplierRepository.searchSuppliersPageable(keyword, pageable);
        return suppliers.map(this::mapToResponse);
    }

    public SupplierResponse updateSupplier(UUID id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));

        // Check if company name already exists for another supplier
        if (!supplier.getCompanyName().equalsIgnoreCase(request.getCompanyName()) &&
            supplierRepository.existsByCompanyNameIgnoreCase(request.getCompanyName())) {
            throw new RuntimeException("Supplier with company name '" + request.getCompanyName() + "' already exists");
        }

        // Check if email already exists for another supplier
        if (request.getEmail() != null && !request.getEmail().isEmpty() &&
            !request.getEmail().equals(supplier.getEmail()) &&
            supplierRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Supplier with email '" + request.getEmail() + "' already exists");
        }

        // Check if tax ID already exists for another supplier
        if (request.getTaxId() != null && !request.getTaxId().isEmpty() &&
            !request.getTaxId().equals(supplier.getTaxId()) &&
            supplierRepository.existsByTaxId(request.getTaxId())) {
            throw new RuntimeException("Supplier with tax ID '" + request.getTaxId() + "' already exists");
        }

        supplier.setCompanyName(request.getCompanyName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setPostalCode(request.getPostalCode());
        supplier.setCountry(request.getCountry());
        supplier.setTaxId(request.getTaxId());
        supplier.setNotes(request.getNotes());

        Supplier updatedSupplier = supplierRepository.save(supplier);
        return mapToResponse(updatedSupplier);
    }

    public void deleteSupplier(UUID id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        
        supplier.setIsActive(false);
        supplierRepository.save(supplier);
    }

    public void activateSupplier(UUID id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        
        supplier.setIsActive(true);
        supplierRepository.save(supplier);
    }

    private SupplierResponse mapToResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setCompanyName(supplier.getCompanyName());
        response.setContactPerson(supplier.getContactPerson());
        response.setEmail(supplier.getEmail());
        response.setPhone(supplier.getPhone());
        response.setAddress(supplier.getAddress());
        response.setCity(supplier.getCity());
        response.setPostalCode(supplier.getPostalCode());
        response.setCountry(supplier.getCountry());
        response.setTaxId(supplier.getTaxId());
        response.setIsActive(supplier.getIsActive());
        response.setNotes(supplier.getNotes());
        response.setCreatedAt(supplier.getCreatedAt());
        response.setUpdatedAt(supplier.getUpdatedAt());
        return response;
    }
}
