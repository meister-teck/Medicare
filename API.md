# MediCare API – Documentation

## URL de base
http://localhost:8080


## Authentification
Tous les endpoints sauf `/api/auth/*` nécessitent un header :



Le token est obtenu lors de la connexion.

---

## 1. Authentification (`/api/auth`)

| Méthode | Endpoint | Body | Réponse |
|---------|----------|------|---------|
| POST | `/api/auth/register` | `{ "email": "...", "password": "..." }` | 201 Created |
| POST | `/api/auth/login` | `{ "email": "...", "password": "..." }` | 200 OK `{ "token": "...", "userId": 1 }` |

---

## 2. Conditions médicales (`/api/conditions`)

| Méthode | Endpoint | Body | Réponse |
|---------|----------|------|---------|
| GET | `/api/conditions` | – | `[ { "id": 1, "type": "CHRONIC", "createdAt": "..." } ]` |
| POST | `/api/conditions` | `{ "type": "CHRONIC" }` | 201 Created |
| GET | `/api/conditions/{id}` | – | `{ "id": 1, "type": "CHRONIC", "createdAt": "..." }` |
| PUT | `/api/conditions/{id}` | `{ "type": "ACUTE" }` | 200 OK |
| DELETE | `/api/conditions/{id}` | – | 204 No Content |

---

## 3. Médicaments (`/api/medications`)

| Méthode | Endpoint | Body | Réponse |
|---------|----------|------|---------|
| POST | `/api/medications` | Voir `MedicationRequest` | 201 Created |
| GET | `/api/medications/condition/{conditionId}` | – | `[ MedicationDto ]` |
| GET | `/api/medications/{id}` | – | `MedicationDto` |
| PUT | `/api/medications/{id}` | `MedicationRequest` | 200 OK |
| DELETE | `/api/medications/{id}` | – | 204 No Content |

### MedicationRequest
```json
{
    "conditionId": 1,
    "name": "Doliprane",
    "dosesPerDay": 3,
    "durationDays": 5,
    "startDate": "2026-04-25",
    "scheduledTimes": ["08:00", "13:00", "20:00"]
}



{
    "id": 1,
    "conditionId": 1,
    "name": "Doliprane",
    "dosesPerDay": 3,
    "durationDays": 5,
    "startDate": "2026-04-25",
    "createdAt": "2026-04-25T10:30:00"
}

{
    "id": 1,
    "medicationId": 1,
    "medicationName": "Doliprane",
    "doseDate": "2026-04-25",
    "doseIndex": 1,
    "scheduledTime": "2026-04-25T08:00:00",
    "takenTimestamp": null,
    "taken": false,
    "notes": null
}





Ajoutez, committez et poussez ce fichier :

```powershell
git add API.md
git commit -m "Ajout documentation API"
git push origin main
