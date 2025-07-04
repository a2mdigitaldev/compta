package com.comptamaroc.clients.controller;

import com.comptamaroc.clients.model.Client;
import com.comptamaroc.clients.service.ClientService;
import com.comptamaroc.core.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Client>>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<Client> clients = clientService.getAllClients(pageable);
            
            return ResponseEntity.ok(ApiResponse.success("Clients retrieved successfully", clients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving clients", e.getMessage()));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Client>>> getAllActiveClients() {
        try {
            List<Client> clients = clientService.getAllActiveClients();
            return ResponseEntity.ok(ApiResponse.success("Active clients retrieved successfully", clients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving active clients", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> getClientById(@PathVariable Long id) {
        try {
            return clientService.getClientById(id)
                .map(client -> ResponseEntity.ok(ApiResponse.success("Client found", client)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Client not found", "No client found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving client", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Client>>> searchClients(@RequestParam String name) {
        try {
            List<Client> clients = clientService.searchClientsByName(name);
            return ResponseEntity.ok(ApiResponse.success("Search completed successfully", clients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error searching clients", e.getMessage()));
        }
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<Client>>> getClientsByCity(@PathVariable String city) {
        try {
            List<Client> clients = clientService.getActiveClientsByCity(city);
            return ResponseEntity.ok(ApiResponse.success("Clients retrieved successfully", clients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error retrieving clients by city", e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getActiveClientsCount() {
        try {
            long count = clientService.getActiveClientsCount();
            return ResponseEntity.ok(ApiResponse.success("Client count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error getting client count", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Client>> createClient(@Valid @RequestBody Client client) {
        try {
            Client createdClient = clientService.createClient(client);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Client created successfully", createdClient));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Failed to create client", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error creating client", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> updateClient(
            @PathVariable Long id, 
            @Valid @RequestBody Client clientDetails) {
        try {
            Client updatedClient = clientService.updateClient(id, clientDetails);
            return ResponseEntity.ok(ApiResponse.success("Client updated successfully", updatedClient));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Failed to update client", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error updating client", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateClient(@PathVariable Long id) {
        try {
            clientService.deactivateClient(id);
            return ResponseEntity.ok(ApiResponse.success("Client deactivated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Failed to deactivate client", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error deactivating client", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok(ApiResponse.success("Client deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Failed to delete client", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error deleting client", e.getMessage()));
        }
    }

    @GetMapping("/email/exists")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(@RequestParam String email) {
        try {
            boolean exists = clientService.emailExists(email);
            return ResponseEntity.ok(ApiResponse.success("Email availability checked", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error checking email availability", e.getMessage()));
        }
    }

    @GetMapping("/tax-id/exists")
    public ResponseEntity<ApiResponse<Boolean>> checkTaxIdExists(@RequestParam String taxId) {
        try {
            boolean exists = clientService.taxIdExists(taxId);
            return ResponseEntity.ok(ApiResponse.success("Tax ID availability checked", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Error checking tax ID availability", e.getMessage()));
        }
    }
}
