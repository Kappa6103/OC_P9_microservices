package com.medilabo.patient.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private Gender gender;

    private String address;
    private String phoneNumber;

    @Transient
    private List<DoctorNote> noteList;

    @Transient
    private Risk risk;

    public Patient(String firstName, String lastName, LocalDate birthday, Gender gender, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient(String firstName, String lastName, LocalDate birthday, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getFormattedBirthday() {
        return String.format("%s-%s-%s", birthday.getDayOfMonth(), birthday.getMonth(), birthday.getYear());
    }

}
