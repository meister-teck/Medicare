package com.medapp.backend.controller;

import com.medapp.backend.dto.MedicationDto;
import com.medapp.backend.dto.MedicationRequest;
import com.medapp.backend.entity.Medication;
import com.medapp.backend.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    // Ajouter un médicament (avec génération des doses)
    @PostMapping
    public ResponseEntity<MedicationDto> createMedication(@Valid @RequestBody MedicationRequest request) {
        Medication created = medicationService.createMedication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MedicationDto.fromEntity(created));
    }

    // Lister les médicaments d'une condition
    @GetMapping("/condition/{conditionId}")
    public ResponseEntity<List<MedicationDto>> getMedicationsByCondition(@PathVariable Long conditionId) {
        List<Medication> medications = medicationService.getMedicationsByCondition(conditionId);
        List<MedicationDto> dtos = medications.stream()
                .map(MedicationDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Récupérer un médicament par son ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicationDto> getMedicationById(@PathVariable Long id) {
        Medication medication = medicationService.getMedicationById(id);
        return ResponseEntity.ok(MedicationDto.fromEntity(medication));
    }

    // Supprimer un médicament
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }

    // Modifier un médicament
    @PutMapping("/{id}")
    public ResponseEntity<MedicationDto> updateMedication(@PathVariable Long id,
                                                          @Valid @RequestBody MedicationRequest request) {
        Medication updated = medicationService.updateMedication(id, request);
        return ResponseEntity.ok(MedicationDto.fromEntity(updated));
    }
}