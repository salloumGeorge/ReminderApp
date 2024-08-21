package org.reminders.scheduler.core.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reminders.scheduler.core.ports.NotificationsJob;
import org.reminders.scheduler.core.ports.NotificationsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

    private final NotificationsRepository notificationsRepository;

    public void scheduleNotification(Notification notification) {
        notificationsRepository.saveNotification(notification);
    }
}
