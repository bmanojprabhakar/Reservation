package com.codebees.reservation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Reservation service REST API Documentation",
				description = "Documentation of the basic reservation service to enable ticket creation, modification and deletion",
				version = "v1",
				contact = @Contact(
						name = "Manoj Prabhakar",
						email = "bmanojprabhakar@gmail.com",
						url = "https://www.bmanojprabhakar.ml"
				)
		)
)
public class ReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}

}
