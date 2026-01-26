package com.medilabo.patient_service_front.models;

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
public class Patient {

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

    private List<DoctorNote> noteList;

    private Risk risk;

    public Patient(String firstName, String lastName, LocalDate birthday, Gender gender, String address, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFormattedBirthday() {
        return String.format("%s-%s-%s", birthday.getDayOfMonth(), birthday.getMonth(), birthday.getYear());
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", risk=" + risk +
                '}';
    }
}
