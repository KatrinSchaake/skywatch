package de.dhbwravensburg.remoso.skywatch.exception;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */
public class TrackedObjectNotFoundException extends RuntimeException {

	public TrackedObjectNotFoundException(Long id) {
		super("TrackedObject with id " + id + " not found");
	}
}
