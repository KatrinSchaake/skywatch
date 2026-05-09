package de.dhbwravensburg.remoso.skywatch.mapper;

import de.dhbwravensburg.remoso.skywatch.dto.TrackedObjectRequest;
import de.dhbwravensburg.remoso.skywatch.dto.TrackedObjectResponse;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;

/**
 * Katrin Schaake, TIA25, Donnerstag, 07.05.2026, Version: 0.1
 *
 * Mapper ist der Verbinder zw. DTOs (Client) und Model (Logik = Service, DB etc.)
 */

public final class TrackedObjectMapper { // final, weil niemand davon erben darf

	private TrackedObjectMapper() {} // privater Konstruktor, weil niemand ein Objekt davon erzeugen darf

	public static TrackedObject toEntity(Long id, TrackedObjectRequest request) { //record ohne "get"
		return new TrackedObject(
				id,
				request.name(),
				request.estimatedDiameterKm(),
				request.potentiallyHazardous(),
				request.closeApproachDate(),
				request.missDistanceKm()
		); // ruft den @AllArgsConstructor von TrackedObject auf
	}

	/* beide Methoden static: Methode gehört zur Klasse, nicht zu einem  Objekt
	* Mapper hat keine eigenen Daten (kein Zustand, keine Felder) - kein Grund, Objekt zu erzeugen */

	public static TrackedObjectResponse toResponse(TrackedObject entity) {//class mit "get"/"is"
		return new TrackedObjectResponse(
				entity.getId(),
				entity.getName(),
				entity.getEstimatedDiameterKm(),
				entity.isPotentiallyHazardous(), // wegen Lombok-getter "is" statt "get" für boolean
				entity.getCloseApproachDate(),
				entity.getMissDistanceKm()
		);
	}
}
