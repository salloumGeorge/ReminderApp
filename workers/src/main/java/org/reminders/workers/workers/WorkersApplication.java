package org.reminders.workers.workers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkersApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkersApplication.class, args);
	}

	/*
	* Handle event.
	* Check in cache for repeated events
	* Setup DLQ
	* send to email
	* */
}
