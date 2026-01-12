package com.medilabo.patient_service_front;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class PatientServiceFrontApplicationTests {

	WebTestClient webClient = WebTestClient.bindToServer()
			.baseUrl("http://localhost:8080")
			.build();


	@Test
	void givenRunningService_whenGetSingleCampaign_thenExpectStatus() {
		webClient.get()
				.uri("/patient/list")
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void contextLoads() {
	}

}
