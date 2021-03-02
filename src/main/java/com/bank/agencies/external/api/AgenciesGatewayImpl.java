package com.bank.agencies.external.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.external.gateway.AgenciesGateway;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AgenciesGatewayImpl implements AgenciesGateway {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Value( "${agencies.service.base.url}" )
    private String baseUrl;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<AgencyGatewayResponse> findAllAgencies() {
        URI apiURI = getBaseParams().build().toUri();

        HttpRequest request = buildRequest(apiURI);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseContent = getResponseContent(response);
            return Arrays.asList(mapper.readValue(responseContent, AgencyGatewayResponse[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error when trying get all Agencies from the API");
        }
    }

	@Override
	public List<AgencyGatewayResponse> findAllAgenciesSortedByStateAndCity() {
		URI apiURI = getBaseParams()
				.queryParam("$orderby", "UF asc,Municipio asc")
				.build()
				.toUri();

        HttpRequest request = buildRequest(apiURI);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseContent = getResponseContent(response);
            return Arrays.asList(mapper.readValue(responseContent, AgencyGatewayResponse[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error when trying get all Agencies grouped by state from the API");
        }
	}

	private HttpRequest buildRequest(URI apiURI) {
		HttpRequest request = HttpRequest.newBuilder()
        		.GET()
        		.uri(apiURI)
        		.build();
		return request;
	}

	private UriComponentsBuilder getBaseParams() {
		return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("$format", "json");
	}
	
	private String getResponseContent(HttpResponse<String> response) throws IOException {
		if (response.statusCode() == HttpStatus.OK.value()) {
            JsonNode parent = mapper.readTree(response.body());
            return parent.get("value").toString();
        }
		
		throw new IllegalStateException("Response did not return 200");
	}
}
