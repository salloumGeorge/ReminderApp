package org.reminders.api.core.ports;

import org.reminders.api.core.domain.Reminder;

public interface RemindersRepository {

    public Reminder save(Reminder reminder);
}
