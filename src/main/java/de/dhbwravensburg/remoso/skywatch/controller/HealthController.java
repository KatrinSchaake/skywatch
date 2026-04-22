package de.dhbwravensburg.remoso.skywatch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/api/health")
	public Map<String, String> health() {

		return Map.of(
				"status", "UP",
				"application", "SkyWatch",
				"version", "0.1.0"
		);
	}

}
