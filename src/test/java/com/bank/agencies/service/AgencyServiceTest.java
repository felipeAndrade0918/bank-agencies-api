package com.bank.agencies.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.external.gateway.AgenciesGateway;
import com.bank.agencies.fixture.AgenciesFixture;

public class AgencyServiceTest {

    final AgenciesGateway gateway = mock(AgenciesGateway.class);
    final AgencyService findAllAgencies = new AgencyService(gateway);

    List<AgencyGatewayResponse> agencies;

    @Test
    void shouldExecuteUseCaseFindAllBBAgenciesWithSuccess() {
        // given
        agencies = AgenciesFixture.gimmeBasicAgencies();
        when(findAllAgencies.execute()).thenReturn(agencies);

        // when
        final List<AgencyGatewayResponse> response = findAllAgencies.execute();

        // then
        verify(gateway, times(1)).findAllAgencies();

        assertNotNull(response);
        assertNotNull(response.get(0));
    }
}
