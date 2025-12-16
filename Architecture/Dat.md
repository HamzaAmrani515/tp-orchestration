# DAT – Document d’Architecture Technique 

## 1. Vue d’ensemble
Plateforme e-commerce composée de 3 microservices :
- ms-membership : gestion des membres
- ms-product : gestion des produits
- ms-order : gestion des commandes + orchestration

Chaque service est autonome : code, déploiement, DB séparée.

## 2. Schéma d’architecture
Client -> ms-order (orchestration)
ms-order -> ms-membership (vérif membre)
ms-order -> ms-product (vérif produit/stock)
DB : H2 in-memory par service

## 3. Description des microservices

### 3.1 ms-membership (8081)
- CRUD minimum membres
- Validation Bean Validation
- DTO Request/Response
- Health custom (ex: compter membres)
- Métriques custom (ex: members_created_total)

### 3.2 ms-product (8082)
- CRUD minimum produits
- Recherche par catégorie + disponibles
- Validation + DTO
- Health custom (ex: produits en rupture)
- Métriques custom (ex: products_created_total, out_of_stock_count)

### 3.3 ms-order (8083)
- Créer commande en vérifiant :
    - membre existe
    - produit existe
    - stock suffisant
- DTO + Validation
- Gestion d’erreurs globale
- Health custom (ex: nb commandes)
- Métriques custom (ex: orders_created_total, orders_failed_total)

## 4. Choix technologiques (justification courte)
- Spring Boot : rapidité, standard entreprise
- Spring Web : REST
- Spring Data JPA : accès DB simple
- H2 : setup rapide pour TP
- Actuator + Micrometer : health + metrics
- Prometheus : collecte de métriques
- Swagger/OpenAPI : documentation + démo simple
- Lombok : réduire boilerplate

## 5. Communication inter-services
- REST synchrone (RestTemplate/WebClient)
- ms-order orchestre, appelle membership + product
- Timeouts & erreurs gérés via exceptions métiers + @ControllerAdvice

## 6. Gestion des données
- Base dédiée par microservice (H2 in-memory)
- Aucune jointure cross-service
- Identifiants transmis via REST (memberId, productId)

## 7. Gestion des erreurs & résilience
- Exceptions métiers : MemberNotFound / ProductNotFound / OutOfStock
- Retour JSON standardisé via @ControllerAdvice
- Codes HTTP : 400 / 404 / 409 / 500

## 8. Tests
- Minimum 3 tests unitaires par microservice :
    - service layer (métier)
    - controller (MockMvc)
    - repository (optionnel)
