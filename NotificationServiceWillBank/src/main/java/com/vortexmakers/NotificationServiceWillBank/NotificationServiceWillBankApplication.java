package com.vortexmakers.NotificationServiceWillBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationServiceWillBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceWillBankApplication.class, args);
    }
}