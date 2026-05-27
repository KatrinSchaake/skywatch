package de.dhbwravensburg.remoso.skywatch.dto.nasa;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record NeoFeddResponse(int element_count,
		Map<String, List<NeoObject>> near_earth_objects) {}
