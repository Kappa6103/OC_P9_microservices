package com.medilabo.risk_assessment.service;

import com.medilabo.risk_assessment.client.DoctorNoteClient;
import com.medilabo.risk_assessment.client.PatientClient;
import com.medilabo.risk_assessment.model.DoctorNote;
import com.medilabo.risk_assessment.model.Patient;
import com.medilabo.risk_assessment.model.Risk;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RiskAssessmentService {

    @Autowired
    DoctorNoteClient noteClient;

    @Autowired
    PatientClient patientClient;


    public Risk assessPatient(Integer patientId) {
        Patient patient = patientClient.getPatientById(patientId);
        List<DoctorNote> allExistingNotes = noteClient.getAllNotesByPatientId(patientId);

        if (allExistingNotes.isEmpty()) {
            return Risk.NONE;
        }

        calculateTriggerWordsCount(allExistingNotes);
        RiskJudger riskJudger = getRiskJudger(patient, allExistingNotes);
        return riskJudger.assessPatient();
    }

    private void calculateTriggerWordsCount(List<DoctorNote> allExistingNotes) {
        for(DoctorNote doctorNote : allExistingNotes) {
            TriggerCounter triggerCounter = getTriggerCounter(doctorNote.getNote());
            doctorNote.setTriggerWordsCount(triggerCounter.getCount());
        }
    }

    private TriggerCounter getTriggerCounter(String note) {
        return new TriggerCounter(note);
    }

    private RiskJudger getRiskJudger(Patient patient, List<DoctorNote> allExistingNotes) {
        return new RiskJudger(patient, allExistingNotes);
    }

}
