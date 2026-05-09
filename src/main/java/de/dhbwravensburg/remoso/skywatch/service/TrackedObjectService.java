package de.dhbwravensburg.remoso.skywatch.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;

/**
 * Katrin Schaake, TIA25, Donnerstag, 07.05.2026, Version: 0.1
 *
 * Service: arbeitet nur mit dem TrackedObject (kein HTTP im Service)
 */

@Service //Spring-Magie:
public class TrackedObjectService {

	private final ConcurrentHashMap<Long, TrackedObject> store = new ConcurrentHashMap<>();
	private final AtomicLong idGenerator = new AtomicLong(1); //eindeutige ID über die Laufzeit

	//constructor - diesmal selbst, weil was spezielles vor ...
	public TrackedObjectService(){
		// seed data for development
		create(new TrackedObject(
				null,"2024 YR4",0.07, false,
				"2032-12-22", 200_000.0));
		create(new TrackedObject(null, "99942 Apophis", 0.37, true,
				"2029-04-13", 31_000.0));
		create(new TrackedObject(null, "Bennu",
				0.49, true,
				"2182-09-25", 750_000.0));
	}

	public List<TrackedObject> findAll() {
		return List.copyOf(store.values());
	}

	//man bekommt ein Objekt zurück, dass entweder null oder ben ein TrackedObject im Bauch hat
	public Optional<TrackedObject> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	public TrackedObject create(TrackedObject entity){
		Long newId = idGenerator.getAndIncrement(); //eine neue ID und plus 1 gerechnet
		entity.setId(newId);
		store.put(newId, entity);
		return entity;
	}

	public Optional<TrackedObject> update(Long id, TrackedObject entity){
		if (!store.containsKey(id)){
			return Optional.empty(); // wenn ID noch nicht existiert, dann macht Cotroller eine 404 daraus
		}
		entity.setId(id);
		store.put(id, entity);
		return Optional.of(entity);
	}

	public Optional<TrackedObject> toggleHazardous(Long id) {
		Optional<TrackedObject> existing = findById(id);
		if (existing.isEmpty()){
			return Optional.empty();
		}
		TrackedObject entity = existing.get();
		entity.setPotentiallyHazardous(!entity.isPotentiallyHazardous());  // das ist der Toggle "!"
		store.put(id, entity);		// speichern (technisch nicht notwendig, aber sicherer)
		return Optional.of(entity);
	}

	public boolean delete(Long id){
		return store.remove(id) != null;
	}
}
