package com.medapp.backend.repository;

import com.medapp.backend.entity.Dose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoseRepository extends JpaRepository<Dose, Long> {

    @Query("""
        SELECT d FROM Dose d
        JOIN d.medication m
        JOIN m.condition c
        WHERE c.user.id = :userId
          AND d.doseDate = :date
        ORDER BY m.name, d.doseIndex
    """)
    List<Dose> findTodayDoses(@Param("userId") Long userId, @Param("date") LocalDate date);

    List<Dose> findByMedicationId(Long medicationId);
    @Query("""
    SELECT d FROM Dose d
    JOIN d.medication m
    JOIN m.condition c
    WHERE c.user.id = :userId
      AND d.doseDate = :date
    ORDER BY m.name, d.doseIndex
""")
    List<Dose> findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("""
    SELECT d FROM Dose d
    WHERE d.medication.id = :medicationId
      AND d.doseDate = :date
    ORDER BY d.doseIndex
""")
    List<Dose> findByMedicationIdAndDate(@Param("medicationId") Long medicationId,
                                         @Param("date") LocalDate date);




    @Query("SELECT d FROM Dose d " +
            "JOIN FETCH d.medication m " +
            "JOIN FETCH m.condition c " +
            "JOIN FETCH c.user u " +
            "WHERE d.doseDate = :today " +
            "AND d.takenTimestamp IS NULL " +
            "AND d.reminderBeforeSent = 0 " +
            "AND d.scheduledTime >= :start " +
            "AND d.scheduledTime < :end")
    List<Dose> findDosesToRemindBefore(@Param("today") LocalDate today,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    @Query("SELECT d FROM Dose d " +
            "JOIN FETCH d.medication m " +
            "JOIN FETCH m.condition c " +
            "JOIN FETCH c.user u " +
            "WHERE d.doseDate = :today " +
            "AND d.takenTimestamp IS NULL " +
            "AND d.reminderAfterSent = 0 " +
            "AND d.scheduledTime <= :deadlineAfter")
    List<Dose> findDosesForAfterReminder(@Param("today") LocalDate today,
                                         @Param("deadlineAfter") LocalDateTime deadlineAfter);
}