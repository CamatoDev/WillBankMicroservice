/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.client.service;

/**
 *
 * @author DELL
 */

import com.vortexmakers.client.entity.Client;
import com.vortexmakers.client.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        // Si le front n’envoie rien
        if (client.getAddress() == null) {
            client.setAddress("N/A");
        }

        if (client.getStatus() == null) {
            client.setStatus(Client.Status.ACTIVE);
        }

        if (client.getCreatedBy() == null) {
            client.setCreatedBy("SYSTEM");
        }
        
        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(UUID id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Optional<Client> getByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void suspendClient(UUID id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setStatus(Client.Status.SUSPENDED);
            clientRepository.save(client);
        });
    }

    public void activateClient(UUID id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setStatus(Client.Status.ACTIVE);
            clientRepository.save(client);
        });
    }
}

