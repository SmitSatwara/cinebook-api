package com.smitsatwara.cinebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CinebookApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinebookApiApplication.class, args);
	}

}
