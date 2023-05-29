package ru.zaza.multitaskbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultiTaskBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiTaskBotApplication.class, args);
	}

}
