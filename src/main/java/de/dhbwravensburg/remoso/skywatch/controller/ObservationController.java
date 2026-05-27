package de.dhbwravensburg.remoso.skywatch.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbwravensburg.remoso.skywatch.model.ObservationSession;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;
import de.dhbwravensburg.remoso.skywatch.repository.ObservationSessionRepository;
import de.dhbwravensburg.remoso.skywatch.repository.TrackedObjectRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

/**
 * Katrin Schaake, TIA25, Version: 0.1
 *
 * Verschachtelte URL: /api/tracked-objects/{id}/observations
 * Eine Beobachtung existiert nur im Kontext eines TrackedObject.
 */
@RestController
@RequestMapping("/api/tracked-objects/{trackedObjectId}/observations")
public class ObservationController {

	private final TrackedObjectRepository trackedObjectRepository;
	private final ObservationSessionRepository observationRepository;

	public ObservationController(TrackedObjectRepository trackedObjectRepository,
			ObservationSessionRepository observationRepository) {
		this.trackedObjectRepository = trackedObjectRepository;
		this.observationRepository = observationRepository;
	}

	// Request-DTO direkt als Record in der Controller-Klasse (klein genug)
	public record ObservationRequest(

			@NotBlank(message = "observerName must not be blank")
			String observerName,

			@Past(message = "observedAt must be in the past")
			LocalDateTime observedAt,

			@NotBlank(message = "location must not be blank")
			String location,

			@Size(max = 500, message = "notes must be at most 500 characters")
			String notes

	) {}


	// GET /api/tracked-objects/2/observations
	@GetMapping
	public List<ObservationSession> getAll(@PathVariable Long trackedObjectId) {
		return observationRepository.findByTrackedObjectId(trackedObjectId);
	}

	// POST /api/tracked-objects/2/observations
	@PostMapping
	public ResponseEntity<ObservationSession> create(
			@PathVariable Long trackedObjectId,
			@RequestBody ObservationRequest request) {

		// Erst: existiert der Parent überhaupt?
		TrackedObject parent = trackedObjectRepository.findById(trackedObjectId)
				.orElse(null);
		if (parent == null) {
			return ResponseEntity.notFound().build();
		}
		// Observation erstellen und speichern
		ObservationSession observation = new ObservationSession(
				request.observerName(),
				request.observedAt(),
				request.location(),
				request.notes(),
				parent
		);
		ObservationSession saved = observationRepository.save(observation);

		return ResponseEntity
				.created(URI.create("/api/tracked-objects/" + trackedObjectId
						+ "/observations/" + saved.getId()))
				.body(saved);
	}
}