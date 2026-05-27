package de.dhbwravensburg.remoso.skywatch.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbwravensburg.remoso.skywatch.dto.TrackedObjectResponse;
import de.dhbwravensburg.remoso.skywatch.dto.nasa.NeoObject;
import de.dhbwravensburg.remoso.skywatch.mapper.NeoObjectMapper;
import de.dhbwravensburg.remoso.skywatch.mapper.TrackedObjectMapper;
import de.dhbwravensburg.remoso.skywatch.service.NasaNeoService;
import de.dhbwravensburg.remoso.skywatch.service.TrackedObjectService;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */

@RestController
@RequestMapping("/api/neo")
public class NeoController {

	private final NasaNeoService nasaService;
	private final TrackedObjectService trackedObjectService;

	public NeoController(NasaNeoService nasaService, TrackedObjectService trackedObjectService) {
		this.nasaService = nasaService;
		this.trackedObjectService = trackedObjectService;
	}

	@GetMapping("/today")
	public List<TrackedObjectResponse> today() {
		List<NeoObject> raw = nasaService.fetchObjectsForDate(LocalDate.now());
		return raw.stream()
				.map(NeoObjectMapper::toEntity)
				.map(TrackedObjectMapper::toResponse)
				.toList();
	}

	@GetMapping("/import")
	public ResponseEntity<Integer> importToday() {
		List<NeoObject> raw = nasaService.fetchObjectsForDate(LocalDate.now());

		raw.stream()
				.map(NeoObjectMapper::toEntity)
				.forEach(trackedObjectService::create);

		return ResponseEntity.ok(raw.size());
	}
}
