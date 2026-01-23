# 🏦 WillBank — Digital Banking Platform (Microservices Architecture)

## 📌 Présentation Générale

**WillBank** est une plateforme bancaire digitale complète développée autour d’une **architecture microservices moderne**, sécurisée et scalable.  
Le projet vise à simuler un système bancaire réel permettant à des **clients** de gérer leurs comptes et à des **administrateurs** de superviser l’ensemble de l’activité via un CRM dédié.

Ce projet a été conçu comme un **cas pratique avancé** combinant :
- architecture distribuée,
- sécurité,
- communication inter-services,
- frontend moderne,
- et bonnes pratiques industrielles.

---

## 🚀 Départ du Projet — Motivations

L’objectif initial de WillBank était de :

- Comprendre **en profondeur les architectures microservices**
- Mettre en pratique **Spring Cloud, JWT, RabbitMQ et JPA**
- Simuler des **règles métier bancaires réelles**
- Créer une application **fullstack complète**, exploitable côté client et administrateur
- Concevoir une base solide pour un **CRM bancaire évolutif**

WillBank est né d’une volonté de **passer du CRUD simple à une architecture bancaire réaliste**.

---

## 💡 Concept du Projet

WillBank repose sur une séparation claire des responsabilités :

### 👤 Côté Client
- Gestion de compte bancaire
- Dépôts et retraits
- Historique des transactions
- Notifications système
- Interface moderne et sécurisée

### 🧑‍💼 Côté Administrateur (CRM en cours)
- Gestion complète des clients
- Supervision des comptes
- Analyse des transactions
- Actions administratives (suspension, blocage)
- Statistiques et rapports

---

## 🏗️ Architecture Technique

### 🔷 Microservices Backend

| Service | Technologie | Port | Rôle |
|------|-----------|------|------|
| Eureka Server | Spring Cloud Netflix | 8761 | Registre des services |
| API Gateway | Spring Cloud Gateway | 8080 | Point d’entrée unique + sécurité |
| Auth Service | Spring Boot + JWT | 8086 | Authentification |
| Client Service | Spring Boot + JPA | 8081 | Gestion clients |
| Compte Service | Spring Boot + JPA | 8082 | Gestion comptes bancaires |
| Transaction Service | Spring Boot + JPA | 8083 | Transactions |
| Notification Service | Spring Boot + JPA | 8084 | Notifications |
| Composite Service | Spring Boot + Feign | 8085 | Agrégation (BFF) |

---

## 🛠️ Stack Technique

### Backend
- **Java 21**
- **Spring Boot 4**
- **Spring Cloud (Eureka, Gateway, OpenFeign)**
- **Spring Security + JWT**
- **JPA / Hibernate**
- **RabbitMQ (Event-driven)**
- **MySQL (Database per Service)**

### Frontend Client
- **React 18 + Vite**
- **Tailwind CSS**
- **Axios**
- **React Router**
- **Lucide React**

---

## 🔄 Communication Inter-Services

### Synchrone (REST / Feign)
- Vérification client → compte
- Mise à jour solde → transaction

### Asynchrone (RabbitMQ)
- ClientCreatedEvent
- AccountCreatedEvent
- TransactionCompletedEvent
- ClientSuspendedEvent

---

## 🔐 Sécurité

- Authentification JWT centralisée
- Validation globale via API Gateway
- Routes publiques : `/auth/**`
- Routes protégées : toutes les autres
- Transmission du contexte utilisateur (username, rôle)

---

## 📊 Fonctionnalités Clés

- ✔️ Un seul compte courant par client
- ✔️ Vérification du solde avant retrait
- ✔️ Blocage automatique des comptes
- ✔️ Notifications persistées
- ✔️ Dashboard agrégé via BFF
- ✔️ Frontend moderne et responsive

---

## 📦 Bases de Données

- Architecture **Database per Service**
- 1 base MySQL par microservice
- Isolation complète des données
- Communication via API et événements uniquement

---

## 🧪 Tests Réalisés

- Parcours client complet
- Sécurité JWT valide / invalide
- Communication Feign
- Événements RabbitMQ
- Règles métier bancaires

---

## 🧭 Roadmap

### Prochaines évolutions
- 📊 Analytics & graphiques
- 📄 Export PDF
- 🔔 WebSocket (notifications temps réel)
- 🧑‍💼 CRM Admin complet
- 📱 Application mobile React Native

---

## 📌 Conclusion

WillBank est une **plateforme bancaire complète**, conçue comme un projet d’architecture avancée, intégrant sécurité, scalabilité et expérience utilisateur moderne.

> Un projet pensé comme un **socle professionnel**, prêt à évoluer vers un environnement de production.
