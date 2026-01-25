package com.medilabo.risk_assessment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorNote {

    private String id;

    @NotNull
    private int patientId;

    @NotBlank
    private String note;

    private Integer triggerWordsCount;

    public DoctorNote(String note) {
        this.note = note;
    }
}
