package com.medilabo.patient_service_front.service;

import com.medilabo.patient_service_front.client.DoctorNoteClient;
import com.medilabo.patient_service_front.client.PatientClient;
import com.medilabo.patient_service_front.models.DoctorNote;
import com.medilabo.patient_service_front.models.Gender;
import com.medilabo.patient_service_front.models.Patient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@org.springframework.stereotype.Service
public class Service implements CommandLineRunner {

    @Autowired
    private PatientClient patientClient;

    @Autowired
    private DoctorNoteClient noteClient;

    @Override
    public void run(String... args) {
        int maxRetries = 10;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                populateDataBases();
                log.info("success in populating the DBs with mock data");
                return;
            } catch (Exception e) {
                attempt++;
                log.error("Attempt {}/{} to populate the DBs. {} Retrying in 5 seconds...", attempt, maxRetries, e.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.error("Interrupted during wait");
                    return;
                }
            }
        }
        log.error("Failed to populate DBs after {} attempts. The dependent services might be down", maxRetries);
    }

    private void populateDataBases() {
        Patient none = new Patient(
                "Test", "TestNone", LocalDate.of(1966, 12, 31),
                Gender.FEMALE, "1 Brookside St", "100-222-3333"
        );
        log.info("creating new patient {}", none.getLastName());
        none = patientClient.save(none);
        log.info("none is back with an id {}", none.getId());
        noteClient.saveNote(new DoctorNote(none.getId(), "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"));

        Patient borderline = new Patient("Test", "TestBorderline", LocalDate.of(1945, 6, 24),
                Gender.MALE, "2 High St", "200-333-4444"
        );
        borderline = patientClient.save(borderline);
        noteClient.saveNote(new DoctorNote(borderline.getId(), "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"));
        noteClient.saveNote(new DoctorNote(borderline.getId(), "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"));

        Patient inDanger = new Patient("Test", "TestInDanger", LocalDate.of(2004, 6, 18),
                Gender.MALE, "3 Club Road", "300-444-5555"
        );
        inDanger = patientClient.save(inDanger);
        noteClient.saveNote(new DoctorNote(inDanger.getId(), "Le patient déclare qu'il fume depuis peu"));
        noteClient.saveNote(new DoctorNote(inDanger.getId(), "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"));

        Patient earlyOnset = new Patient("Test", "TestEarlyOnset", LocalDate.of(2002, 6, 28),
                Gender.FEMALE, "4 Valley Dr", "400-555-6666"
        );
        earlyOnset = patientClient.save(earlyOnset);
        noteClient.saveNote(new DoctorNote(earlyOnset.getId(), "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"));
        noteClient.saveNote(new DoctorNote(earlyOnset.getId(), "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"));
        noteClient.saveNote(new DoctorNote(earlyOnset.getId(), "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"));
        noteClient.saveNote(new DoctorNote(earlyOnset.getId(), "Taille, Poids, Cholestérol, Vertige et Réaction"));
    }




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
