package org.reminders.api.adapters.in.rest.reminders;

import lombok.*;
import org.reminders.api.core.domain.FrequencyEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Setter
@AllArgsConstructor
@ToString
public class ReminderApiModel {

    private String userEmail;
    private UUID reminderId;
    private LocalDateTime localDateTime;
    private String zoneId;
    private boolean repeatable;
    private FrequencyEnum frequency;
    private int interval;

}
