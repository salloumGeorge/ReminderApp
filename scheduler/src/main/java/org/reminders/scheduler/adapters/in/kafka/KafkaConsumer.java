package org.reminders.scheduler.adapters.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.reminders.scheduler.adapters.in.kafka.model.Recurrence;
import org.reminders.scheduler.adapters.in.kafka.model.ReminderCreatedEvent;
import org.reminders.scheduler.core.domain.Notification;
import org.reminders.scheduler.core.domain.NotificationScheduler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {


    public static final String SCHEDULERS = "schedulers";
    private final ObjectMapper objectMapper;

    private final NotificationScheduler scheduler;
    public static final String REMINDERS_V_0 = "reminders.v0";

    @KafkaListener(topics = REMINDERS_V_0, groupId = SCHEDULERS)
    public void consume(String message) {
        try {
            ReminderCreatedEvent object = objectMapper.readValue(message, ReminderCreatedEvent.class);
            System.out.println("Consumed object: " + object);
            scheduler.scheduleNotification(mapToNotification(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Notification mapToNotification(ReminderCreatedEvent event) {
        return Notification.build(
                event.getReminderId(),
                event.getLocalDateTime(),
                event.getZoneId(),
                event.getUserEmail(),
                "TODO Pass content in original service",
                event.getRecurrence().isPresent(),
                event.getRecurrence().map(Recurrence::getFrequency).orElse(null),
                event.getRecurrence().map(Recurrence::getInterval).orElse(0)
        );
    }
}
