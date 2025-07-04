package com.comptamaroc.clients.repository;

import com.comptamaroc.clients.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Optional<Client> findByEmail(String email);
    
    Optional<Client> findByTaxId(String taxId);
    
    List<Client> findByIsActiveTrue();
    
    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Client> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT c FROM Client c WHERE c.city = :city AND c.isActive = true")
    List<Client> findActiveClientsByCity(@Param("city") String city);
    
    @Query("SELECT COUNT(c) FROM Client c WHERE c.isActive = true")
    long countActiveClients();
    
    boolean existsByEmail(String email);
    
    boolean existsByTaxId(String taxId);
}
