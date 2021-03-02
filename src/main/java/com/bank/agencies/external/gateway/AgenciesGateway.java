package com.bank.agencies.external.gateway;

import com.bank.agencies.domain.AgencyGatewayResponse;

import java.util.List;

public interface AgenciesGateway {
	
    List<AgencyGatewayResponse> findAllAgencies();
    
    List<AgencyGatewayResponse> findAllAgenciesSortedByStateAndCity();
}
