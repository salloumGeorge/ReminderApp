package org.reminders.scheduler.core.ports;

import org.reminders.scheduler.core.domain.Notification;

public interface NotificationsRepository {

    public void saveNotification(Notification notification);
}
