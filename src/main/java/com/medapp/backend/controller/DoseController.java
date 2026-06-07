package com.medapp.backend.controller;

import com.medapp.backend.dto.DoseDto;
import com.medapp.backend.dto.NoteRequest;
import com.medapp.backend.entity.Dose;
import com.medapp.backend.service.DoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doses")
@RequiredArgsConstructor
public class DoseController {

    private final DoseService doseService;

    // Planning du jour
    @GetMapping("/today")
    public ResponseEntity<List<DoseDto>> getTodayDoses() {
        List<Dose> doses = doseService.getTodayDoses();
        List<DoseDto> dtos = doses.stream()
                .map(DoseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Historique complet d'un médicament
    @GetMapping("/medication/{medicationId}")
    public ResponseEntity<List<DoseDto>> getDosesByMedication(@PathVariable Long medicationId) {
        List<Dose> doses = doseService.getDosesByMedication(medicationId);
        List<DoseDto> dtos = doses.stream()
                .map(DoseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Marquer une dose comme prise
    @PatchMapping("/{doseId}/take")
    public ResponseEntity<DoseDto> markAsTaken(@PathVariable Long doseId) {
        Dose dose = doseService.markDoseAsTaken(doseId);
        return ResponseEntity.ok(DoseDto.fromEntity(dose));
    }

    // Ajouter/modifier une note
    @PatchMapping("/{doseId}/note")
    public ResponseEntity<DoseDto> updateNote(@PathVariable Long doseId,
                                              @RequestBody NoteRequest request) {
        Dose dose = doseService.updateNote(doseId, request);
        return ResponseEntity.ok(DoseDto.fromEntity(dose));
    }

    // Doses pour une date donnée (optionnel, paramètre de requête ?date=YYYY-MM-DD)
    @GetMapping("/date")
    public ResponseEntity<List<DoseDto>> getDosesByDate(@RequestParam LocalDate date) {
        List<Dose> doses = doseService.getDosesByDate(date);
        List<DoseDto> dtos = doses.stream()
                .map(DoseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Doses d'un médicament pour une date donnée
    @GetMapping("/medication/{medicationId}/date")
    public ResponseEntity<List<DoseDto>> getDosesByMedicationAndDate(
            @PathVariable Long medicationId,
            @RequestParam LocalDate date) {
        List<Dose> doses = doseService.getDosesByMedicationAndDate(medicationId, date);
        List<DoseDto> dtos = doses.stream()
                .map(DoseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}