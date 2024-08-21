package org.reminders.workers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Workers {

    public static final String NOTIFICATIONS = "notifications.v0";
    public static final String WORKERS = "workers";

    @KafkaListener(topics = NOTIFICATIONS, groupId = WORKERS, concurrency = "5" )
    public void listen(String message) {
        try {
            //TODO integrate email provider service
            log.info("Sending email based on: " + message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
