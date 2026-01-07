package com.medilabo.risk_assessment.client;

import com.medilabo.risk_assessment.model.Patient;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

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
}
