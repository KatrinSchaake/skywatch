package de.dhbwravensburg.remoso.skywatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Katrin Schaake, TIA25, Mittwoch, 27.05.2026, Version: 0.1
 */
@Configuration
public class NasaApiConfig {

	@Value("${nasa.api.base-url}")
	private String baseUrl;

	@Value("${nasa.api.connect-timeout-ms}")
	private int connectTimeoutMs;

	@Value("${nasa.api.read-timeout-ms}")
	private int readTimeoutMs;

	@Bean
	public RestClient nasaRestClient() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(connectTimeoutMs);
		requestFactory.setReadTimeout(readTimeoutMs);

		return RestClient.builder()
				.baseUrl(baseUrl)
				.requestFactory(requestFactory)
				.defaultHeader(HttpHeaders.ACCEPT, "application/json")
				.build();
	}
}
