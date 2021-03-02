package com.bank.agencies.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.domain.AgencyGroupedByStateJsonView;
import com.bank.agencies.domain.AgencyResponse;
import com.bank.agencies.service.AgencyService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgenciesController {

	private final AgencyService agencyService;

	public AgenciesController(AgencyService agencyService) {
		this.agencyService = agencyService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AgencyResponse>> findAllAgencies() {
		List<AgencyGatewayResponse> agencies = agencyService.execute();

		List<AgencyResponse> agencyResponse = agencies.stream()
				.map(agencyGateway -> AgencyResponse.AgencyResponseBuilder.anAgencyResponse()
						.bank(agencyGateway.getBank()).city(agencyGateway.getCity()).name(agencyGateway.getName())
						.state(agencyGateway.getState()).build())
				.collect(Collectors.toList());

		return new ResponseEntity<>(agencyResponse, HttpStatus.OK);
	}
	
	@JsonView(AgencyGroupedByStateJsonView.class)
	@GetMapping("/grouped-by-state")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Map<String, List<AgencyResponse>>> findAllAgenciesGroupedByState() {
		Map<String, List<AgencyResponse>> agenciesGroupedByState = agencyService.findAllAgenciesGroupedByState();
		return new ResponseEntity<>(agenciesGroupedByState, HttpStatus.OK);
	}
}
