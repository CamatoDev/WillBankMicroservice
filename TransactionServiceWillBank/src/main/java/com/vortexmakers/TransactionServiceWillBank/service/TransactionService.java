/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.TransactionServiceWillBank.service;

/**
 *
 * @author DELL
 */
import com.vortexmakers.TransactionServiceWillBank.client.AccountDto;
import com.vortexmakers.TransactionServiceWillBank.client.AccountFeignClient;
import com.vortexmakers.TransactionServiceWillBank.client.BalanceUpdateRequest;
import com.vortexmakers.TransactionServiceWillBank.entity.Transaction;
import com.vortexmakers.TransactionServiceWillBank.repository.TransactionRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountFeignClient accountFeignClient;

    public TransactionService(TransactionRepository repository, AccountFeignClient accountFeignClient) {
        this.repository = repository;
        this.accountFeignClient = accountFeignClient;
    }

    public Transaction create(Transaction transaction) {
        try {
            // Vérifier que le compte existe et est actif
            AccountDto account = accountFeignClient.getAccountById(transaction.getAccountId());

            if (!"ACTIVE".equals(account.getStatus())) {
                transaction.setStatus(Transaction.TransactionStatus.FAILED);
                transaction.setFailureReason("Le compte n'est pas actif");
                return repository.save(transaction);
            }

            // Vérifier les règles métier selon le type de transaction
            if (transaction.getType() == Transaction.TransactionType.WITHDRAWAL) {
                // Vérification du solde suffisant pour retrait
                if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
                    transaction.setStatus(Transaction.TransactionStatus.FAILED);
                    transaction.setFailureReason("Solde insuffisant");
                    return repository.save(transaction);
                }
            }

            // Déterminer l'opération sur le solde
            String operation;
            switch (transaction.getType()) {
                case DEPOSIT:
                    operation = "ADD";
                    break;
                case WITHDRAWAL:
                case PAYMENT:
                    operation = "SUBTRACT";
                    break;
                case TRANSFER:
                    // Pour un transfert on suppose qu'on débite le compte
                    operation = "SUBTRACT";
                    break;
                default:
                    throw new IllegalArgumentException("Type de transaction non supporté");
            }

            // Mettre à jour le solde du compte
            BalanceUpdateRequest request = new BalanceUpdateRequest(
                    transaction.getAmount(),
                    operation
            );
            accountFeignClient.updateBalance(transaction.getAccountId(), request);

            // Marquer la transaction comme réussie
            transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
            return repository.save(transaction);

        } catch (FeignException.NotFound e) {
            // Compte introuvable
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
            transaction.setFailureReason("Compte introuvable");
            return repository.save(transaction);

        } catch (FeignException e) {
            // Erreur lors de la communication avec le Compte Service
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
            transaction.setFailureReason("Erreur lors de la mise à jour du solde : " + e.getMessage());
            return repository.save(transaction);

        } catch (Exception e) {
            // Toute autre erreur
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
            transaction.setFailureReason("Erreur interne : " + e.getMessage());
            return repository.save(transaction);
        }
    }

    public List<Transaction> getByAccount(UUID accountId) {
        return repository.findByAccountId(accountId);
    }
}