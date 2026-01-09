package com.medilabo.patient_service_front.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorNote {

    private String id;

    @NotNull
    private int patientId;

    @NotBlank
    private String note;
}
