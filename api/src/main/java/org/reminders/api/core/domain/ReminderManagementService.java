package org.reminders.api.core.domain;

import lombok.RequiredArgsConstructor;
import org.reminders.api.core.ports.RemindersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderManagementService {

    private final RemindersRepository remindersRepository;

    public List<Reminder> findAll(String userEmail) {
        return null;
    }

    public Reminder find(UUID reminderId) {
        return null;
    }

    @Transactional
    public Reminder createReminder(Reminder reminder) {
       return remindersRepository.save(reminder);
    }
}
