package de.dhbwravensburg.remoso.skywatch.dto.nasa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NeoObject(
		String id,
		String name,
		EstimatedDiameter estimated_diameter,
		boolean is_potentially_hazardous_asteroid,
		List<CloseApproachData> close_approach_data
) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record EstimatedDiameter(Kilometers kilometers) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Kilometers(
			double estimated_diameter_min,
			double estimated_diameter_max
	) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record CloseApproachData(
			String close_approach_date,
			MissDistance miss_distance
	) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record MissDistance(String kilometers) {}
}
