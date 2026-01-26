package com.medilabo.patient.service;

import com.medilabo.patient.models.Patient;
import com.medilabo.patient.repositories.PatientRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    @PostConstruct
    private void cleanDataBase() {
        List<Patient> all = repository.findAll();
        log.info("there was {} patients, removing them", all.size());
        repository.deleteAll();
    }
}
