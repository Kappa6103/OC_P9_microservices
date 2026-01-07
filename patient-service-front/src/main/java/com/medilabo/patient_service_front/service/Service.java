package com.medilabo.patient_service_front.service;

import com.medilabo.patient_service_front.models.DoctorNote;
import com.medilabo.patient_service_front.models.Patient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@org.springframework.stereotype.Service
public class Service {


    public void patientAndNoteJoiner(List<Patient> patientList, List<DoctorNote> doctorNoteList) {
        if (patientList == null || doctorNoteList == null) {
            throw new IllegalArgumentException("patientList or doctorNoteList null");
        }
        Map<Integer, List<DoctorNote>> notesByPatientId = new HashMap<>();
        for (DoctorNote note : doctorNoteList) {
            int patientId = note.getPatientId();
            if (notesByPatientId.containsKey(patientId)) {
                notesByPatientId.get(patientId).add(note);
            } else {
                List<DoctorNote> noteList = new ArrayList<>();
                noteList.add(note);
                notesByPatientId.put(patientId, noteList);
            }
        }
        for (Patient patient : patientList) {
            patient.setNoteList(notesByPatientId.getOrDefault(patient.getId(), new ArrayList<>()));
        }
    }
}
