package org.reminders.scheduler.adapters.out.psql;

import lombok.RequiredArgsConstructor;
import org.reminders.scheduler.core.domain.Notification;
import org.reminders.scheduler.core.ports.NotificationsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NotificationsPsqlRepository implements NotificationsRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public void saveNotification(Notification notification) {
        String sql = "INSERT INTO notifications (Id, next_notification_time_utc, user_original_timestamp, timezone, targetUser, repeatable, Frequency, Interval) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, notification.getId(),
                notification.getNextNotificationUTC(),
                notification.getUserTimeZoneScheduledNotification(),
                notification.getUserTimeZone(),
                notification.getUserEmail(),
                notification.isRepeatable(),
                notification.getFrequency(),
                notification.getInterval());
    }
}
