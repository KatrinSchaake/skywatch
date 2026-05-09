package de.dhbwravensburg.remoso.skywatch.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbwravensburg.remoso.skywatch.dto.TrackedObjectRequest;
import de.dhbwravensburg.remoso.skywatch.dto.TrackedObjectResponse;
import de.dhbwravensburg.remoso.skywatch.mapper.TrackedObjectMapper;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;
import de.dhbwravensburg.remoso.skywatch.service.TrackedObjectService;

/**
 * Katrin Schaake, TIA25, Sonnabend, 09.05.2026, Version: 0.2
 */
@RestController		// diese Klasse ist ein REST-Controller, alle Methoden geben JSON zurück
@RequestMapping("/api/tracked-objects")
public class TrackedObjectController {

	private final TrackedObjectService service;		// ein Feld

	// Konstruktor mit dem SErvice als Parameter
	public TrackedObjectController(TrackedObjectService service) {
		this.service = service;
	}

	@GetMapping
	public List<TrackedObjectResponse> getAll() {
		return service.findAll().stream()
				.map(TrackedObjectMapper::toResponse)
				.toList();
	}

	@GetMapping("/{id}") // @PathVariable holt Wert aus URL - GET /api/tracked-objects/42
							// -> @PathVariable Long id ergibt id =42
	public ResponseEntity<TrackedObjectResponse> getById(@PathVariable Long id) {
		return service.findById(id)
				.map(TrackedObjectMapper::toResponse)
				.map(ResponseEntity::ok) // wenn was drin ist, pack es in ResponseEntity.ok(...) =200 OK, wenn leer, bleib leer
				.orElse(ResponseEntity.notFound().build()); // wenn am Ende immernoch leer, gib 404 zurück
	}

	@PostMapping
	public ResponseEntity<TrackedObjectResponse> create(
			@RequestBody TrackedObjectRequest request) { //JSON-Body in Java-Objekt - Spring-Magie

		TrackedObject created = service.create(
				TrackedObjectMapper.toEntity(null, request));

		TrackedObjectResponse response = TrackedObjectMapper.toResponse(created);

		return ResponseEntity // setzt Statuscode 201 Created und den Location-Header
				.created(URI.create("/api/tracked-objects/" + created.getId()))
				.body(response); // packt das Response-DTO als JSON in den Body
	}

	@PutMapping("/{id}")
	public ResponseEntity<TrackedObjectResponse> update(
			@PathVariable Long id,
			@RequestBody TrackedObjectRequest request) {

		return service.update(id, TrackedObjectMapper.toEntity(id, request))
				.map(TrackedObjectMapper::toResponse)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.delete(id)) {
			return ResponseEntity.noContent().build(); // 204
		}
		return ResponseEntity.notFound().build(); // 404
	}

	@GetMapping("/hazardous")
	public List<TrackedObjectResponse> getHazardous() {
		return service.findAll().stream()
				.filter(TrackedObject::isPotentiallyHazardous)
				.map(TrackedObjectMapper::toResponse)
				.toList();
	}

	@GetMapping("/count")
	public int getCount() {
		return service.findAll().size();
	}
}
