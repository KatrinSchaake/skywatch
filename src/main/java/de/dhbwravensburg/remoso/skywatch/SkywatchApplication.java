package de.dhbwravensburg.remoso.skywatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@OpenAPIDefinition(
		info = @Info(
				title = "SkyWatch API",
				version = "0.1.0",
				description = "Near-Earth Object Monitor - REST API",
				contact = @Contact(
						name = "DHBW WebEng II Team",
						email = "webeng-team@dhbw-rav.de"
				)
		)
)

@SpringBootApplication
public class SkywatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SkywatchApplication.class, args);
	}

}
