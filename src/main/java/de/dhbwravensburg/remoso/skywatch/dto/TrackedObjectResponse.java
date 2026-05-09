package de.dhbwravensburg.remoso.skywatch.dto;

/**
 * Katrin Schaake, TIA25, Donnerstag, 07.05.2026, Version: 0.1
 *
 * record: kompakte Schreibweise für unveränderliche Datenklasse
 *	-> Java generiert automatisch Konstuktoren, "Getter" ohne get, equals()... und alle private final
 *
 * DTO für ausgehende Daten (Response), enthält ID, Server meldet diese zurück
 */
public record TrackedObjectResponse(
		Long id,
		String name,
		double estimatedDiameterKm,
		boolean potentiallyHazardous,
		String closeApproachData,
		double missDistanceKm
){}
