package org.reminders.scheduler.core.domain;

import lombok.RequiredArgsConstructor;
import org.reminders.scheduler.core.ports.NotificationsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationsRepository notificationsRepository;
    public void scheduleNotification(Notification notification) {
        notificationsRepository.saveNotification(notification);
    }
}
