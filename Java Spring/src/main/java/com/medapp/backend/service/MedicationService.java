package com.medapp.backend.service;

import com.medapp.backend.dto.MedicationRequest;
import com.medapp.backend.entity.*;
import com.medapp.backend.repository.*;
import com.medapp.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final ConditionRepository conditionRepository;
    private final DoseRepository doseRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    // Récupère l'utilisateur authentifié
    private User getCurrentUser() {
        String email = securityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
    }

    // Vérifie que la condition appartient à l'utilisateur
    private Condition validateAndGetCondition(Long conditionId, User user) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new EntityNotFoundException("Condition non trouvée"));
        if (!condition.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Accès non autorisé à cette condition");
        }
        return condition;
    }

    /**
     * Crée un médicament et génère les doses planifiées.
     * Valide que le nombre d'heures de prise correspond à la posologie.
     */
    @Transactional
    public Medication createMedication(MedicationRequest request) {
        User user = getCurrentUser();
        Condition condition = validateAndGetCondition(request.getConditionId(), user);

        // Validation du nombre d'heures de prise
        List<LocalTime> scheduledTimes = request.getScheduledTimes();
        int dosesPerDay = request.getDosesPerDay();
        if (scheduledTimes != null && scheduledTimes.size() != dosesPerDay) {
            throw new IllegalArgumentException(
                    "Le nombre d'heures de prise (" + scheduledTimes.size() +
                            ") ne correspond pas au nombre de prises par jour (" + dosesPerDay + ")");
        }

        Medication medication = new Medication();
        medication.setCondition(condition);
        medication.setName(request.getName());
        medication.setDosesPerDay(dosesPerDay);
        medication.setDurationDays(request.getDurationDays());
        medication.setStartDate(request.getStartDate());

        medication = medicationRepository.save(medication);

        // Génération des doses planifiées (avec heures)
        generateDoses(medication, scheduledTimes);

        return medication;
    }

    /**
     * Génère toutes les doses pour un médicament donné.
     * Si une liste d'heures est fournie, chaque dose reçoit l'heure correspondante.
     */
    private void generateDoses(Medication medication, List<LocalTime> scheduledTimes) {
        List<Dose> doses = new ArrayList<>();
        LocalDate start = medication.getStartDate();

        for (int day = 0; day < medication.getDurationDays(); day++) {
            LocalDate doseDate = start.plusDays(day);
            for (int doseNum = 0; doseNum < medication.getDosesPerDay(); doseNum++) {
                Dose dose = new Dose();
                dose.setMedication(medication);
                dose.setDoseDate(doseDate);
                dose.setDoseIndex(doseNum + 1);
                if (scheduledTimes != null && scheduledTimes.size() > doseNum) {
                    dose.setScheduledTime(LocalDateTime.of(doseDate, scheduledTimes.get(doseNum)));
                }
                doses.add(dose);
            }
        }
        doseRepository.saveAll(doses);
    }

    /**
     * Récupère les médicaments d'une condition (vérification d'accès incluse).
     */
    public List<Medication> getMedicationsByCondition(Long conditionId) {
        User user = getCurrentUser();
        Condition condition = validateAndGetCondition(conditionId, user);
        return medicationRepository.findByConditionId(condition.getId());
    }

    /**
     * Récupère un médicament par son ID avec vérification d'accès.
     */
    public Medication getMedicationById(Long medicationId) {
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new EntityNotFoundException("Médicament non trouvé"));
        User user = getCurrentUser();
        if (!medication.getCondition().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Accès non autorisé à ce médicament");
        }
        return medication;
    }

    /**
     * Supprime un médicament (et ses doses par cascade).
     */
    @Transactional
    public void deleteMedication(Long medicationId) {
        Medication medication = getMedicationById(medicationId);
        medicationRepository.delete(medication);
    }

    /**
     * Modifie un médicament tout en préservant l'historique des prises déjà effectuées.
     * Valide également les nouvelles heures de prise.
     */
    @Transactional
    public Medication updateMedication(Long medicationId, MedicationRequest request) {
        Medication medication = getMedicationById(medicationId);

        // Mise à jour des champs de base
        medication.setName(request.getName());
        medication.setDosesPerDay(request.getDosesPerDay());
        medication.setDurationDays(request.getDurationDays());
        medication.setStartDate(request.getStartDate());

        List<LocalTime> scheduledTimes = request.getScheduledTimes();
        int dosesPerDay = request.getDosesPerDay();
        if (scheduledTimes != null && scheduledTimes.size() != dosesPerDay) {
            throw new IllegalArgumentException(
                    "Le nombre d'heures de prise (" + scheduledTimes.size() +
                            ") ne correspond pas au nombre de prises par jour (" + dosesPerDay + ")");
        }

        medication = medicationRepository.save(medication);

        // Supprimer les doses futures non prises
        LocalDate today = LocalDate.now();
        List<Dose> dosesToDelete = medication.getDoses().stream()
                .filter(d -> d.getTakenTimestamp() == null)
                .filter(d -> !d.getDoseDate().isBefore(today))
                .collect(Collectors.toList());
        for (Dose dose : dosesToDelete) {
            medication.getDoses().remove(dose);
            doseRepository.delete(dose);
        }

        // Régénération des doses futures
        LocalDate generationStart = request.getStartDate().isBefore(today) ? today : request.getStartDate();
        long daysPassed = java.time.temporal.ChronoUnit.DAYS.between(request.getStartDate(), today);
        int remainingDays = (int)(request.getDurationDays() - Math.max(0, daysPassed));

        List<Dose> newDoses = new ArrayList<>();
        for (int day = 0; day < remainingDays; day++) {
            LocalDate doseDate = generationStart.plusDays(day);
            for (int doseNum = 0; doseNum < dosesPerDay; doseNum++) {
                Dose dose = new Dose();
                dose.setMedication(medication);
                dose.setDoseDate(doseDate);
                dose.setDoseIndex(doseNum + 1);
                if (scheduledTimes != null && scheduledTimes.size() > doseNum) {
                    dose.setScheduledTime(LocalDateTime.of(doseDate, scheduledTimes.get(doseNum)));
                }
                newDoses.add(dose);
            }
        }
        doseRepository.saveAll(newDoses);
        medication.getDoses().addAll(newDoses);

        return medication;
    }
}