package com.ctrip.flight.faq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppSpringBootConfiguration {

	public static void main(String[] args) {
		// TODO: Remove before deployment: enable Artemis local mode for local service testing;
		System.setProperty("artemis.client.local.enabled", "true");
		SpringApplication.run(AppSpringBootConfiguration.class, args);
	}

}
