/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.NotificationServiceWillBank.event;

/**
 *
 * @author DELL
 */
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    @RabbitListener(queues = "notification.transaction.events")
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        System.out.println("");
        System.out.println("NOTIFICATION - Transaction complétée");
        System.out.println("");
        System.out.println("Transaction ID: " + event.getTransactionId());
        System.out.println("Compte ID: " + event.getAccountId());
        System.out.println("Type: " + event.getType());
        System.out.println("Montant: " + event.getAmount());
        System.out.println("Date: " + event.getCompletedAt());
        System.out.println("");
    }

    @RabbitListener(queues = "notification.client.events")
    public void handleClientCreated(ClientCreatedEvent event) {
        System.out.println("");
        System.out.println("NOTIFICATION - Nouveau client créé");
        System.out.println("");
        System.out.println("Client ID: " + event.getClientId());
        System.out.println("Nom: " + event.getFirstName() + " " + event.getLastName());
        System.out.println("Email: " + event.getEmail());
        System.out.println("Téléphone: " + event.getPhone());
        System.out.println("Date: " + event.getCreatedAt());
        System.out.println("");
        System.out.println("Email de bienvenue envoyé à : " + event.getEmail());
        System.out.println("");
    }

    @RabbitListener(queues = "notification.account.events")
    public void handleAccountCreated(AccountCreatedEvent event) {
        System.out.println("");
        System.out.println("NOTIFICATION - Nouveau compte créé");
        System.out.println("");
        System.out.println("Compte ID: " + event.getAccountId());
        System.out.println("Client ID: " + event.getCustomerId());
        System.out.println("Type: " + event.getType());
        System.out.println("Solde initial: " + event.getBalance());
        System.out.println("Date: " + event.getCreatedAt());
        System.out.println("");
        System.out.println("Email de confirmation d'ouverture de compte envoyé");
        System.out.println("");
    }
    
    //Ici plus tard l'envoi d'email ou de notification push
}
