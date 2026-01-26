package com.medilabo.patient_service_front.client;

import com.medilabo.patient_service_front.models.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Component
public class PatientClient extends AbstractClient {

    private static final String PATIENT_PATH = "/patients";
    private static final String PATIENT_ID_PATH = PATIENT_PATH + "/{patientId}";


    public List<Patient> getAll() {
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
        return response.getBody();
    }

    public Patient getById(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_ID_PATH)
                .buildAndExpand(patientId)
                .toUriString();

        return restTemplate.getForObject(url, Patient.class);
    }

    public void update(Patient patient) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_ID_PATH)
                .buildAndExpand(patient.getId())
                .toUriString();
        HttpEntity<Patient> request = new HttpEntity<>(patient);
        restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                Void.class
        );
    }


    public Patient save(Patient patient) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_PATH)
                .build()
                .toUriString();
        HttpEntity<Patient> request = new HttpEntity<>(patient);
        HttpEntity<Patient> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                Patient.class
        );
        log.info("patient is back {}", response.getBody());
        return response.getBody();
    }

    public void delete(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_ID_PATH)
                .buildAndExpand(patientId)
                .toUriString();
        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

}
