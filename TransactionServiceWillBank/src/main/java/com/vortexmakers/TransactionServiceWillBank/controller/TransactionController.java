/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.TransactionServiceWillBank.controller;

/**
 *
 * @author DELL
 */
import com.vortexmakers.TransactionServiceWillBank.entity.Transaction;
import com.vortexmakers.TransactionServiceWillBank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(service.create(transaction));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getByAccount(@PathVariable UUID accountId) {
        return ResponseEntity.ok(service.getByAccount(accountId));
    }

    // Endpoint pour effectuer un dépôt
    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody DepositWithdrawRequest request) {
        try {
            Transaction transaction = service.deposit(request.getAccountId(), request.getAmount());
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint pour effectuer un retrait
    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody DepositWithdrawRequest request) {
        try {
            Transaction transaction = service.withdraw(request.getAccountId(), request.getAmount());
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint pour effectuer un virement
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequest request) {
        try {
            Transaction transaction = service.transfer(
                request.getSourceAccountId(), 
                request.getTargetAccountId(), 
                request.getAmount()
            );
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DTO pour les requêtes de dépôt/retrait
    public static class DepositWithdrawRequest {
        private UUID accountId;
        private BigDecimal amount;

        public UUID getAccountId() { return accountId; }
        public void setAccountId(UUID accountId) { this.accountId = accountId; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }

    // DTO pour les requêtes de virement
    public static class TransferRequest {
        private UUID sourceAccountId;
        private UUID targetAccountId;
        private BigDecimal amount;

        public UUID getSourceAccountId() { return sourceAccountId; }
        public void setSourceAccountId(UUID sourceAccountId) { this.sourceAccountId = sourceAccountId; }
        public UUID getTargetAccountId() { return targetAccountId; }
        public void setTargetAccountId(UUID targetAccountId) { this.targetAccountId = targetAccountId; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }
}
