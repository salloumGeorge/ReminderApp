package org.reminders.scheduler.core.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reminders.scheduler.core.ports.NotificationsJob;
import org.reminders.scheduler.core.ports.NotificationsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationExecutor {
    private final NotificationsRepository notificationsRepository;
    private final NotificationsJob notificationsJob;

    public void executeNotifications(Date utcDate) {
        LocalDateTime startOfMinute = getStartOfMinute(utcDate);
        log.info("Executing Job at UTC: " + startOfMinute );

        log.info("Executing notifications at UTC: " + utcDate);


        List<Notification> notifications = notificationsRepository.findAll(startOfMinute);
        log.info("Found notifications to execute: " + notifications.size());

        notifications.forEach(notification -> {
            try {
                handleNotification(notification);
                //TODO extract compute next notifications from main thread into background async thread
                computeNextNotificationSchedule(notification);
            } catch (Exception e) {
                //TODO Collect metrics and setup alerts to trigger
                //TODO introduce resilience patterns such as retries with resilient4j for example
                log.error("Error while executing notification: " + notification, e);
            }
        });


    }

    private void computeNextNotificationSchedule(Notification notification) {
        Optional<LocalDateTime> localDateTime = notification.computeNextScheduleUTC();
        if (localDateTime.isEmpty()) {
            notificationsRepository.deleteNotification(notification);
            return;
        }
        Notification updatedNotification = notification.generateNextNotification(localDateTime.get());
        notificationsRepository.updateNotificationNextNotificationTime(updatedNotification);
    }

    private void  handleNotification(Notification notification) throws Exception {
        log.info("Executing notification: " + notification);
        notificationsJob.executeNotification(notification);
    }


    private static LocalDateTime getStartOfMinute(Date fireTime) {
        LocalDateTime fireDateTime = fireTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime utcFireDateTime = fireDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        return utcFireDateTime.withSecond(0).withNano(0);
    }
}
