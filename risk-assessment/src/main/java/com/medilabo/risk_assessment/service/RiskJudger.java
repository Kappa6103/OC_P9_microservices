package com.medilabo.risk_assessment.service;

import com.medilabo.risk_assessment.model.DoctorNote;
import com.medilabo.risk_assessment.model.Gender;
import com.medilabo.risk_assessment.model.Patient;
import com.medilabo.risk_assessment.model.Risk;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * The RiskJudger class provides methods to assess the health risk level of a patient
 * based on medical notes, trigger words, and the patient's demographic information.
 * The risk assessment leverages predefined risk categories and thresholds.
 */
@Slf4j
public class RiskJudger {

    private Risk risk;
    private final Patient patient;
    private final List<DoctorNote> allExistingNotes;
    private int triggerWordsTotal = 0;
    private int age = 0;


    public RiskJudger(Patient patient, List<DoctorNote> allExistingNotes) {
        this.patient = patient;
        this.allExistingNotes = allExistingNotes;
    }

    public Risk assessPatient() {
        addAllTriggerWord();
        calculatePatientAge();
        assessRisk();
        return risk;
    }

    private void assessRisk() {
        if (isRiskNone()) {
            risk = Risk.NONE;
        } else if (isRiskBorderline()) {
            risk = Risk.BORDERLINE;
        } else if (isRiskEarlyOnset()) {
            risk = Risk.EARLY_ONSET;
        } else if (isRiskInDanger()) {
            risk = Risk.IN_DANGER;
        } else {
            log.error("Error in risk assessment algo for patient {} id {}", patient.getFirstName(), patient.getId());
            risk = Risk.ERROR;
        }
    }

    private boolean isRiskNone() {
        return triggerWordsTotal == 0;
    }

    private boolean isRiskBorderline() {
        return triggerWordsTotal >= 2 && triggerWordsTotal <= 5 && age > 30;
    }

    private boolean isRiskEarlyOnset() {
        if (age > 30 && triggerWordsTotal >= 8) {
            return true;
        } else if (patient.getGender() == Gender.MALE && age < 30 && triggerWordsTotal >= 5) {
            return true;
        } else if (patient.getGender() == Gender.FEMALE && age < 30 && triggerWordsTotal >= 7) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRiskInDanger() {
        if (patient.getGender() == Gender.MALE && age < 30 && triggerWordsTotal >= 3) {
            return true;
        } else if (patient.getGender() == Gender.FEMALE && age < 30 && triggerWordsTotal >= 4) {
            return true;
        } else if (age > 30 && triggerWordsTotal >= 8) {
            return true;
        } else {
            return false;
        }
    }

    private void addAllTriggerWord() {
        for (DoctorNote doctorNote : allExistingNotes) {
            triggerWordsTotal += doctorNote.getTriggerWordsCount();
        }
    }

    private void calculatePatientAge() {
        Period period = Period.between(patient.getBirthday(), LocalDate.now());
        age = period.getYears();
    }

}
