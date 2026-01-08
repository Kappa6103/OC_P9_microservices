package com.medilabo.risk_assessment.service;

import com.medilabo.risk_assessment.client.DoctorNoteClient;
import com.medilabo.risk_assessment.client.PatientClient;
import com.medilabo.risk_assessment.model.DoctorNote;
import com.medilabo.risk_assessment.model.Patient;
import com.medilabo.risk_assessment.model.Risk;
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
        Risk risk;
        if (allExistingNotes.isEmpty()) {
            risk = Risk.NONE;
        } else {
            calculateTriggerWordsCount(allExistingNotes);
            RiskJudger riskJudger = getRiskJudger(patient, allExistingNotes);
            risk = riskJudger.assessPatient();
        }
        return risk;
    }

    //TODO boolean flag in the note model to know if the triggerWordsCount is necessary ?
    private void calculateTriggerWordsCount(List<DoctorNote> allExistingNotes) {
        for(DoctorNote doctorNote : allExistingNotes) {
            NoteJudger noteJudger = getNoteJudger(doctorNote.getNote());
            doctorNote.setTriggerWordsCount(noteJudger.getScore());
        }
    }

    private NoteJudger getNoteJudger(String note) {
        return new NoteJudger(note);
    }

    private RiskJudger getRiskJudger(Patient patient, List<DoctorNote> allExistingNotes) {
        return new RiskJudger(patient, allExistingNotes);
    }

}
