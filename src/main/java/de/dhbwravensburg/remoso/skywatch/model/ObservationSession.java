package de.dhbwravensburg.remoso.skywatch.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Katrin Schaake, TIA25, Version: 0.1
 *
 * Eine Beobachtungs-Session für einen Asteroiden.
 * Viele Beobachtungen können zu einem TrackedObject gehören (n:1).
 */
@Entity
public class ObservationSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String observerName;
	private LocalDateTime observedAt;
	private String location;
	private String notes;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tracked_object_id")
	private TrackedObject trackedObject;

	protected ObservationSession() {} // JPA braucht leeren Konstruktor

	public ObservationSession(String observerName, LocalDateTime observedAt,
			String location, String notes,
			TrackedObject trackedObject) {
		this.observerName = observerName;
		this.observedAt = observedAt;
		this.location = location;
		this.notes = notes;
		this.trackedObject = trackedObject;
	}

	// Getter
	public Long getId() { return id; }
	public String getObserverName() { return observerName; }
	public LocalDateTime getObservedAt() { return observedAt; }
	public String getLocation() { return location; }
	public String getNotes() { return notes; }
	public TrackedObject getTrackedObject() { return trackedObject; }

	// Setter (kein Setter für id!)
	public void setObserverName(String v) { this.observerName = v; }
	public void setObservedAt(LocalDateTime v) { this.observedAt = v; }
	public void setLocation(String v) { this.location = v; }
	public void setNotes(String v) { this.notes = v; }
	public void setTrackedObject(TrackedObject v) { this.trackedObject = v; }
}