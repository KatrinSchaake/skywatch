package de.dhbwravensburg.remoso.skywatch.mapper;

import de.dhbwravensburg.remoso.skywatch.dto.nasa.NeoObject;
import de.dhbwravensburg.remoso.skywatch.model.TrackedObject;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */
public final class NeoObjectMapper {

	private NeoObjectMapper() {
	}

	public static TrackedObject toEntity(NeoObject source) {

		double avgDiameter = (
				source.estimated_diameter().kilometers().estimated_diameter_min()
						+ source.estimated_diameter().kilometers().estimated_diameter_max()
		) / 2.0;

		String approachDate = source.close_approach_data().isEmpty()
				? null
				: source.close_approach_data().get(0).close_approach_date();

		double missDistanceKm = source.close_approach_data().isEmpty()
				? 0.0
				: Double.parseDouble(
						source.close_approach_data().get(0).miss_distance().kilometers()
				);

		return new TrackedObject(
				null,
				source.name(),
				avgDiameter,
				source.is_potentially_hazardous_asteroid(),
				approachDate,
				missDistanceKm
		);
	}
}
