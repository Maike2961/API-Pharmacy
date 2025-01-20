package io.github.farmacia.Farmacia.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FarmaciaDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmaciaDbApplication.class, args);
	}

}
