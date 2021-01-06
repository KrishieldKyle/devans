package com.haphor.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.haphor.social.dao")
public class DevansApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevansApplication.class, args);
		
	}

}
