package com.medapp.backend.repository;

import com.medapp.backend.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {
    List<Condition> findByUserId(Long userId);
}