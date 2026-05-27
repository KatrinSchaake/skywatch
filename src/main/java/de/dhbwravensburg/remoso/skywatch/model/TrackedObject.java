package de.dhbwravensburg.remoso.skywatch.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Katrin Schaake, TIA25, Donnerstag, 07.05.2026, Version: 0.3
 */

@Getter
@Setter
@NoArgsConstructor										// JPA braucht leeren Konstruktor
@EqualsAndHashCode 										// wichtig, für Vergleichen von Objekten später oder verwenden in Sets/Maps
@Entity													// sagt JPA: "Diese Klasse gehört zu einer Tabelle	"
public class TrackedObject {

	@Id													// Primärschlüssel
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// H2 vergibt id automatisch
	private Long id; 									//Klasse, kann null sein - wichtig für "neu" vs. "gespeichert"

	private String name;
	private double estimatedDiameterKm; 				//primitiver Datntyp, default 0.0
	private boolean potentiallyHazardous;
	private String closeApproachDate;
	private double missDistanceKm;

	@JsonIgnore
	@OneToMany(
			mappedBy = "trackedObject",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<ObservationSession> observations = new ArrayList<>();

	public TrackedObject(Long id, String name, double estimatedDiameterKm,
			boolean potentiallyHazardous, String closeApproachDate, double missDistanceKm) {
		this.id = id;
		this.name = name;
		this.estimatedDiameterKm = estimatedDiameterKm;
		this.potentiallyHazardous = potentiallyHazardous;
		this.closeApproachDate = closeApproachDate;
		this.missDistanceKm = missDistanceKm;
	}

	public void addObservation(ObservationSession observation) {
		observations.add(observation);
		observation.setTrackedObject(this);
	}

	public void removeObservation(ObservationSession observation) {
		observations.remove(observation);
		observation.setTrackedObject(null);
	}
}
