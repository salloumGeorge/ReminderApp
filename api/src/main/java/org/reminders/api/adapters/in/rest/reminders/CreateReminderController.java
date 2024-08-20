package org.reminders.api.adapters.in.rest.reminders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reminders.api.core.domain.Recurrence;
import org.reminders.api.core.domain.Reminder;
import org.reminders.api.core.domain.ReminderManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
@Slf4j
public class CreateReminderController {

    private final ReminderManagementService reminderManagementService;

    @PostMapping
    public ResponseEntity<?> createReminder(@RequestBody ReminderApiModel request) {
        log.info("Attempting to create reminder: " +request);
        Reminder reminder = reminderManagementService.createReminder(buildReminder(request));
        try {
            return ResponseEntity.ok(mapModel(reminder));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create reminder");
        }

    }

    private Reminder buildReminder(ReminderApiModel request) {
        return new Reminder(request.getUserEmail(),
                request.getReminderId(),
                request.getLocalDateTime(),
                request.getZoneId(),
                request.isRepeatable() ? new Recurrence(true, request.getFrequency(), request.getInterval()) : null);
    }

    @GetMapping("/{id}")
    //TODO authenticate with auth token to resolve identity and email and add needed checks
    public ResponseEntity<?> getReminder(@PathVariable UUID id) {
        Reminder reminder = reminderManagementService.find(id);
        return ResponseEntity.ok(mapModel(reminder));
    }


    @GetMapping
    public ResponseEntity<List<?>> getReminders(@RequestParam String userEmail) {
        List<Reminder> all = reminderManagementService.findAll(userEmail);
        return ResponseEntity.ok(map(all));
    }


    private List<ReminderApiModel> map(List<Reminder> all) {
        return all.stream().map(CreateReminderController::mapModel).toList();
    }

    private static ReminderApiModel mapModel(Reminder reminder) {
        return new ReminderApiModel(reminder.getUserEmail(),
                reminder.getReminderId(),
                reminder.getLocalDateTime(),
                reminder.getZoneId(),
                reminder.getRecurrence().isPresent(),
                reminder.getRecurrence().map(Recurrence::getFrequency).orElse(null),
                reminder.getRecurrence().map(Recurrence::getInterval).orElse(0));
    }

}
