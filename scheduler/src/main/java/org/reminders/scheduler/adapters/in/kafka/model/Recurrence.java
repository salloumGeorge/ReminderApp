package org.reminders.scheduler.adapters.in.kafka.model;

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
    private String frequency;
    private int interval;
}
