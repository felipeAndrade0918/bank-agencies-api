package com.bank.agencies.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.domain.AgencyResponse;
import com.bank.agencies.external.gateway.AgenciesGateway;

@Service
public class AgencyService {

    private final AgenciesGateway bankResourcesGateway;

    public AgencyService(AgenciesGateway bankResourcesGateway) {
        this.bankResourcesGateway = bankResourcesGateway;
    }

    public List<AgencyGatewayResponse> execute() {
        return bankResourcesGateway.findAllAgencies();
    }
    
    public Map<String, List<AgencyResponse>> findAllAgenciesGroupedByState() {
    	List<AgencyGatewayResponse> agencies = bankResourcesGateway.findAllAgenciesSortedByStateAndCity();
    	
    	return agencies.stream()
				.map(agencyGateway -> AgencyResponse.AgencyResponseBuilder.anAgencyResponse()
						.bank(agencyGateway.getBank()).city(agencyGateway.getCity()).name(agencyGateway.getName())
						.state(agencyGateway.getState()).build())
				.collect(Collectors.groupingBy(AgencyResponse::getState, LinkedHashMap::new, Collectors.toList()));
    }
}
