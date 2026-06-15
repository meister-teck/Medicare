# MediCare - Application de Suivi Médical

## 📌 Présentation
MediCare est une application complète permettant aux utilisateurs de gérer facilement leurs traitements médicaux, leurs conditions de santé et de recevoir des rappels pour la prise de leurs médicaments. 

Le projet adopte une architecture client-serveur claire, séparée en deux environnements distincts.

## 🛠️ Technologies Utilisées

**Backend (API REST) :**
* **Framework :** Java, Spring Boot
* **Sécurité :** Spring Security, JWT (JSON Web Tokens)
* **Notifications :** Firebase Cloud Messaging (FCM)

**Mobile (Client Android) :**
* **Interface & Langage :** Kotlin, Jetpack Compose
* **Architecture :** MVVM, Clean Architecture
* **Outils :** Room (Cache local), Retrofit (Appels API), Dagger Hilt (Injection de dépendances)

## 📂 Structure du Dépôt

* `/backend` : Code source et configuration du serveur Spring Boot.
* `/Mobile` : Code source de l'application Android native.

## 🚀 Lancement Rapide

### 1. Démarrer le Backend
1. Ouvrez le dossier `/backend` dans votre IDE (IntelliJ / Eclipse).
2. Vérifiez la configuration de la base de données dans `application.yml`.
3. Lancez l'application via la classe `BackendApplication.java` ou via le terminal : `./mvnw spring-boot:run`.

### 2. Démarrer l'Application Mobile
1. Ouvrez le dossier `/Mobile` directement dans Android Studio.
2. Laissez Gradle synchroniser le projet.
3. Lancez l'application sur un émulateur ou un appareil physique.