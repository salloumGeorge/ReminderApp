package org.reminders.scheduler.core.ports;

import org.reminders.scheduler.core.domain.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationsRepository {

    void saveNotification(Notification notification);

    List<Notification> findAll(LocalDateTime utc);

    void deleteNotification(Notification notification);

    void updateNotificationNextNotificationTime(Notification updatedNotification);
}
