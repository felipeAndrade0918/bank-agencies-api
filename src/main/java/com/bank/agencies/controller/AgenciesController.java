package com.bank.agencies.controller;

import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.domain.AgencyResponse;
import com.bank.agencies.service.AgencyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgenciesController {

	private final AgencyService findAllAgenciesUseCase;

	public AgenciesController(AgencyService findAllAgenciesUseCase) {
		this.findAllAgenciesUseCase = findAllAgenciesUseCase;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AgencyResponse>> findAllAgencies() {
		List<AgencyGatewayResponse> agencies = findAllAgenciesUseCase.execute();

		List<AgencyResponse> agencyResponse = agencies.stream()
				.map(agencyGateway -> AgencyResponse.AgencyResponseBuilder.anAgencyResponse()
						.bank(agencyGateway.getBank()).city(agencyGateway.getCity()).name(agencyGateway.getName())
						.state(agencyGateway.getState()).build())
				.collect(Collectors.toList());

		return new ResponseEntity<>(agencyResponse, HttpStatus.OK);
	}
}
