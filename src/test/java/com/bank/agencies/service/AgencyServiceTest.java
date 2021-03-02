package com.bank.agencies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.domain.AgencyResponse;
import com.bank.agencies.external.gateway.AgenciesGateway;
import com.bank.agencies.fixture.AgenciesFixture;

public class AgencyServiceTest {

    final AgenciesGateway gateway = mock(AgenciesGateway.class);
    
    AgencyService agencyService;

    List<AgencyGatewayResponse> agencies;
    
    @BeforeEach
    public void setup() {
    	agencyService = new AgencyService(gateway);
    }

    @Test
    void shouldExecuteUseCaseFindAllBBAgenciesWithSuccess() {
        // given
        agencies = AgenciesFixture.gimmeBasicAgencies();
        when(agencyService.execute()).thenReturn(agencies);

        // when
        final List<AgencyGatewayResponse> response = agencyService.execute();

        // then
        verify(gateway, times(1)).findAllAgencies();

        assertNotNull(response);
        assertEquals(3, response.size());
        assertNotNull(response.get(0));
    }
    
    @Test
    void shouldListAllTheAgenciesAndGroupThemByState() {
    	// given
        agencies = AgenciesFixture.gimmeBasicAgencies();
        when(gateway.findAllAgenciesSortedByStateAndCity()).thenReturn(agencies);

        // when
        Map<String, List<AgencyResponse>> agenciesGroupedByState = agencyService.findAllAgenciesGroupedByState();

        // then
        verify(gateway, times(1)).findAllAgenciesSortedByStateAndCity();

        assertNotNull(agenciesGroupedByState);
        assertEquals(2, agenciesGroupedByState.size());
        assertEquals(1, agenciesGroupedByState.get("AC").size());
        assertEquals("JOAO PESSOA", agenciesGroupedByState.get("AC").get(0).getCity());
        assertEquals("BAURU", agenciesGroupedByState.get("SP").get(0).getCity());
        assertEquals("SAO JOSE DOS CAMPOS", agenciesGroupedByState.get("SP").get(1).getCity());
        assertEquals(2, agenciesGroupedByState.get("SP").size());
    }
}
