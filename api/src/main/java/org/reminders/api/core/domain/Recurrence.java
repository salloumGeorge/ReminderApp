package org.reminders.api.core.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
@AllArgsConstructor
public class Recurrence {
    private boolean repeatable;
    private FrequencyEnum frequency;
    private int interval;
}
