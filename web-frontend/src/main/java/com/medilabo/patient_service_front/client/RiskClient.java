package com.medilabo.patient_service_front.client;

import com.medilabo.patient_service_front.models.Risk;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RiskClient {

    @Autowired
    RestTemplate restTemplate;

    private final static String RISK_SERVICE = "http://risk-assessment:8084";
    private final static String RISK_PATIENT_ID_PATH = "/risk/{patientId}";

    public Risk getRiskForPatient(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(RISK_SERVICE)
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
