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
   ./mvnw spring-boot:run<<<<<<< HEAD
# MediCare (Android)

Application Android native (Kotlin + Jetpack Compose) pour le suivi de traitements
médicaux, consommant l'API Spring Boot MediCare.

## Configuration

1. Ouvrir le projet dans Android Studio (Hedgehog ou +).
2. Remplacer `app/google-services.json` par votre fichier Firebase réel
   (package : `com.medicare.app`).
3. Adapter `API_BASE_URL` dans `app/build.gradle.kts` si l'API ne tourne pas
   sur le poste hôte de l'émulateur. Valeur par défaut : `http://10.0.2.2:8080/`
   (l'émulateur Android voit le `localhost` du poste via 10.0.2.2).
4. Gradle sync, puis Run sur un émulateur API 24+.

## Architecture

- Kotlin + Jetpack Compose (Material 3)
- Navigation Compose
- Retrofit + OkHttp (logging + auth interceptor + 401 handler)
- DataStore (token JWT) + Room (cache des prises du jour)
- Hilt (DI)
- Firebase Cloud Messaging

Structure des packages : voir `app/src/main/java/com/medicare/app/`.

## Notes

- L'app ne contient pas de wrapper gradlew — utilisez Android Studio pour
  générer le wrapper et builder.
- Les icônes / splash sont laissés par défaut ; ajoutez vos ressources
  dans `app/src/main/res/`.
=======
# Medicare
MediCare aide les patients à suivre leurs traitements facilement. L’utilisateur saisit ses maladies et médicaments, puis l’application génère un planning précis. Des rappels intelligents évitent les oublis, et chaque prise peut être confirmée avec un suivi complet de l’observance.
>>>>>>> 0d6104cbed72b04dd4ee96096c82446412e5780f
