package de.dhbwravensburg.remoso.skywatch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;
import de.dhbwravensburg.remoso.skywatch.repository.TrackedObjectRepository;

/**
 * Katrin Schaake, TIA25, Montag, 11.05.2026, Version: 0.1
 *
 * Legt Seed Daten an, wenn DB leer ist. Läuft genau einmal bei App-Start
 */
@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner seedTrackedObjects(TrackedObjectRepository repository) {
		return args -> {
			if (repository.count() > 0) {	// DB nicht leer, also nichts machen
				return;
			}
			repository.save(new TrackedObject(null, "2024 YR4", 0.07, false,
					"2032-12-22", 200_000.0));
			repository.save(new TrackedObject(null, "99942 Apophis", 0.37, true,
					"2029-04-13", 31_000.0));
			repository.save(new TrackedObject(null, "Bennu", 0.49, true,
					"2182-09-25", 750_000.0));
		};
	}
}
