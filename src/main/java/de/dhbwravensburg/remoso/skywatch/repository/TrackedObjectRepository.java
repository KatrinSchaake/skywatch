package de.dhbwravensburg.remoso.skywatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;

/**
 * Katrin Schaake, TIA25, Version: 0.2
 *
 * Derived Queries: Spring Data baut SQL automatisch aus dem Methodennamen.
 */
public interface TrackedObjectRepository extends JpaRepository<TrackedObject, Long> {

	// SELECT * FROM tracked_object WHERE potentially_hazardous = ?
	List<TrackedObject> findByPotentiallyHazardous(boolean potentiallyHazardous);

	// SELECT * FROM tracked_object WHERE LOWER(name) LIKE LOWER(%?%)
	List<TrackedObject> findByNameContainingIgnoreCase(String namePart);

	// Custom Query: alle Asteroiden mit Durchmesser > minSize
	@Query("""
            SELECT t FROM TrackedObject t
            WHERE t.estimatedDiameterKm > :minSize
            """)
	List<TrackedObject> findLargerThan(double minSize);

	@Query("""
        SELECT t FROM TrackedObject t
        WHERE t.estimatedDiameterKm > :minSize
          AND t.potentiallyHazardous = true
        """)
	List<TrackedObject> findLargeHazardous(double minSize);

	@Query(value = "SELECT * FROM tracked_object WHERE close_approach_date < CURRENT_DATE",
			nativeQuery = true)
	List<TrackedObject> findPastApproaches();
}