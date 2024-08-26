## Architecture

### Services

System consists of 3 services:
1. API to manage reminders
2. Scheduler that listens to reminders changes and saves notifications. Later it runs a chron every 1 minute
3. Workers that react on Notification events

### Kafka
* *Reminders* and *Scheduler* communicate through reminders.v0 topic
* *Scheduler* and *workers* communicate through notifications.v0 topic


### Env

* All infra dependencies are defined in docker-compose.yml
* logs of each service are router under ./logs
* DB init scripts are under ./db
* observability config is under ./observability

### Running
* ./hard-clean.sh removes all local volume references and kills all services
* ./init.sh create the applications with the infra

POST http://localhost:8083/api/manager/reminders
Content-Type: application/json
```json
{
  "userEmail": "usser@example.com",
  "reminderId": "67ffcf76-0aa2-4d11-97fc-0e8d86ba9f21",
  "localDateTime": "2024-08-26T11:21:00.000",
  "zoneId": "Europe/Madrid",
  "repeatable": true,
  "frequency": "WEEK",
  "interval": 1
}

```
Create a reminder through reminder API.
1. In api logs, you will see the reminder created. It will also appear in manager psql db
2. In scheduler logs, you will see the event consumed. The notification will appear in scheduler psql db
```shell
2024-08-26T09:20:54.296Z  INFO 1 --- [scheduler] [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] o.r.s.adapters.in.kafka.KafkaConsumer    : Received message: {"userEmail":"usser@example.com","reminderId":"67ffcf76-0aa2-4d11-97fc-0e8d86ba9f21","localDateTime":"2024-08-26T11:21:00","zoneId":"Europe/Madrid","recurrence":{"repeatable":true,"frequency":"WEEK","interval":1}}
```
3. In scheduler logs, you will see chron job run every 1 minute
```shell
2024-08-26T09:21:00.003Z  INFO 1 --- [scheduler] [quartzScheduler_Worker-4] o.r.s.core.domain.NotificationExecutor   : Executing notifications at UTC: Mon Aug 26 09:21:00 UTC 2024
2024-08-26T09:21:00.009Z  INFO 1 --- [scheduler] [quartzScheduler_Worker-4] o.r.s.core.domain.NotificationExecutor   : Found notifications to execute: 1
2024-08-26T09:21:00.010Z  INFO 1 --- [scheduler] [quartzScheduler_Worker-4] o.r.s.core.domain.NotificationExecutor   : Executing notification: org.reminders.scheduler.core.domain.Notification@1e33cdea
2024-08-26T09:21:00.038Z  INFO 1 --- [scheduler] [quartzScheduler_Worker-4] o.a.k.clients.producer.ProducerConfig    : ProducerConfig values: 
```
4. In the worker, you will see email sending simulated
```shell
2024-08-26T09:21:00.110Z  INFO 1 --- [workers] [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] org.reminders.workers.Workers            : Sending email based on: {"id":"67ffcf76-0aa2-4d11-97fc-0e8d86ba9f21","nextNotificationUTC":"2024-08-26T09:21:00","userTimeZoneScheduledNotification":"2024-08-26T11:21:00","userTimeZone":"Europe/Madrid","userEmail":"usser@example.com","content":"TODO Add content","repeatable":true,"frequency":"WEEK","interval":1}
```
