package com.medapp.backend.service;

import com.medapp.backend.entity.Condition;
import com.medapp.backend.entity.User;
import com.medapp.backend.repository.ConditionRepository;
import com.medapp.backend.repository.UserRepository;
import com.medapp.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionService {

    private final ConditionRepository conditionRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private User getCurrentUser() {
        String email = securityUtils.getCurrentUserEmail();
        System.out.println(">>> Email extrait du token : " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println(">>> Aucun utilisateur trouvé pour l'email : " + email);
                    return new EntityNotFoundException("Utilisateur non trouvé");
                });
        System.out.println(">>> Utilisateur trouvé : ID = " + user.getId());
        return user;
    }

    public List<Condition> getAllConditions() {
        User user = getCurrentUser();
        return conditionRepository.findByUserId(user.getId());
    }

    public Condition getConditionById(Long id) {
        Condition condition = conditionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Condition non trouvée"));
        User user = getCurrentUser();
        if (!condition.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Accès non autorisé à cette condition");
        }
        return condition;
    }

    @Transactional
    public Condition createCondition(Condition.ConditionType type) {
        User user = getCurrentUser();
        Condition condition = new Condition();
        condition.setUser(user);
        condition.setType(type);
        return conditionRepository.save(condition);
    }

    @Transactional
    public Condition updateCondition(Long id, Condition.ConditionType type) {
        Condition condition = getConditionById(id); // vérifie l'accès
        condition.setType(type);
        return conditionRepository.save(condition);
    }

    @Transactional
    public void deleteCondition(Long id) {
        Condition condition = getConditionById(id); // vérifie l'accès
        conditionRepository.delete(condition);
    }

}