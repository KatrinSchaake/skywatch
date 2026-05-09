package de.dhbwravensburg.remoso.skywatch.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Katrin Schaake, TIA25, Mittwoch, 06.05.2026, Version: 0.1
 */

@RestController
@RequestMapping("/api/info")
public class InfoController {

	@GetMapping
	public Map<String, String> info() {

		return Map.of("name", "SkyWatch API",
				"version", "0.1.0");
	}
}
