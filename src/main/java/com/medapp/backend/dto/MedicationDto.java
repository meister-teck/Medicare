package com.medapp.backend.dto;

import com.medapp.backend.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MedicationDto {
    private Long id;
    private Long conditionId;
    private String name;
    private Integer dosesPerDay;
    private Integer durationDays;
    private LocalDate startDate;
    private LocalDateTime createdAt;

    public static MedicationDto fromEntity(Medication medication) {
        return new MedicationDto(
                medication.getId(),
                medication.getCondition().getId(),
                medication.getName(),
                medication.getDosesPerDay(),
                medication.getDurationDays(),
                medication.getStartDate(),
                medication.getCreatedAt()
        );
    }
}