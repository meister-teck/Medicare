package com.medapp.backend.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendDoseReminder(String fcmToken, String medicationName, int doseIndex) {
        if (fcmToken == null || fcmToken.isEmpty()) return;

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(Notification.builder()
                        .setTitle("Rappel de prise")
                        .setBody("Il est temps de prendre " + medicationName + " (prise " + doseIndex + ")")
                        .build())
                .putData("type", "dose_reminder")
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notification envoyée : " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Erreur d'envoi FCM : " + e.getMessage());
        }
    }
}