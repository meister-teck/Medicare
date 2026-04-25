package com.medapp.backend.controller;

import com.medapp.backend.dto.ConditionDto;
import com.medapp.backend.dto.CreateConditionRequest;
import com.medapp.backend.entity.Condition;
import com.medapp.backend.service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conditions")
@RequiredArgsConstructor
public class ConditionController {

    private final ConditionService conditionService;

    @GetMapping
    public ResponseEntity<List<ConditionDto>> getAllConditions() {
        List<Condition> conditions = conditionService.getAllConditions();
        List<ConditionDto> dtos = conditions.stream()
                .map(ConditionDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConditionDto> getConditionById(@PathVariable Long id) {
        Condition condition = conditionService.getConditionById(id);
        return ResponseEntity.ok(ConditionDto.fromEntity(condition));
    }

    @PostMapping
    public ResponseEntity<ConditionDto> createCondition(@Valid @RequestBody CreateConditionRequest request) {
        Condition.ConditionType type;
        try {
            type = Condition.ConditionType.valueOf(request.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        Condition created = conditionService.createCondition(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(ConditionDto.fromEntity(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConditionDto> updateCondition(@PathVariable Long id,
                                                        @Valid @RequestBody CreateConditionRequest request) {
        Condition.ConditionType type;
        try {
            type = Condition.ConditionType.valueOf(request.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        Condition updated = conditionService.updateCondition(id, type);
        return ResponseEntity.ok(ConditionDto.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id) {
        conditionService.deleteCondition(id);
        return ResponseEntity.noContent().build();
    }
}