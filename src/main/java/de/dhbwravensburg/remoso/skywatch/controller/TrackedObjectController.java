package de.dhbwravensburg.remoso.skywatch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;

@RestController
@RequestMapping("/api/tracked-objects")
public class TrackedObjectController {

	private final List<TrackedObject> trackedObjects = TrackedObject.generateTestData();

	@GetMapping
	public List<TrackedObject> getAll() {
		return this.trackedObjects;
	}

	@GetMapping("/{id}")
	public TrackedObject getById(@PathVariable Long id) {

		return this.trackedObjects.stream()
				.filter(obj -> obj.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	@GetMapping("/hazardous")
	public List<TrackedObject> getHazardous() {
		return this.trackedObjects.stream()
				.filter(TrackedObject::isPotentiallyHazardous)
				.toList();
	}
}
