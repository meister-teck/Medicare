# MediCare - Application de Suivi Médical

## 📌 Présentation
MediCare est une solution complète de suivi médical conçue pour aider les utilisateurs à gérer facilement leurs traitements, leurs conditions de santé et leurs rappels de médicaments. Le projet est divisé en deux parties : une application mobile native Android garantissant une expérience utilisateur fluide, et une API REST robuste assurant la gestion et la sécurité des données.

## 📸 Aperçu de l'application
*(Remplacer par les liens vers vos captures d'écran après les avoir ajoutées dans le dossier docs)*
`![Écran d'accueil](docs/screenshots/accueil.png)`
`![Liste des médicaments](docs/screenshots/medicaments.png)`

## 🛠️ Architecture et Technologies

### Backend (API REST)
* **Langage & Framework :** Java 17, Spring Boot
* **Sécurité :** Spring Security, Authentification par Token (JWT)
* **Persistance des données :** Hibernate / Spring Data JPA
* **Services Tiers :** Firebase Cloud Messaging (FCM) pour les notifications push

### Application Mobile (Android)
* **Langage & UI :** Kotlin, Jetpack Compose
* **Architecture :** MVVM (Model-View-ViewModel) et Clean Architecture
* **Réseau & Cache :** Retrofit (appels API) et Room (base de données locale)
* **Injection de dépendances :** Dagger Hilt

## 📂 Structure du Répertoire

* `/backend` : Code source et fichiers de configuration de l'API Spring Boot.
* `/mobile` : Code source du client Android natif.
* `/docs` : Contient le rapport complet du projet, les diagrammes et les captures d'écran.

## 🚀 Installation et Lancement

### 1. Configuration du Backend
1. S'assurer qu'une base de données compatible est configurée selon les paramètres de `application.yml`.
2. Ajouter le fichier d'authentification `firebase-service-account.json` dans `backend/src/main/resources/`.
3. Se placer dans le dossier backend et lancer l'application :
```bash
   cd backend
   ./mvnw spring-boot:run