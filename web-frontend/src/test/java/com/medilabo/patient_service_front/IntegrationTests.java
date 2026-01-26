package com.medilabo.patient_service_front;

import com.medilabo.patient_service_front.client.PatientClient;
import com.medilabo.patient_service_front.models.Patient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientClient patientClient;

    @Value("${api.gateway.url}")
    protected String gatewayUrl;

    private static final String PATIENT_PATH = "/patients";

    @Test
    void testPatientClientIntegration() {
        List<Patient> patients = patientClient.getAll();

        assertThat(patients).isNotNull();
    }

    @Test
    public void getAll() {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_PATH)
                .build()
                .toUriString();

        ResponseEntity<List<Patient>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertTrue(response.hasBody());
        List<Patient> patientList = new ArrayList<>();
        assertThat(response.getBody()).isExactlyInstanceOf(patientList.getClass());
    }

}