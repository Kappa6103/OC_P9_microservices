package com.medilabo.patient_service_front.client;


import com.medilabo.patient_service_front.models.DoctorNote;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class DoctorNoteClient extends AbstractClient {

    private final static String DOCTOR_NOTE_PATH = "/notes";

    public List<DoctorNote> getAllNotesByPatientId(Integer patientId) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(DOCTOR_NOTE_PATH)
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

    public List<DoctorNote> getAllNotes() {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(DOCTOR_NOTE_PATH)
                .toUriString();

        ResponseEntity<List<DoctorNote>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public void saveNote(DoctorNote doctorNote) {
        final String url = UriComponentsBuilder
                .fromUriString(gatewayUrl)
                .path(DOCTOR_NOTE_PATH)
                .toUriString();

        HttpEntity<DoctorNote> request = new HttpEntity<>(doctorNote);
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                DoctorNote.class
        );
    }
}
