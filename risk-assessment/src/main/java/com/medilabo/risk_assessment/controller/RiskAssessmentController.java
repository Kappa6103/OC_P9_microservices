package com.medilabo.risk_assessment.controller;

import com.medilabo.risk_assessment.service.RiskAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class RiskAssessmentController {

    @Autowired
    RiskAssessmentService service;

    @PostMapping("/risk")
    public ResponseEntity<Void> receiveAssessmentRequest(@RequestBody Integer patientId) {
        try {
            log.info("processing Assessment request for patientId {}",
                    patientId);
            service.assessPatient(patientId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("failed to assess patient {}", patientId, e);
            return ResponseEntity.internalServerError().build();
        }

    }




}
