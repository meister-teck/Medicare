package com.medapp.backend.repository;

import com.medapp.backend.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByConditionId(Long conditionId);
    List<Medication> findByConditionUserId(Long userId);
}