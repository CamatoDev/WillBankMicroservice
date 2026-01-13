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
import org.springframework.web.bind.annotation.*;

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
    public Transaction create(@RequestBody Transaction transaction) {
        return service.create(transaction);
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getByAccount(@PathVariable UUID accountId) {
        return service.getByAccount(accountId);
    }
}
