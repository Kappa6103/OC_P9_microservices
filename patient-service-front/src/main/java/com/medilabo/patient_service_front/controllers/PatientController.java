package com.medilabo.patient_service_front.controllers;

import com.medilabo.patient_service_front.models.Patient;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
public class PatientController {

    private static final String TEMPLATE_PATIENT_LIST = "patientList";
    private static final String TEMPLATE_PATIENT_CREATE = "patientCreate";
    private static final String TEMPLATE_PATIENT_UPDATE = "patientUpdate";

    private static final String ATTRIBUTE_PATIENT_LIST = "patientList";
    private static final String ATTRIBUTE_PATIENT = "patient";
    private static final String ATTRIBUTE_RESULT = "result";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_SUCCESS_MESSAGE = "successMessage";

    private static final String URL_GATEWAY = "http://localhost:8081";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("patient/list")
    public String patientList(Model model) {
        try {
            ResponseEntity<List<Patient>> response = restTemplate.exchange(
                    URL_GATEWAY + "/patient/list",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {});
            final List<Patient> patientList = response.getBody();
            model.addAttribute(ATTRIBUTE_PATIENT_LIST, patientList);
            log.info("Fetching list of {} patients", patientList.size());
            return TEMPLATE_PATIENT_LIST;
        } catch (HttpClientErrorException e) {
            // 4xx errors
            log.error("Client error when fetching patients: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Client error. Unable to load patients.");
            model.addAttribute(ATTRIBUTE_PATIENT_LIST, Collections.emptyList());
            return TEMPLATE_PATIENT_LIST;
        } catch (HttpServerErrorException e) {
            //5xx errors
            log.error("Server error when fetching patients: {} {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Server error. Unable to load patients.");
            model.addAttribute(ATTRIBUTE_PATIENT_LIST, Collections.emptyList());
            return TEMPLATE_PATIENT_LIST;
        } catch (Exception e) {
            log.error("Unexpected error when fetching patients: {}", e.getMessage(), e);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "An unexpected error occurred.");
            model.addAttribute(ATTRIBUTE_PATIENT_LIST, Collections.emptyList());
            return TEMPLATE_PATIENT_LIST;
        }
    }

    @GetMapping("patient/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        try {
            Patient patient = restTemplate.getForObject(
                    URL_GATEWAY+ "/patient/update/" + id,
                    Patient.class);
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            log.info("updating patient {}", patient.getId());
            return TEMPLATE_PATIENT_UPDATE;
        } catch (HttpClientErrorException e) {
            //4xx errors
            log.error("Client error when showing update page for patient {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Client error. Unable to load patient");
            model.addAttribute(ATTRIBUTE_PATIENT, null);
            return TEMPLATE_PATIENT_UPDATE;
        } catch (HttpServerErrorException e) {
            log.error("Server error when showing update page for patient {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Server error. Unable to load patient");
            model.addAttribute(ATTRIBUTE_PATIENT, null);
            return TEMPLATE_PATIENT_UPDATE;
        } catch (Exception e) {
            log.error("Unexpected error when showing update page for patient: {}", e.getMessage(), e);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "An unexpected error occurred");
            model.addAttribute(ATTRIBUTE_PATIENT, null);
            return TEMPLATE_PATIENT_UPDATE;
        }
    }

    @PostMapping("/patient/update")
    public String updatePatient(
            @Valid Patient patient,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            model.addAttribute(ATTRIBUTE_RESULT, result);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "input form not valid !");
            return TEMPLATE_PATIENT_UPDATE;
        }
        try {
            HttpEntity<Patient> request = new HttpEntity<>(patient);
            ResponseEntity<Patient> response = restTemplate.exchange(
                    URL_GATEWAY + "/patient/update",
                    HttpMethod.PUT,
                    request,
                    Patient.class
            );
            redirectAttributes.addFlashAttribute(ATTRIBUTE_SUCCESS_MESSAGE,
                    String.format("Patient %s %s is updated !", patient.getFirstName(), patient.getLastName()));
            return "redirect:" + URL_GATEWAY + "patient/list";
        } catch (HttpClientErrorException e) {
            //4xx errors
            log.error("Client error when updating patient {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Client error, unable to update patient");
            return TEMPLATE_PATIENT_UPDATE;
        } catch (HttpServerErrorException e) {
            //5xx errors
            log.error("Server error when updating patient {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Server error, unable to update patient");
            return TEMPLATE_PATIENT_UPDATE;
        } catch (Exception e) {
            log.error("Unexpected error when updating patient: {}", e.getMessage(), e);
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Unexpected error, unable to update patient");
            return TEMPLATE_PATIENT_UPDATE;
        }
    }

    @GetMapping("patient/add")
    public String showAddPatient(Model model) {
        model.addAttribute(ATTRIBUTE_PATIENT, new Patient());
        return TEMPLATE_PATIENT_CREATE;
    }

    @PostMapping("/patient/add")
    public String createPatient(
            @Valid Patient patient,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            model.addAttribute(ATTRIBUTE_RESULT, result);
            return TEMPLATE_PATIENT_CREATE;
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Patient> request = new HttpEntity<>(patient, headers);
            ResponseEntity<Patient> response = restTemplate.exchange(
                    URL_GATEWAY + "/patient/add",
                    HttpMethod.POST,
                    request,
                    Patient.class
            );
            log.info("Patient created successfully: {}", response.getBody().getId());
            redirectAttributes.addFlashAttribute(ATTRIBUTE_SUCCESS_MESSAGE, "Patient created !");
            return "redirect:" + URL_GATEWAY + "patient/list";
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                log.error("Bad request when creating patient: {}", e.getResponseBodyAsString());
                model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Invalid patient date. Please check your input");
            } else if (e.getStatusCode() == HttpStatus.CONFLICT) {
                log.error("Conflict when creating patient {}", e.getResponseBodyAsString());
                model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Patient already exists.");
            } else {
                log.error("Client error when creating patient: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
                model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Unable to create patient.");
            }
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            return TEMPLATE_PATIENT_CREATE;
        } catch (HttpServerErrorException e) {
            log.error("Server error when creating patient: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Server error.");
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            return TEMPLATE_PATIENT_CREATE;
        } catch (Exception e) {
            log.error("Unexpected error when creating patient: {}", e.getMessage(), e);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "An unexpected error occured");
            model.addAttribute(ATTRIBUTE_PATIENT, patient);
            return TEMPLATE_PATIENT_CREATE;
        }
    }

    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable int id, Model model) {
        try {
            restTemplate.delete(
                    URL_GATEWAY + "/patient/delete/" + id
            );
            log.info("Deleting patient {}", id);
            model.addAttribute(ATTRIBUTE_SUCCESS_MESSAGE, "Patient deleted");
            return "redirect:" + URL_GATEWAY + "patient/list";
        } catch (HttpClientErrorException e) {
            log.error("Client error when deleting patient {} {} - {}", id, e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Client error when deleting");
            return "redirect:" + URL_GATEWAY + "patient/list";
        } catch (HttpServerErrorException e) {
            log.error("Server error when deleting patient {} {} - {}", id, e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Server error when deleting patient");
            return "redirect:" + URL_GATEWAY + "patient/list";
        } catch (Exception e) {
            log.error("Unexpected error when deleting patient {} {}", id, e.getMessage(), e);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Unexpected error when deleting patient");
            return "redirect:" + URL_GATEWAY + "patient/list";
        }
    }
}
