package com.medilabo.risk_assessment.client;

import com.medilabo.risk_assessment.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientClient extends AbstractClient {

    public Patient getPatientById(Integer patientId) {
        return restTemplate.getForObject(
                gatewayUrl + "/patient/" + patientId,
                Patient.class
        );
    }

}
