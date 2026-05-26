package de.dhbwravensburg.remoso.skywatch.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;
import de.dhbwravensburg.remoso.skywatch.repository.TrackedObjectRepository;
import lombok.AllArgsConstructor;

/**
 * Katrin Schaake, TIA25, Montag, 11.05.2026, Version: 0.2
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

	public TrackedObject create(TrackedObject entity){
		return repository.save(entity);
	}

	public Optional<TrackedObject> update(Long id, TrackedObject updated){
		return repository.findById(id).map(existing -> {
			existing.setName(updated.getName());
			existing.setEstimatedDiameterKm(updated.getEstimatedDiameterKm());
			existing.setPotentiallyHazardous(updated.isPotentiallyHazardous());
			existing.setCloseApproachDate(updated.getCloseApproachDate());
			existing.setMissDistanceKm(updated.getMissDistanceKm());
			return repository.save(existing);
		});
	}

	public Optional<TrackedObject> toggleHazardous(Long id) {
		Optional<TrackedObject> existing = findById(id);
		if (existing.isEmpty()){
			return Optional.empty();
		}
		TrackedObject entity = existing.get();
		entity.setPotentiallyHazardous(!entity.isPotentiallyHazardous());  // das ist der Toggle "!"
		TrackedObject saved = repository.save(entity);
		return Optional.of(saved);
	}

	public boolean delete(Long id){

		if (!repository.existsById(id)){
			return false;
		}
		repository.deleteById(id);
		return true;
	}
}
