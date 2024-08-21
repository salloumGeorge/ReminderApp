package org.reminders.scheduler.adapters.in.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
public class NotificationJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime startOfMinute = getStartOfMinute(context.getFireTime());
        log.info("Executing Job at UTC: " + startOfMinute );

    }

    private static LocalDateTime getStartOfMinute(Date fireTime) {
        LocalDateTime fireDateTime = fireTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime utcFireDateTime = fireDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        return utcFireDateTime.withSecond(0).withNano(0);
    }
}
