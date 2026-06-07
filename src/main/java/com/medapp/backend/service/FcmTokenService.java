package com.medapp.backend.service;

import com.medapp.backend.entity.FcmToken;
import com.medapp.backend.entity.User;
import com.medapp.backend.repository.FcmTokenRepository;
import com.medapp.backend.repository.UserRepository;
import com.medapp.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private User getCurrentUser() {
        String email = securityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
    }

    @Transactional
    public void registerToken(String token, String device) {
        User user = getCurrentUser();
        // Éviter les doublons : supprimer l'ancien token s'il existe déjà pour cet utilisateur/appareil
        fcmTokenRepository.findByToken(token).ifPresent(fcmTokenRepository::delete);
        FcmToken fcmToken = new FcmToken();
        fcmToken.setUser(user);
        fcmToken.setToken(token);
        fcmToken.setDevice(device);
        fcmTokenRepository.save(fcmToken);
    }

    @Transactional
    public void unregisterToken(String token) {
        fcmTokenRepository.deleteByToken(token);
    }
}