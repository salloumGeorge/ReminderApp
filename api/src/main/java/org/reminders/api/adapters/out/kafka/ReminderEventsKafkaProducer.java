package org.reminders.api.adapters.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.reminders.api.core.domain.Reminder;
import org.reminders.api.core.ports.RemindersUpdatesNotifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReminderEventsKafkaProducer implements RemindersUpdatesNotifier {


    public static final String REMINDERS_V_0 = "reminders.v0";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(Reminder reminder) throws JsonProcessingException {
        // TODO: extract topics and events into library
        kafkaTemplate.send(REMINDERS_V_0, toStringFormat(reminder));
        log.info("Sent message: " + reminder);
    }

    private String toStringFormat(Reminder reminder) throws JsonProcessingException {
        return objectMapper.writeValueAsString(reminder);
    }

}
