package org.reminders.scheduler.adapters.in.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobScheduler {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(NotificationJob.class)
            .withIdentity("notificationJob")
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("notificationTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
            .build();
    }
}
