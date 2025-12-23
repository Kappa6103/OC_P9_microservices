package com.medilabo.patient.controllers;

import com.medilabo.patient.models.Patient;
import com.medilabo.patient.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class PatientController {

    @Autowired
    PatientRepository repo;

    @GetMapping("patient/list")
    @ResponseBody
    public ResponseEntity<List<Patient>> patientList() {
        try {
            List<Patient> patientList = repo.findAll();
            log.info("Fetching all patients from database. {} patients in db", patientList.size());
            return ResponseEntity.ok(patientList);
        } catch (DataAccessException e) {
            log.error("Error with the database when fetching all patients {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when fetching all patients {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("patient/update/{id}")
    public ResponseEntity<Patient> showUpdateForm(@PathVariable int id) {
        try {
            Optional<Patient> optionalPatient = repo.findById(id);
            if (optionalPatient.isEmpty()) {
                log.error("Patient didn't exist for id: {}", id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok(optionalPatient.get());
        } catch (DataAccessException e) {
            log.error("Error with the database when fetching patient {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when fetching patient {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/patient/update")
    public ResponseEntity<Patient> updatePatient(
            @Valid @RequestBody Patient patient,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.error("Patient object not valid");
            return ResponseEntity.badRequest().build();
        }
        try {
            final var savedPatient = repo.save(patient);
            log.info("Patient {} updated", savedPatient.getId());
            return ResponseEntity.ok(savedPatient);
        } catch (DataAccessException e) {
            log.error("Error with the database when updating patient {}", patient.getId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when updating patient {}", patient.getId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/patient/add")
    public ResponseEntity<Patient> createPatient(
            @RequestBody @Valid Patient patient,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.error("Patient object not valid");
            return ResponseEntity.badRequest().build();
        }
        try {
            Patient saved = repo.save(patient);
            return ResponseEntity.ok().body(saved);
        } catch (DataAccessException e) {
            log.error("Error with the database when creating patient {}", patient.getFirstName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when creating patient {}", patient.getFirstName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("patient/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        try {
            repo.deleteById(id);
            log.info("Patient {} deleted from database", id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            log.error("Error with the database when deleting patient {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when deleting patient {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
