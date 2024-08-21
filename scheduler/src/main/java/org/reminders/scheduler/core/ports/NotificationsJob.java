package org.reminders.scheduler.core.ports;

import org.reminders.scheduler.core.domain.Notification;

import java.util.List;

public interface NotificationsJob {


    void executeNotification(Notification notification) throws Exception;
}
