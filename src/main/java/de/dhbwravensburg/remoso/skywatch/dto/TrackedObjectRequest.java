package de.dhbwravensburg.remoso.skywatch.dto;

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
		String name,
		double estimatedDiameterKm,
		boolean potentiallyHazardous,
		String closeApproachDate,
		double missDistanceKm
){}
