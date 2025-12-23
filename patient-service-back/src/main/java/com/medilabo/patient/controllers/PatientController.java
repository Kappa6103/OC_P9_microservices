package com.medilabo.patient.controllers;

import com.medilabo.patient.models.Patient;
import com.medilabo.patient.repositories.PatientRepository;
import com.medilabo.patient.services.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    PatientService service;
    @Autowired
    PatientRepository repo;

    @GetMapping("patient/list")
    @ResponseBody
    public List<Patient> patientList() {
        List<Patient> patientList = repo.findAll();
        return patientList;
    }

    @GetMapping("patient/update/{id}")
    public Patient showUpdateForm(@PathVariable int id) {
        Optional<Patient> optionalPatient = repo.findById(id);
        if (optionalPatient.isEmpty()) {
            throw new IllegalArgumentException("Patient's id doesn't exist: " + id);
        }
        return optionalPatient.get();
    }

//    @PostMapping("/update")
//    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient) {
//        try {
//            Patient updatedPatient = patientService.update(patient);
//            if (updatedPatient != null) {
//                return ResponseEntity.ok(updatedPatient);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    @PutMapping("/patient/update")
    public ResponseEntity<Patient> updatePatient(
            @Valid @RequestBody Patient patient,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.info("post mapping hit with patient : {} ", patient.toString());

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        repo.save(patient);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/patient/add")
    public ResponseEntity<Patient> createPatient(
            @RequestBody @Valid Patient patient,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Patient saved = repo.save(patient);
        return ResponseEntity.ok().body(saved);
    }

    //TODO confirmer avant delete
    @DeleteMapping("patient/delete/{id}")
    public void deletePatient(@PathVariable int id) {
        repo.deleteById(id);

    }


}
