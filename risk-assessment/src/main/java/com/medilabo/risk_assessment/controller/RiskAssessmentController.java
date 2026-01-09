package com.medilabo.risk_assessment.controller;

import com.medilabo.risk_assessment.model.Risk;
import com.medilabo.risk_assessment.service.RiskAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class RiskAssessmentController {

    @Autowired
    RiskAssessmentService service;

    @GetMapping("/risk")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/risk/{patientId}")
    public ResponseEntity<Risk> receiveAssessmentRequest(@PathVariable Integer patientId) {
        try {
            log.info("processing Assessment request for patientId {}",
                    patientId);
            final Risk risk = service.assessPatient(patientId);
            return ResponseEntity.ok(risk);
        } catch (Exception e) {
            log.error("failed to assess patient {}", patientId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
