package com.medapp.backend.dto;

import lombok.Data;

@Data
public class CreateConditionRequest {
    private String type; // "CHRONIC" ou "ACUTE"
}