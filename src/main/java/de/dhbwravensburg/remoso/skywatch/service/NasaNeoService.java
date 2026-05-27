package de.dhbwravensburg.remoso.skywatch.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import de.dhbwravensburg.remoso.skywatch.dto.nasa.NeoFeedResponse;
import de.dhbwravensburg.remoso.skywatch.dto.nasa.NeoObject;
import de.dhbwravensburg.remoso.skywatch.exception.ExternalApiException;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */
@Service
public class NasaNeoService {

	private final RestClient nasaRestClient;
	private final String apiKey;
	private final RestClient restClient;

	// dependencies injection
	public NasaNeoService(RestClient restClient, @Value("${nasa.api.key}") String apiKey) {
		this.nasaRestClient = restClient;
		this.apiKey = apiKey;
		this.restClient = restClient;
	}

	public List<NeoObject> fetchObjectsForDate(LocalDate date) {

		try {
			NeoFeedResponse response = nasaRestClient.get()
					.uri(uriBuilder -> uriBuilder
							.path("/neo/rest/v1/feed")
							.queryParam("start_date", date.toString())
							.queryParam("end_date", date.toString())
							.queryParam("api_key", apiKey)
							.build()
					).retrieve()
					.onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
						throw new ExternalApiException("NASA API client error: " + res.getStatusCode());
					})
					.onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
						throw new ExternalApiException("NASA API server error: " + res.getStatusCode());
					})
					.body(NeoFeedResponse.class);
			if (response == null || response.near_earth_objects() == null) {
				return Collections.emptyList();
			}

			return response.near_earth_objects().getOrDefault(date.toString(), Collections.emptyList());

		} catch(RestClientException e) {
			throw new ExternalApiException("Failed to call NASA API: " + e.getMessage(), e);
		}
	}

}
