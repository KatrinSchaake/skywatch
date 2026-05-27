package de.dhbwravensburg.remoso.skywatch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Katrin Schaake, TIA25, Donnerstag, 07.05.2026, Version: 0.2
 *
 * record: kompakte Schreibweise für unveränderliche Datenklasse
 *	-> Java generiert automatisch Konstuktoren, "Getter" ohne get, equals()... und alle private final
 *-> perfekt für DTOs - nicht mehr verändert
 *
 * DTO für eingehende Daten (POST/PUT)
 * ID wird von Server vergeben, nicht hier
 */
public record TrackedObjectRequest(

		@NotBlank(message = "name must not be blank")
		@Size(max = 100, message = "name must be at most 100 characters")
		String name,

		@Positive(message = "estimatedDiameterKm must be positive")
		double estimatedDiameterKm,

		boolean potentiallyHazardous,

		@NotBlank(message = "closeApproachDate must not be blank")
		@Pattern(
				regexp = "^\\d{4}-\\d{2}-d\\{2}$}",
				message = "closeApproachDate must match format YYYY-MM-DD"
		)
		String closeApproachDate,

		@PositiveOrZero(message = "missDistanceKm must be zero or positive")
		double missDistanceKm
){}
