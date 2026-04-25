package com.medapp.backend.dto;

import lombok.Data;

@Data
public class FcmTokenRequest {
    private String token;
    private String device;
}