package com.medapp.backend.controller;

import com.medapp.backend.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.medapp.backend.dto.FcmTokenRequest;

@RestController
@RequestMapping("/api/user/fcm-token")
@RequiredArgsConstructor
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping
    public ResponseEntity<Void> registerToken(@RequestBody FcmTokenRequest request) {
        fcmTokenService.registerToken(request.getToken(), request.getDevice());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unregisterToken(@RequestBody FcmTokenRequest request) {
        fcmTokenService.unregisterToken(request.getToken());
        return ResponseEntity.ok().build();
    }
}