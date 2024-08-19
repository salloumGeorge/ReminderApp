package org.reminders.api.core.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Setter
@AllArgsConstructor
public class Reminder {

    private String userEmail;
    private UUID reminderId;
    private LocalDateTime localDateTime;
    private String zoneId;
    private Recurrence recurrence;

    public Optional<Recurrence> getRecurrence() {
        return Optional.ofNullable(recurrence);
    }
}
