package com.code.camping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CampingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampingApplication.class, args);
	}

}
