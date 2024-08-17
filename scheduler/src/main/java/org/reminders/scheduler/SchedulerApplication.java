package org.reminders.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApplication.class, args);
	}

	/*
	 * handle events save in UTC in mysql db
	 * schedule job every 5 minutes
	 * add kafka to publish event message on created events
	 * deploy an instance in docker compose
	 *
	 * */
}
