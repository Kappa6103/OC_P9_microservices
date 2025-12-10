package com.medilabo.patient_service_front.controllers;

import com.medilabo.patient_service_front.models.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("patient/list")
    public String patientList(Model model) {
        ResponseEntity<List<Patient>> response = restTemplate.exchange(
                "http://localhost:8080/patient/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        List<Patient> patientList = response.getBody();

        model.addAttribute("patientList", patientList);
        return "patientList";
    }

    @GetMapping("patient/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Patient patient = restTemplate.getForObject(
                "http://localhost:8080/patient/update/" + id,
                Patient.class);

        model.addAttribute("patient", patient);
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
        HttpEntity<Patient> request = new HttpEntity<>(patient);
        ResponseEntity<Patient> response = restTemplate.exchange(
                "http://localhost:8080/patient/update",
                HttpMethod.PUT,
                request,
                Patient.class
        );

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Patient> request = new HttpEntity<>(patient, headers);
//        ResponseEntity<Patient> response = restTemplate.exchange(
//                "http://localhost:8080/patient/update",
//                HttpMethod.POST,
//                request,
//                Patient.class
//        );


//        ResponseEntity<Patient> response = restTemplate.postForEntity(
//                "http://localhost:8080/patient/update",
//                patient,
//                Patient.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("successMessage", "Patient updated !");
            return "redirect:/patient/list";
        } else {
            //TODO ADD AN ERROR MESSAGE TO THE VIEW
            model.addAttribute("patient", patient);
            model.addAttribute("result", result);
            return "patientUpdate";
        }

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Patient> request = new HttpEntity<>(patient, headers);
        System.out.println(request.hasBody());
        ResponseEntity<Patient> response = restTemplate.exchange(
                "http://localhost:8080/patient/add",
                HttpMethod.POST,
                request,
                Patient.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("successMessage", "Patient created !");
            return "redirect:/patient/list";
        } else {
            //TODO error message to display
            model.addAttribute("patient", patient);
            model.addAttribute("result", result);
            return "patientCreate";
        }

    }

    //TODO confirmer avant delete
    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable int id, Model model) {
        restTemplate.delete(
                "http://localhost:8080/patient/delete/" + id
        );
        return "redirect:/patient/list";
    }


}
