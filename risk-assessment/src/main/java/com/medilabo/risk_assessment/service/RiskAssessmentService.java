package com.medilabo.risk_assessment.service;

import com.medilabo.risk_assessment.model.AssessmentRequest;
import com.medilabo.risk_assessment.model.DoctorNote;
import com.medilabo.risk_assessment.model.Patient;
import com.medilabo.risk_assessment.model.Risk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class RiskAssessmentService {

//    @Bean
//    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
//        return builder
//                .connectTimeout(Duration.ofSeconds(3))
//                .readTimeout(Duration.ofSeconds(3))
//                .build();
//    }

    RestTemplate restTemplate = new RestTemplate();

    public void processRequest(AssessmentRequest assessmentRequest) {
        Patient patient = getPatient(assessmentRequest.patientId());
        // DoctorNote newNote = getDoctorNote(assessmentRequest.noteId());
        List<DoctorNote> allExistingNotes = getAllNotesForPatient(assessmentRequest.patientId());

        calculateTriggerWordsCount(allExistingNotes);

        RiskJudger riskJudger = new RiskJudger(patient, allExistingNotes);

        patient.setRisk(riskJudger.assessPatient());

    }

    private void calculateTriggerWordsCount(List<DoctorNote> allExistingNotes) {
        for(DoctorNote doctorNote : allExistingNotes) {
            NoteJudger noteJudger = getNoteJudger(doctorNote.getNote());
            doctorNote.setTriggerWords(noteJudger.getScore());
        }
    }

    private NoteJudger getNoteJudger(String note) {
        return new NoteJudger(note);
    }

    private List<DoctorNote> getAllNotesForPatient(int patientId) {

    }

    private Patient getPatient(int patientId) {

    }

    private DoctorNote getDoctorNote(String noteId) {

    }

}
