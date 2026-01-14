/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.CompteServiceWillBank.service;

/**
 *
 * @author DELL
 */
import com.vortexmakers.CompteServiceWillBank.client.ClientDto;
import com.vortexmakers.CompteServiceWillBank.dto.BalanceUpdateRequest;
import com.vortexmakers.CompteServiceWillBank.client.ClientFeignClient;
import com.vortexmakers.CompteServiceWillBank.entity.Account;
import com.vortexmakers.CompteServiceWillBank.repository.AccountRepository;
import feign.FeignException;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final ClientFeignClient clientFeignClient;

    public AccountService(AccountRepository repository, ClientFeignClient clientFeignClient) {
        this.repository = repository;
        this.clientFeignClient = clientFeignClient;
    }

    public Account createAccount(Account account) {

        // Vérifier que le client existe
        try {
            ClientDto client = clientFeignClient.getClientById(account.getCustomerId());
            
            // Vérifier que le client est actif
            if (!"ACTIVE".equals(client.getStatus())) {
                throw new IllegalStateException(
                    "Impossible de créer un compte pour un client non actif"
                );
            }
            
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException(
                "Client introuvable avec l'ID : " + account.getCustomerId()
            );
        } catch (FeignException e) {
            throw new RuntimeException(
                "Erreur lors de la vérification du client : " + e.getMessage()
            );
        }

        // Règle : un seul compte courant par client
        if (account.getType() == Account.AccountType.CURRENT) {
            repository.findByCustomerIdAndType(
                    account.getCustomerId(),
                    Account.AccountType.CURRENT
            ).ifPresent(a -> {
                throw new IllegalStateException(
                        "Un client ne peut avoir qu'un seul compte courant"
                );
            });
        }

        // Initialiser le compte
        account.setStatus(Account.AccountStatus.ACTIVE);
        account.setCreatedAt(LocalDateTime.now());

        return repository.save(account);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public Account getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Compte introuvable")
                );
    }
    
    // Récupérer les comptes d'un client
    public List<Account> getByCustomerId(UUID customerId) {
        return repository.findByCustomerId(customerId);
    }

    public void closeAccount(UUID id) {
        Account account = getById(id);

        if (account.getBalance().signum() != 0) {
            throw new IllegalStateException(
                    "Impossible de fermer un compte avec un solde non nul"
            );
        }

        account.setStatus(Account.AccountStatus.CLOSED);
        account.setUpdatedAt(LocalDateTime.now());

        repository.save(account);
    }
    
    // Mise à jour du solde (appelé par le Transaction Service)
    public void updateBalance(UUID accountId, BalanceUpdateRequest request) {
        Account account = getById(accountId);

        if (account.getStatus() != Account.AccountStatus.ACTIVE) {
            throw new IllegalStateException("Le compte n'est pas actif");
        }

        BigDecimal newBalance;

        if ("ADD".equals(request.getOperation())) {
            newBalance = account.getBalance().add(request.getAmount());
        } else if ("SUBTRACT".equals(request.getOperation())) {
            newBalance = account.getBalance().subtract(request.getAmount());

            // Vérification pas de solde négatif
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("Solde insuffisant");
            }
        } else {
            throw new IllegalArgumentException("Opération invalide : " + request.getOperation());
        }

        account.setBalance(newBalance);
        account.setUpdatedAt(LocalDateTime.now());
        repository.save(account);
    }
    
    // Bloquer tous les comptes d'un client (événement ClientSuspended)
    public void blockAccountsByCustomerId(UUID customerId) {
        List<Account> accounts = repository.findByCustomerId(customerId);

        for (Account account : accounts) {
            if (account.getStatus() == Account.AccountStatus.ACTIVE) {
                account.setStatus(Account.AccountStatus.BLOCKED);
                account.setUpdatedAt(LocalDateTime.now());
                repository.save(account);
                System.out.println("Compte " + account.getId() + " bloqué");
            }
        }
    }
}