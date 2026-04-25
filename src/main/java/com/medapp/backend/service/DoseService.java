package com.medapp.backend.service;

import com.medapp.backend.dto.NoteRequest;
import com.medapp.backend.entity.Dose;
import com.medapp.backend.entity.Medication;
import com.medapp.backend.entity.User;
import com.medapp.backend.repository.DoseRepository;
import com.medapp.backend.repository.MedicationRepository;
import com.medapp.backend.repository.UserRepository;
import com.medapp.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoseService {

    private final DoseRepository doseRepository;
    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private User getCurrentUser() {
        String email = securityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
    }

    // Récupère les doses du jour pour l'utilisateur connecté
    public List<Dose> getTodayDoses() {
        User user = getCurrentUser();
        LocalDate today = LocalDate.now();
        return doseRepository.findTodayDoses(user.getId(), today);
    }

    // Récupère toutes les doses d'un médicament (historique complet)
    public List<Dose> getDosesByMedication(Long medicationId) {
        User user = getCurrentUser();
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new EntityNotFoundException("Médicament non trouvé"));

        // Vérifier que le médicament appartient à l'utilisateur
        if (!medication.getCondition().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Accès non autorisé à ce médicament");
        }

        return doseRepository.findByMedicationId(medicationId);
    }

    // Marque une dose comme prise
    @Transactional
    public Dose markDoseAsTaken(Long doseId) {
        Dose dose = validateDoseAccess(doseId);

        if (dose.getTakenTimestamp() != null) {
            throw new IllegalStateException("Cette dose a déjà été marquée comme prise");
        }

        // Optionnel : empêcher de marquer une dose future
        if (dose.getDoseDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Impossible de marquer une dose future comme prise");
        }

        dose.setTakenTimestamp(LocalDateTime.now());
        return doseRepository.save(dose);
    }

    // Ajoute ou modifie une note sur une dose
    @Transactional
    public Dose updateNote(Long doseId, NoteRequest request) {
        Dose dose = validateDoseAccess(doseId);
        dose.setNotes(request.getNotes());
        return doseRepository.save(dose);
    }

    // Vérifie que la dose appartient bien à l'utilisateur connecté
    private Dose validateDoseAccess(Long doseId) {
        User user = getCurrentUser();
        Dose dose = doseRepository.findById(doseId)
                .orElseThrow(() -> new EntityNotFoundException("Dose non trouvée"));

        if (!dose.getMedication().getCondition().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Accès non autorisé à cette dose");
        }

        return dose;
    }

    // Récupère les doses pour une date spécifique (tous médicaments confondus)
    public List<Dose> getDosesByDate(LocalDate date) {
        User user = getCurrentUser();
        return doseRepository.findByUserIdAndDate(user.getId(), date);
    }

    // Récupère les doses d'un médicament pour une date donnée
    public List<Dose> getDosesByMedicationAndDate(Long medicationId, LocalDate date) {
        User user = getCurrentUser();
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new EntityNotFoundException("Médicament non trouvé"));
        if (!medication.getCondition().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Accès non autorisé");
        }
        return doseRepository.findByMedicationIdAndDate(medicationId, date);
    }
}