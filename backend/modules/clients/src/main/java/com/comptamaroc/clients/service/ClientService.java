package com.comptamaroc.clients.service;

import com.comptamaroc.clients.model.Client;
import com.comptamaroc.clients.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Client> getAllActiveClients() {
        return clientRepository.findByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientByTaxId(String taxId) {
        return clientRepository.findByTaxId(taxId);
    }

    @Transactional(readOnly = true)
    public List<Client> searchClientsByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Client> getActiveClientsByCity(String city) {
        return clientRepository.findActiveClientsByCity(city);
    }

    @Transactional(readOnly = true)
    public long getActiveClientsCount() {
        return clientRepository.countActiveClients();
    }

    public Client createClient(Client client) {
        validateClient(client);
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        return clientRepository.findById(id)
                .map(existingClient -> {
                    updateClientFields(existingClient, clientDetails);
                    validateClient(existingClient);
                    return clientRepository.save(existingClient);
                })
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    public void deactivateClient(Long id) {
        clientRepository.findById(id)
                .map(client -> {
                    client.setIsActive(false);
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean taxIdExists(String taxId) {
        return clientRepository.existsByTaxId(taxId);
    }

    private void validateClient(Client client) {
        if (client.getEmail() != null && clientRepository.existsByEmail(client.getEmail())) {
            // Check if it's a different client with the same email
            Optional<Client> existingClient = clientRepository.findByEmail(client.getEmail());
            if (existingClient.isPresent() && !existingClient.get().getId().equals(client.getId())) {
                throw new RuntimeException("Email already exists: " + client.getEmail());
            }
        }

        if (client.getTaxId() != null && clientRepository.existsByTaxId(client.getTaxId())) {
            // Check if it's a different client with the same tax ID
            Optional<Client> existingClient = clientRepository.findByTaxId(client.getTaxId());
            if (existingClient.isPresent() && !existingClient.get().getId().equals(client.getId())) {
                throw new RuntimeException("Tax ID already exists: " + client.getTaxId());
            }
        }
    }

    private void updateClientFields(Client existingClient, Client clientDetails) {
        if (clientDetails.getName() != null) {
            existingClient.setName(clientDetails.getName());
        }
        if (clientDetails.getContactPerson() != null) {
            existingClient.setContactPerson(clientDetails.getContactPerson());
        }
        if (clientDetails.getEmail() != null) {
            existingClient.setEmail(clientDetails.getEmail());
        }
        if (clientDetails.getPhone() != null) {
            existingClient.setPhone(clientDetails.getPhone());
        }
        if (clientDetails.getAddress() != null) {
            existingClient.setAddress(clientDetails.getAddress());
        }
        if (clientDetails.getCity() != null) {
            existingClient.setCity(clientDetails.getCity());
        }
        if (clientDetails.getPostalCode() != null) {
            existingClient.setPostalCode(clientDetails.getPostalCode());
        }
        if (clientDetails.getCountry() != null) {
            existingClient.setCountry(clientDetails.getCountry());
        }
        if (clientDetails.getTaxId() != null) {
            existingClient.setTaxId(clientDetails.getTaxId());
        }
        if (clientDetails.getIsActive() != null) {
            existingClient.setIsActive(clientDetails.getIsActive());
        }
        if (clientDetails.getNotes() != null) {
            existingClient.setNotes(clientDetails.getNotes());
        }
    }
}
