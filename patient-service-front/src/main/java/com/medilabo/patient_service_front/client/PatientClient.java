package com.medilabo.patient_service_front.client;

import com.medilabo.patient_service_front.models.Patient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class PatientClient extends AbstractClient {

    private static final String PATIENT_PATH = "/patients";

    public Patient getPatientById(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_PATH)
                .buildAndExpand(patientId)
                .toUriString();

        return restTemplate.getForObject(url, Patient.class);
    }


    public void savePatient(Patient patient) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_PATH)
                .buildAndExpand(patient.getId())
                .toUriString();

        restTemplate.put(url, patient);
    }

    public List<Patient> getAllPatients() {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(PATIENT_PATH)
                .toUriString();

        ResponseEntity<List<Patient>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

}
