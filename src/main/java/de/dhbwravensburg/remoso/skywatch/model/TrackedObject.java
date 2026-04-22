package de.dhbwravensburg.remoso.skywatch.model;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class TrackedObject {

	private Long id; //Klasse
	private String name;
	private double estimatedDiameterKm; //primitiver Datntyp
	private boolean potentiallyHazardous;

	//statt getter und setter lomnok

	public static List<TrackedObject> generateTestData() {
		return List.of(
				new TrackedObject(1L, "Apophis", 0.375, true),
				new TrackedObject(2L, "Bennu", 0.492, true),
				new TrackedObject(3L, "Eros", 16.84, false)
		);
	}
}