
## Services & Ports
- ms-membership : `http://localhost:8081`
- ms-product    : `http://localhost:8082`
- ms-order      : `http://localhost:8083`

## Swagger
- membership: `http://localhost:8081/swagger-ui.html`
- product:    `http://localhost:8082/swagger-ui.html`
- order:      `http://localhost:8083/swagger-ui.html`

## Actuator / Health / Prometheus
- health: `http://localhost:808X/actuator/health`
- prometheus: `http://localhost:808X/actuator/prometheus`

## Démo (10 minutes) – Ce que tu montres au prof
1) **Lancer les 3 services**
- 3 terminaux :
    - `cd ms-membership && mvn spring-boot:run`
    - `cd ms-product && mvn spring-boot:run`
    - `cd ms-order && mvn spring-boot:run`

2) **Montrer l’architecture**
- expliquer : ms-order orchestre, ms-membership = membres, ms-product = produits, DB séparées.

3) **Scénario complet (happy path)**
- Créer membre (Swagger ms-membership)
    - `POST /api/v1/members`
- Créer 5 à 10 produits (Swagger ms-product)
    - `POST /api/v1/products` (répéter ou via data.sql)
- Créer commande (Swagger ms-order)
    - `POST /api/v1/orders` avec memberId + productId + quantity

4) **Health checks**
- ouvrir `/actuator/health` sur les 3 services

5) **Métriques Prometheus**
- ouvrir `/actuator/prometheus` sur les 3 services
- si docker monitoring activé : ouvrir Prometheus et requêter un metric custom

6) **Gestion d’erreur (2 min)**
- commande avec user inexistant
- commande avec produit en rupture (quantity=0 ou quantity<demandé)
- vérifier réponse HTTP + message d’erreur JSON

## Documentation
- Document d’architecture : `architecture/DAT.md`
- Guide déploiement : `DEPLOYMENT.md`
- Monitoring : `monitoring/MONITORING.md`
- Postman : `postman/platform-tests.json`

## Auteur
Hamza Amrani
