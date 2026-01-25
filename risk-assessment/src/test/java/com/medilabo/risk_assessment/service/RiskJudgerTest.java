package com.medilabo.risk_assessment.service;


import com.medilabo.risk_assessment.model.DoctorNote;
import com.medilabo.risk_assessment.model.Gender;
import com.medilabo.risk_assessment.model.Patient;
import com.medilabo.risk_assessment.model.Risk;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RiskJudgerTest {


    @Test
    void assessPatient_None() {
        Patient patientNone = new Patient(
                "Test",
                "TestNone",
                LocalDate.of(1966, 12, 31),
                Gender.FEMALE
        );
        //0
        DoctorNote doctorNote = new DoctorNote("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé");
        doctorNote.setTriggerWordsCount(0);
        List<DoctorNote> notesForPatientNone = List.of(doctorNote);

        RiskJudger riskJudger = new RiskJudger(patientNone, notesForPatientNone);

        Risk result = riskJudger.assessPatient();

        assertEquals(Risk.NONE, result);
    }

    @Test
    void assessPatient_Borderline() {
        Patient patientBorderline = new Patient(
                "Test",
                "TestBorderline",
                LocalDate.of(1945, 06, 24),
                Gender.MALE

        );
        // 1 2
        DoctorNote doctorNote = new DoctorNote("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement");
        doctorNote.setTriggerWordsCount(1);
        DoctorNote doctorNote1 = new DoctorNote("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale");
        doctorNote1.setTriggerWordsCount(2);
        List<DoctorNote> notesForPatientBorderline = List.of(doctorNote, doctorNote1);

        RiskJudger riskJudger = new RiskJudger(patientBorderline, notesForPatientBorderline);

        Risk result = riskJudger.assessPatient();

        assertEquals(Risk.BORDERLINE, result);
    }

    @Test
    void assessPatient_InDanger() {
        Patient patientInDanger = new Patient(
                "Test",
                "TestInDanger",
                LocalDate.of(2004, 06, 18),
                Gender.MALE
        );// 0 3
        DoctorNote doctorNote = new DoctorNote("Le patient déclare qu'il fume depuis peu");
        doctorNote.setTriggerWordsCount(0);
        DoctorNote doctorNote1 = new DoctorNote("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        doctorNote1.setTriggerWordsCount(3);
        List<DoctorNote> notesForPatientInDanger = List.of(doctorNote, doctorNote1);

        RiskJudger riskJudger = new RiskJudger(patientInDanger, notesForPatientInDanger);

        Risk result = riskJudger.assessPatient();

        assertEquals(Risk.IN_DANGER, result);
    }

    @Test
    void assessPatient_EarlyOnset() {
        Patient patientEarlyOnset = new Patient(
                "Test",
                "TestEarlyOnSet",
                LocalDate.of(2002, 06, 28),
                Gender.FEMALE
        );// 2 0 1 4
        DoctorNote doctorNote = new DoctorNote("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments");
        doctorNote.setTriggerWordsCount(2);
        DoctorNote doctorNote1 = new DoctorNote("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");
        doctorNote1.setTriggerWordsCount(0);
        DoctorNote doctorNote2 = new DoctorNote("Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé");
        doctorNote2.setTriggerWordsCount(1);
        DoctorNote doctorNote3 = new DoctorNote("Taille, Poids, Cholestérol, Vertige et Réaction");
        doctorNote3.setTriggerWordsCount(4);
        List<DoctorNote> notesForPatientEarlyOnset = List.of(doctorNote, doctorNote1, doctorNote2, doctorNote3);

        RiskJudger riskJudger = new RiskJudger(patientEarlyOnset, notesForPatientEarlyOnset);

        Risk result = riskJudger.assessPatient();

        assertEquals(Risk.EARLY_ONSET, result);

    }








}