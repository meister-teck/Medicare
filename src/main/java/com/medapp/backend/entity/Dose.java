package com.medapp.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEDICATION_DOSES",
        uniqueConstraints = @UniqueConstraint(columnNames = {"MEDICATION_ID", "DOSE_DATE", "DOSE_INDEX"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICATION_ID", nullable = false)
    private Medication medication;

    @Column(name = "DOSE_DATE", nullable = false)
    private LocalDate doseDate;

    @Column(name = "DOSE_INDEX", nullable = false)
    private Integer doseIndex;

    @Column(name = "TAKEN_TIMESTAMP")
    private LocalDateTime takenTimestamp;

    @Column(length = 500)
    private String notes;

    @Column(name = "SCHEDULED_TIME")
    private LocalDateTime scheduledTime;

    @Column(name = "REMINDER_BEFORE_SENT", nullable = false)
    private Integer reminderBeforeSent = 0;

    @Column(name = "REMINDER_AFTER_SENT", nullable = false)
    private Integer reminderAfterSent = 0;

}