package de.dhbwravensburg.remoso.skywatch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.dhbwravensburg.remoso.skywatch.exception.TrackedObjectNotFoundException;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;
import de.dhbwravensburg.remoso.skywatch.repository.TrackedObjectRepository;

/**
 * Katrin Schaake, TIA25, Montag, 11.05.2026, Version: 0.3
 *
 * Service: arbeitet nur mit dem TrackedObject (kein HTTP im Service)
 * repository statt store
 */

@Service //Spring-Magie:
public class TrackedObjectService {

	private final TrackedObjectRepository repository;

	public TrackedObjectService(TrackedObjectRepository repository) {
		this.repository = repository;
	}

	public List<TrackedObject> findAll() {
		return this.repository.findAll();
	}

	public Optional<TrackedObject> findById(Long id) {
		return this.repository.findById(id);
	}

	public List<TrackedObject> findHazardous() {
		return repository.findByPotentiallyHazardous(true);
	}

	public List<TrackedObject> findLargerThan(double minSizeKm) {
		return repository.findLargerThan(minSizeKm);
	}

	public TrackedObject create(TrackedObject entity){
		return repository.save(entity);
	}

	public TrackedObject getOrThrow(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new TrackedObjectNotFoundException(id));
	}

	public TrackedObject update(Long id, TrackedObject updated){
		TrackedObject existing = getOrThrow(id);
		existing.setName(updated.getName());
		existing.setEstimatedDiameterKm(updated.getEstimatedDiameterKm());
		existing.setPotentiallyHazardous(updated.isPotentiallyHazardous());
		existing.setCloseApproachDate(updated.getCloseApproachDate());
		existing.setMissDistanceKm(updated.getMissDistanceKm());
		return repository.save(existing);
	}

	public TrackedObject toggleHazardous(Long id) {
		TrackedObject entity = getOrThrow(id);
		entity.setPotentiallyHazardous(!entity.isPotentiallyHazardous());  // das ist der Toggle "!"
		return repository.save(entity);
	}

	public List<TrackedObject> searchByName(String namePart) {
		return repository.findByNameContainingIgnoreCase(namePart);
	}

	public long count() {
		return repository.count();
	}

	public void delete(Long id){

		if (!repository.existsById(id)){
			throw new TrackedObjectNotFoundException(id);
		}
		repository.deleteById(id);
	}
}
