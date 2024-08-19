package org.reminders.api.adapters.in.rest.reminders;

import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ReminderCreatedApiResponse extends EntityModel<ReminderApiModel> {

    public ReminderCreatedApiResponse(ReminderApiModel content) {
        super(content);

        add(linkTo(methodOn(CreateReminderController.class).getReminder(content.getReminderId())).withSelfRel());

        add(linkTo(methodOn(CreateReminderController.class).getReminders(content.getUserEmail())).withSelfRel());
    }

    public ReminderApiModel getReminder() {
        return getContent();
    }
}
