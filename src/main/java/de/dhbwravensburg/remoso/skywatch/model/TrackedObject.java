package de.dhbwravensburg.remoso.skywatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Katrin Schaake, TIA25, Donnerstag, 07.05.2026, Version: 0.1
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // wichtig, für Vergleichen von Objekten später oder verwenden in Sets/Maps
@Entity
public class TrackedObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //Klasse, kann null sein - wichtig für "neu" vs. "gespeichert"

	private String name;
	private double estimatedDiameterKm; //primitiver Datntyp, default 0.0
	private boolean potentiallyHazardous;
	private String closeApproachDate;
	private double missDistanceKm;

	//statt getter und setter lomnok
}