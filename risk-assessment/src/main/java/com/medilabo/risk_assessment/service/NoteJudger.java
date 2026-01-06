package com.medilabo.risk_assessment.service;

import java.util.List;

class NoteJudger {

    private final String note;
    private String[] tokenisedNote;
    private int score = 0;

    private final List<String> listOfTriggers = List.of(
            "Hémoglobine A1C", "hémoglobine A1C",
            "Microalbumine", "microalbumine",
            "Taille", "taille",
            "Fumeur", "Fumeuse", "fumeur", "fumeuse",
            "Anormal", "anormal",
            "Cholestérol", "cholestérol", "Cholesterol", "cholesterol",
            "Vertiges", "vertiges", "Vertige", "vertige",
            "Rechute", "rechute", "Rechutes", "rechutes",
            "Réaction", "réaction", "Réactions", "réactions",
            "Anticorps", "anticorps"
    );

    public NoteJudger(String note) {
        if (note == null || note.isBlank()) {
            throw new IllegalArgumentException("Note was null or blank");
        }
        this.note = note;
    }

    public int getScore() {
        stringTokeniser();
        for (String string : tokenisedNote) {
            if (listOfTriggers.contains(string)) {
                score++;
            }
        }
        return score;
    }

    private void stringTokeniser() {
        tokenisedNote = note.split("[\\s,.;:!?]+");
    }

}
