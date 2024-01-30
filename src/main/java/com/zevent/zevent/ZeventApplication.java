package com.zevent.zevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ZeventApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeventApplication.class, args);
	}

}
