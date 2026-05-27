package de.dhbwravensburg.remoso.skywatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import de.dhbwravensburg.remoso.skywatch.model.ObservationSession;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */
public interface ObservationSessionRepository extends JpaRepository<ObservationSession, Long> {

	List<ObservationSession> findByTrackedObjectId(Long trackedObjectId);

}
