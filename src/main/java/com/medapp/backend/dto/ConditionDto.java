package com.medapp.backend.dto;

import com.medapp.backend.entity.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConditionDto {
    private Long id;
    private String type;
    private LocalDateTime createdAt;

    public static ConditionDto fromEntity(Condition condition) {
        return new ConditionDto(
                condition.getId(),
                condition.getType().name(),
                condition.getCreatedAt()
        );
    }
}