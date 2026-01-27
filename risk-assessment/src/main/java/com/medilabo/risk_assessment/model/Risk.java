package com.medilabo.risk_assessment.model;

public enum Risk {
    NONE("Aucun risque"),
    BORDERLINE("Risque Limité"),
    EARLY_ONSET("Apparition précoce"),
    IN_DANGER("Danger"),
    ERROR("Erreur dans le calcul du risque");

    private final String label;

    Risk(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
