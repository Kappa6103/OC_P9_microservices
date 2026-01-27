package com.medilabo.risk_assessment.client;

import com.medilabo.risk_assessment.model.DoctorNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class DoctorNoteClient {

    @Autowired
    RestTemplate restTemplate;

    private final static String NOTE_SERVICE = "http://note-service:8083";

    public List<DoctorNote> getAllNotesByPatientId(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(NOTE_SERVICE)
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
