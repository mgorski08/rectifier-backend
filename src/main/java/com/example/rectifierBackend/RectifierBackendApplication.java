package com.example.rectifierBackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class RectifierBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(RectifierBackendApplication.class, args);
	}

}
