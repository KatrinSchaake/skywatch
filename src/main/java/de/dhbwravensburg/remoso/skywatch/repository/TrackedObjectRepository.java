package de.dhbwravensburg.remoso.skywatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;

/**
 * Katrin Schaake, TIA25, Montag, 11.05.2026, Version: 0.1
 */
public interface TrackedObjectRepository extends JpaRepository<TrackedObject, Long> {}
