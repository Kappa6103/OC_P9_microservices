package com.medilabo.risk_assessment.service;


import java.util.List;

/**
 * The TriggerCounter class is responsible for analyzing a given note and counting the
 * occurrences of predefined trigger words. The trigger words represent specific terms
 * that may indicate the patient's condition based on the note content.
 *
 * Instances of this class are immutable after initialization and perform case-insensitive
 * searches for each trigger word within the provided note.
 *
 * Usage context:
 * - The class is used in healthcare-related scenarios to process patient notes and
 *   track the presence of medically relevant terms. For example, it is used within the
 *   RiskAssessmentService to calculate a trigger words count for patient doctor notes,
 *   which contributes to risk assessment analyzes.
 */
final class TriggerCounter {

    private final String note;
    private int score = 0;

    private final List<String> listOfTriggers = List.of(
            "hémoglobine a1c",
            "microalbumine",
            "taille",
            "fumeur", "fumeuse",
            "poids",
            "anormal",
            "cholestérol", "cholesterol",
            "vertiges", "vertige",
            "rechute", "rechutes",
            "réaction", "réactions",
            "anticorps"
    );

    public TriggerCounter(String note) {
        if (note == null || note.isBlank()) {
            throw new IllegalArgumentException("Note was null or blank");
        }
        this.note = note.toLowerCase();
    }

    public int getCount() {
        for (String trigger : listOfTriggers) {
            if (note.contains(trigger)) {
                score++;
            }
        }
        return score;
    }

}
