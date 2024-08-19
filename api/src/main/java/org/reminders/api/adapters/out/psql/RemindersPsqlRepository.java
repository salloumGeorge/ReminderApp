package org.reminders.api.adapters.out.psql;

import lombok.RequiredArgsConstructor;
import org.reminders.api.core.domain.Reminder;
import org.reminders.api.core.ports.RemindersRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Repository
public class RemindersPsqlRepository implements RemindersRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Reminder save(Reminder reminder) {

        String sql =

                "INSERT INTO reminders (id, event_date, event_time, user_time_zone, target_user_email, repeatable, frequency, interval, created_on) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                reminder.getReminderId(), // id
                java.sql.Date.valueOf(reminder.getLocalDateTime().toLocalDate()), // event_date
                reminder.getLocalDateTime().toLocalTime() != null ? java.sql.Timestamp.valueOf(reminder.getLocalDateTime()) : null, // event_time
                reminder.getZoneId(), // user_time_zone
                reminder.getUserEmail(), // target_user
                reminder.getRecurrence().isPresent(),
                reminder.getRecurrence().isPresent() ? reminder.getRecurrence().get().getFrequency().toString() : null, // frequency
                reminder.getRecurrence().isPresent() ? reminder.getRecurrence().get().getInterval() : null, // interval
                java.sql.Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));

        return reminder;
    }


}
