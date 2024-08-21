package org.reminders.scheduler.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Notification {
    private UUID id;
    LocalDateTime nextNotificationUTC;
    LocalDateTime userTimeZoneScheduledNotification;
    String userTimeZone;
    String userEmail;
    String content;
    boolean repeatable;

    //TODO add enum
    String frequency;
    int interval;

    public static Notification build(
        UUID id,
        LocalDateTime userTimeZoneScheduledNotification,
        String userTimeZone,
        String userEmail,
        String content,
        boolean repeatable,
        String frequency,
        int interval
    ){
        return new Notification(
                id,
                computeInUTC(userTimeZoneScheduledNotification, userTimeZone),
                userTimeZoneScheduledNotification,
                userTimeZone,
                userEmail,
                content,
                repeatable,
                frequency,
                interval
        );
    }


    private static LocalDateTime computeInUTC(LocalDateTime userTimeZoneScheduledNotification, String userTimeZone) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(userTimeZoneScheduledNotification, ZoneId.of(userTimeZone));
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utc.toLocalDateTime();
    }

    public Optional<LocalDateTime> computeNextScheduleUTC() {
        if (repeatable) {
            return switch (frequency) {
                case "daily" -> Optional.of(nextNotificationUTC.plusDays(interval));
                case "weekly" -> Optional.of(nextNotificationUTC.plusWeeks(interval));
                case "monthly" -> Optional.of(nextNotificationUTC.plusMonths(interval));
                default -> Optional.empty();
            };
        }
        return Optional.empty();
    }

    public Notification generateNextNotification(LocalDateTime nextNotificationTime) {
        Notification notification = new Notification(
                id,
                nextNotificationTime,
                userTimeZoneScheduledNotification,
                userTimeZone,
                userEmail,
                content,
                repeatable,
                frequency,
                interval
        );
        return notification;
    }
}


