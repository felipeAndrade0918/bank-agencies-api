package com.bank.agencies.service;

import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.external.gateway.AgenciesGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    private final AgenciesGateway bankResourcesGateway;

    public AgencyService(AgenciesGateway bankResourcesGateway) {
        this.bankResourcesGateway = bankResourcesGateway;
    }

    public List<AgencyGatewayResponse> execute() {
        return bankResourcesGateway.findAllAgencies();
    }
}
