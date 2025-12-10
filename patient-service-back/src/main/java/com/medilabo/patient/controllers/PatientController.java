package com.medilabo.patient.controllers;

import com.medilabo.patient.models.Patient;
import com.medilabo.patient.repositories.PatientRepository;
import com.medilabo.patient.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
    public String showUpdateForm(@PathVariable int id, Model model) {
        Optional<Patient> optionalPatient = repo.findById(id);
        if (optionalPatient.isEmpty()) {
            throw new IllegalArgumentException("Patient's id doesn't exist: " + id);
        }
        model.addAttribute("patient", optionalPatient.get());
        return "patientUpdate";
    }

    @PostMapping("/patient/update")
    public String updatePatient(
            @Valid Patient patient,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("patient", patient);
            model.addAttribute("result", result);
            return "patientUpdate";
        }
        repo.save(patient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient updated !");
        return "redirect:/patient/list";
    }

    @GetMapping("patient/add")
    public String showAddPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientCreate";
    }

    @PostMapping("/patient/add")
    public String createPatient(
            @Valid Patient patient,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("patient", patient);
            model.addAttribute("result", result);
            return "patientCreate";
        }
        repo.save(patient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient created !");
        return "redirect:/patient/list";
    }

    //TODO confirmer avant delete
    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable int id, Model model) {
        repo.deleteById(id);
        return "redirect:/patient/list";
    }


}
