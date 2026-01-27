package com.medilabo.risk_assessment.client;

import com.medilabo.risk_assessment.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PatientClient {

    @Autowired
    RestTemplate restTemplate;

    private final static String PATIENT_SERVICE = "http://patient-service:8082";
    private final static String PATIENT_ID_PATH = "/patients/{id}";

    public Patient getPatientById(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(PATIENT_SERVICE)
                .path(PATIENT_ID_PATH)
                .buildAndExpand(patientId)
                .toUriString();
        return restTemplate.getForObject(url, Patient.class);
    }

}
