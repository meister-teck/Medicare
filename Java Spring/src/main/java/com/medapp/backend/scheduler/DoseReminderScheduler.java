package com.medapp.backend.scheduler;

import com.medapp.backend.entity.Dose;
import com.medapp.backend.entity.FcmToken;
import com.medapp.backend.repository.DoseRepository;
import com.medapp.backend.repository.FcmTokenRepository;
import com.medapp.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DoseReminderScheduler {

    private final DoseRepository doseRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final NotificationService notificationService;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void checkAndRemind() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        // Rappel AVANT (5 min avant)
        LocalDateTime windowStart = now.plusMinutes(5).withSecond(0).withNano(0);
        LocalDateTime windowEnd   = windowStart.plusMinutes(1);

        List<Dose> beforeDoses = doseRepository.findDosesToRemindBefore(today, windowStart, windowEnd);
        for (Dose dose : beforeDoses) {
            sendReminderForDose(dose);
            dose.setReminderBeforeSent(1);
            doseRepository.save(dose);
        }

        // Rappel APRES (30 min après l’heure prévue)
        LocalDateTime deadlineAfter = now.minusMinutes(30);
        List<Dose> afterDoses = doseRepository.findDosesForAfterReminder(today, deadlineAfter);
        for (Dose dose : afterDoses) {
            sendReminderForDose(dose);
            dose.setReminderAfterSent(1);
            doseRepository.save(dose);
        }
    }

    private void sendReminderForDose(Dose dose) {
        Long userId = dose.getMedication().getCondition().getUser().getId();
        List<FcmToken> tokens = fcmTokenRepository.findByUserId(userId);
        for (FcmToken token : tokens) {
            notificationService.sendDoseReminder(
                    token.getToken(),
                    dose.getMedication().getName(),
                    dose.getDoseIndex());
        }
    }
}