package com.traveljoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TraveljoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraveljoyApplication.class, args);
	}

}
