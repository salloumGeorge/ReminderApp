package org.reminders.scheduler.adapters.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reminders.scheduler.core.domain.Notification;
import org.reminders.scheduler.core.ports.NotificationsJob;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationsStartedEventPublisher implements NotificationsJob {


    public static final String NOTIFICATIONS = "notifications.v0";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public void executeNotification(Notification notifications) throws Exception {
        try {
            kafkaTemplate.send(NOTIFICATIONS, toStringFormat(notifications));
            log.info("Sent message: " + notifications);
        } catch (JsonProcessingException e) {
            log.error("Error sending message: " + notifications, e);
            throw new Exception("Error sending message: " + notifications, e);
        }
    }

    private String toStringFormat(Notification notification) throws JsonProcessingException {
        return objectMapper.writeValueAsString(notification);
    }
}


