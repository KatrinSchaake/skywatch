package de.dhbwravensburg.remoso.skywatch.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.dhbwravensburg.remoso.skywatch.dto.TrackedObjectRequest;
import de.dhbwravensburg.remoso.skywatch.dto.TrackedObjectResponse;
import de.dhbwravensburg.remoso.skywatch.mapper.TrackedObjectMapper;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;
import de.dhbwravensburg.remoso.skywatch.service.TrackedObjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

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

	// GET /api/tracked-objects/large?minSize=0.3 bzw. /api/tracked-object/large mit > 0.3km
	@GetMapping("/large")
	public List<TrackedObjectResponse> getLarge(
			@RequestParam(defaultValue = "0.3") double minSize) {
		return service.findLargerThan(minSize).stream()
				.map(TrackedObjectMapper::toResponse)
				.toList();
	}

	@GetMapping("/{id}")
	public TrackedObjectResponse getById(@PathVariable Long id) {
		return TrackedObjectMapper.toResponse(service.getOrThrow(id));
	}

	@Operation(summary = "Create a new tracked object")
	@ApiResponse(responseCode = "201", description = "Created")
	@ApiResponse(responseCode = "400", description = "Validation failed")
	@PostMapping
	public ResponseEntity<TrackedObjectResponse> create(
			@Valid @RequestBody TrackedObjectRequest request) {

		TrackedObject created = service.create(TrackedObjectMapper.toEntity(null, request));
		TrackedObjectResponse response = TrackedObjectMapper.toResponse(created);
		return ResponseEntity 					// setzt Statuscode 201 Created und den Location-Header
				.created(URI.create("/api/tracked-objects/" + created.getId()))
				.body(response); 				// packt das Response-DTO als JSON in den Body
	}

	@PutMapping("/{id}")
	public ResponseEntity<TrackedObjectResponse> update(
			@PathVariable Long id,
			@Valid @RequestBody TrackedObjectRequest request) {

		TrackedObject updated = service.update(id, TrackedObjectMapper.toEntity(id, request));
		return ResponseEntity.ok(TrackedObjectMapper.toResponse(updated));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@GetMapping("/hazardous")
	public List<TrackedObjectResponse> getHazardous() {
		return service.findHazardous().stream()
				.map(TrackedObjectMapper::toResponse)
				.toList();
	}

	@PatchMapping("/{id}/hazardous")
	public ResponseEntity<TrackedObjectResponse> toggleHazardous(@PathVariable Long id) {
		TrackedObject updated = service.toggleHazardous(id);
		return ResponseEntity.ok(TrackedObjectMapper.toResponse(updated));
	}

	@GetMapping("/search")
	public List<TrackedObjectResponse> search(@RequestParam String name) {
		return service.searchByName(name).stream()
				.map(TrackedObjectMapper::toResponse)
				.toList();
	}

	@GetMapping("/count")
	public long getCount() {
		return service.count();
	}
}
