package com.medilabo.risk_assessment.client;

import com.medilabo.risk_assessment.model.DoctorNote;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class DoctorNoteClient extends AbstractClient {

    public List<DoctorNote> getAllNotesByPatientId(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path("/notes")
                .queryParam("patientId",patientId)
                .toUriString();
        ResponseEntity<List<DoctorNote>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
