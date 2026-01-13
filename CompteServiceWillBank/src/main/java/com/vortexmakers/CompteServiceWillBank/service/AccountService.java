/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.CompteServiceWillBank.service;

/**
 *
 * @author DELL
 */
import com.vortexmakers.CompteServiceWillBank.entity.Account;
import com.vortexmakers.CompteServiceWillBank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    // Création 

    public Account createAccount(Account account) {

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
}

