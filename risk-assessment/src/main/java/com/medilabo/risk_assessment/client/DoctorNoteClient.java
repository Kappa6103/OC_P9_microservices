package com.medilabo.risk_assessment.client;

import com.medilabo.risk_assessment.model.DoctorNote;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorNoteClient extends AbstractClient {

    public List<DoctorNote> getAllNotesByPatientId(Integer patientId) {
        ResponseEntity<List<DoctorNote>> response = restTemplate.exchange(
                gatewayUrl + "/notes/patient/" + patientId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
