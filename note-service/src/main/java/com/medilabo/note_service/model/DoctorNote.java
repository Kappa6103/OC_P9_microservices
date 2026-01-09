package com.medilabo.note_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "patient_note")
public class DoctorNote {

    @Id
    private String id;

    @NotNull
    private int patientId;

    @NotBlank
    private String note;
}
