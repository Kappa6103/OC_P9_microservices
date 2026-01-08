package com.medilabo.patient_service_front.client;

import com.medilabo.patient_service_front.models.Risk;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RiskClient extends AbstractClient{

    private final static String RISK_PATIENT_ID_PATH = "/risk/{patientId}";

    public Risk getRiskForPatient(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(RISK_PATIENT_ID_PATH)
                .buildAndExpand(patientId)
                .toUriString();
        ResponseEntity<Risk> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Risk.class
        );
        return response.getBody();
    }
}
