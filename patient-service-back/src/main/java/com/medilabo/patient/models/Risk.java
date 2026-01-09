package com.medilabo.patient.models;

public enum Risk {
    NONE("Aucun risque"),
    BORDERLINE("Risque Limité"),
    EARLY_ONSET("Apparition précoce"),
    IN_DANGER("Danger");

    private final String label;

    Risk(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
