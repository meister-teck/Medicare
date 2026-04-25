package com.medapp.backend.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MedicationRequest {
    private Long conditionId;
    private String name;
    private Integer dosesPerDay;
    private Integer durationDays;
    private LocalDate startDate;
    private List<LocalTime> scheduledTimes; // ex: ["09:00", "15:00", "21:00"]
}