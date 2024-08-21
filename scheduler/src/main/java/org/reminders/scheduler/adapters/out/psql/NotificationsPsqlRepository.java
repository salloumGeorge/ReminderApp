package org.reminders.scheduler.adapters.out.psql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reminders.scheduler.core.domain.Notification;
import org.reminders.scheduler.core.ports.NotificationsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Slf4j
public class NotificationsPsqlRepository implements NotificationsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveNotification(Notification notification) {
        try {
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

            log.info("Notification saved successfully");
        } catch (Exception e) {
            log.error("Error while saving notification", e);
        }
    }

    @Override
    public List<Notification> findAll(LocalDateTime utc) {
        try {
            String sql = "SELECT * FROM notifications WHERE next_notification_time_utc = ?";
            return jdbcTemplate.query(sql, new Object[]{utc}, (rs, rowNum) -> new Notification(
                            UUID.fromString(rs.getString("Id")),
                            rs.getTimestamp("next_notification_time_utc").toLocalDateTime(),
                            rs.getTimestamp("user_original_timestamp").toLocalDateTime(),
                            rs.getString("timezone"),
                            rs.getString("targetUser"),
                            "TODO Add content",
                            rs.getBoolean("repeatable"),
                            rs.getString("Frequency"),
                            rs.getInt("Interval")
                    ));
        } catch (Exception e) {
            log.error("Error while fetching notifications", e);
            return List.of();
        }
    }

    @Override
    public void deleteNotification(Notification notification) {
        try {
            String sql = "DELETE FROM notifications WHERE Id = ?";
            jdbcTemplate.update(sql, notification.getId());
            log.info("Notification deleted successfully");
        } catch (Exception e) {
            log.error("Error while deleting notification", e);
        }
    }

    @Override
    public void updateNotificationNextNotificationTime(Notification updatedNotification) {
        try {
            String sql = "UPDATE notifications SET next_notification_time_utc = ? WHERE Id = ?";
            jdbcTemplate.update(sql, updatedNotification.getNextNotificationUTC(), updatedNotification.getId());
            log.info("Notification updated successfully");
        } catch (Exception e) {
            log.error("Error while updating notification", e);
        }
    }
}
