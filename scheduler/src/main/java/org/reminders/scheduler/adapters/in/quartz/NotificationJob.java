package org.reminders.scheduler.adapters.in.quartz;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.reminders.scheduler.core.domain.NotificationExecutor;
import org.reminders.scheduler.core.domain.NotificationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Component
public class NotificationJob extends QuartzJobBean {

    @Autowired
    NotificationExecutor notificationExecutor;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        notificationExecutor.executeNotifications(context.getFireTime());

    }

}
