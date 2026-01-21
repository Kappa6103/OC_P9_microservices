package com.medilabo.patient.services;

import com.medilabo.patient.models.Gender;
import com.medilabo.patient.models.Patient;
import com.medilabo.patient.repositories.PatientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository repository;

    @PostConstruct
    private void populateDataBase() {
        List<Patient> mockUsers = List.of(
                new Patient("Edouard", "Leclerc", LocalDate.now(), Gender.MALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Stephanie", "Dupont", LocalDate.of(2013,1,23), Gender.MALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Raoul", "Mayer", LocalDate.of(1991, 4, 1), Gender.FEMALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Lucas", "Dassault", LocalDate.of(2012,12,12), Gender.OTHER, "22 blb D'anvers", "+33454545454"),
                new Patient("Jessica", "Meyer", LocalDate.of(1992, 9, 29), Gender.MALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Rebecca", "Schmidt", LocalDate.of(1993, 3, 5), Gender.MALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Stephen", "Hawking", LocalDate.of(1995, 8, 2), Gender.MALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Steven", "Rolls", LocalDate.of(2015, 9, 20), Gender.FEMALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Lucile", "Le Marchal", LocalDate.of(2012, 9, 23), Gender.FEMALE, "22 blb D'anvers", "+33454545454"),
                new Patient("Antoine", "De Monaco", LocalDate.of(1999, 2, 28), Gender.OTHER, "22 blb D'anvers", "+33454545454")
        );
        repository.saveAll(mockUsers);
    }

}
