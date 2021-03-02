package com.bank.agencies.fixture;

import com.bank.agencies.domain.AgencyGatewayResponse;

import java.util.List;

public class AgenciesFixture {

	public static List<AgencyGatewayResponse> gimmeBasicAgencies() {
		return List.of(
				AgencyGatewayResponse.AgencyGatewayResponseBuilder.anAgency().bank("BANCO DO BRASIL S.A.")
				.name("EMPRESA JOAO PESSOA").city("JOAO PESSOA").state("AC").build(),
				AgencyGatewayResponse.AgencyGatewayResponseBuilder.anAgency().bank("BANCO DO BRASIL S.A.")
				.name("EMPRESA BAURU").city("BAURU").state("SP").build(),
				AgencyGatewayResponse.AgencyGatewayResponseBuilder.anAgency().bank("BANCO DO BRASIL S.A.")
						.name("EMPRESA SAO JOSE DOS CAMPOS").city("SAO JOSE DOS CAMPOS").state("SP").build()
				)
				;
	}
}
