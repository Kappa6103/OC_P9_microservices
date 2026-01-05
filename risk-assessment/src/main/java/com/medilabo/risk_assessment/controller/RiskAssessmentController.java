package com.medilabo.risk_assessment.controller;

import com.medilabo.risk_assessment.model.AssessmentRequest;
import com.medilabo.risk_assessment.service.RiskAssessmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RiskAssessmentController {

    @Autowired
    RiskAssessmentService service;

    @PostMapping("/risk")
    public ResponseEntity<Void> receiveAssessmentRequest(
            @RequestBody @Valid AssessmentRequest assessmentRequest, BindingResult result
    ) {
        if (result.hasErrors()) {
            log.error("receiving request, but requestBody not valid");
            return ResponseEntity.badRequest().build();
        }
        log.info("processing Assessment request for patientId {} and noteId {}",
                assessmentRequest.patientId(), assessmentRequest.noteId());
        service.processRequest(assessmentRequest);
    }


}
