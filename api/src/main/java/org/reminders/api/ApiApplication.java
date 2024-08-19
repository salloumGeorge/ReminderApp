package org.reminders.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	/*
	* add api to create reminder
	* add psql database and save in timezone
	* add kafka to publish event message of created and dropped
	* deploy 2 instances with a load balancer / reverse proxy
	* */
}
