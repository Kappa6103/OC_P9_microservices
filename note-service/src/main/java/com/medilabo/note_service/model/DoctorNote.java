package com.medilabo.note_service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString //TODO can remove ?
@Document(collection = "patient_note")
public class DoctorNote {

    private int patientId;
    private String note;
}
