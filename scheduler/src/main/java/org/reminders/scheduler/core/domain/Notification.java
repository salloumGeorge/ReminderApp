package org.reminders.scheduler.core.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
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
        Notification notification = new Notification();
        notification.id = id;
        notification.nextNotificationUTC = computeInUTC(userTimeZoneScheduledNotification, userTimeZone);
        notification.userTimeZoneScheduledNotification = userTimeZoneScheduledNotification;
        notification.userTimeZone = userTimeZone;
        notification.userEmail = userEmail;
        notification.content = content;
        notification.repeatable = repeatable;
        notification.frequency = frequency;
        notification.interval = interval;
        return notification;
    }

    private static LocalDateTime computeInUTC(LocalDateTime userTimeZoneScheduledNotification, String userTimeZone) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(userTimeZoneScheduledNotification, ZoneId.of(userTimeZone));
        ZonedDateTime utc = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utc.toLocalDateTime();
    }

}


