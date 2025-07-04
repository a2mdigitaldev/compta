package com.comptamaroc.suppliers.repository;

import com.comptamaroc.suppliers.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    // Find suppliers by active status
    List<Supplier> findByIsActiveTrue();
    List<Supplier> findByIsActiveFalse();

    // Find by email
    Optional<Supplier> findByEmail(String email);

    // Find by company name (case insensitive)
    Optional<Supplier> findByCompanyNameIgnoreCase(String companyName);

    // Find by tax ID
    Optional<Supplier> findByTaxId(String taxId);

    // Search suppliers by company name containing keyword
    @Query("SELECT s FROM Supplier s WHERE LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) AND s.isActive = true")
    List<Supplier> findByCompanyNameContainingIgnoreCase(@Param("keyword") String keyword);

    // Search suppliers by multiple criteria
    @Query("SELECT s FROM Supplier s WHERE " +
           "(LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.contactPerson) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "s.isActive = true")
    List<Supplier> searchSuppliers(@Param("keyword") String keyword);

    // Find suppliers by city
    List<Supplier> findByCityIgnoreCaseAndIsActiveTrue(String city);

    // Find suppliers by country
    List<Supplier> findByCountryIgnoreCaseAndIsActiveTrue(String country);

    // Paginated search
    @Query("SELECT s FROM Supplier s WHERE " +
           "(LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.contactPerson) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "s.isActive = true")
    Page<Supplier> searchSuppliersPageable(@Param("keyword") String keyword, Pageable pageable);

    // Check if company name exists
    boolean existsByCompanyNameIgnoreCase(String companyName);

    // Check if email exists
    boolean existsByEmail(String email);

    // Check if tax ID exists
    boolean existsByTaxId(String taxId);
}
