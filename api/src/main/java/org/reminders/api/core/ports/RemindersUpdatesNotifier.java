package org.reminders.api.core.ports;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.reminders.api.core.domain.Reminder;

public interface RemindersUpdatesNotifier {
    void sendMessage(Reminder reminder) throws JsonProcessingException;
}
