package org.reminders.api.core.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.reminders.api.core.ports.RemindersRepository;
import org.reminders.api.core.ports.RemindersUpdatesNotifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderManagementService {

    private final RemindersRepository remindersRepository;
    private final RemindersUpdatesNotifier remindersUpdatesNotifier;
    public List<Reminder> findAll(String userEmail) {
        return null;
    }

    public Reminder find(UUID reminderId) {
        return null;
    }

    @Transactional
    public Reminder createReminder(Reminder reminder) {
        Reminder savedReminder = remindersRepository.save(reminder);
        try {
            remindersUpdatesNotifier.sendMessage(savedReminder);
        } catch (Exception e) {
            //TODO define proper Domain Checked Exception
            throw new RuntimeException(e);
        }
        return savedReminder;
    }

}
