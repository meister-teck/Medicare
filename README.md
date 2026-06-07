<<<<<<< HEAD
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
