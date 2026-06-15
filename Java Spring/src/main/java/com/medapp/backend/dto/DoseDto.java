package com.medapp.backend.dto;

import com.medapp.backend.entity.Dose;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DoseDto {
    private Long id;
    private Long medicationId;
    private String medicationName;
    private LocalDate doseDate;
    private Integer doseIndex;
    private LocalDateTime takenTimestamp;
    private String notes;
    private boolean taken; // dérivé : true si takenTimestamp non null

    public static DoseDto fromEntity(Dose dose) {
        return new DoseDto(
                dose.getId(),
                dose.getMedication().getId(),
                dose.getMedication().getName(),
                dose.getDoseDate(),
                dose.getDoseIndex(),
                dose.getTakenTimestamp(),
                dose.getNotes(),
                dose.getTakenTimestamp() != null
        );
    }
}