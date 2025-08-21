package com.jobspring.jobspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
public class JobSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSpringApplication.class, args);
	}

}
