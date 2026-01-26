package com.medilabo.patient_service_front.controllers;

import com.medilabo.patient_service_front.client.DoctorNoteClient;
import com.medilabo.patient_service_front.client.PatientClient;
import com.medilabo.patient_service_front.client.RiskClient;
import com.medilabo.patient_service_front.models.DoctorNote;
import com.medilabo.patient_service_front.models.Patient;
import com.medilabo.patient_service_front.models.Risk;
import com.medilabo.patient_service_front.service.Service;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
public class WebController {

    @Autowired
    Service service;

    @Autowired
    PatientClient patientClient;
    @Autowired
    DoctorNoteClient noteClient;
    @Autowired
    RiskClient riskClient;

    private static final String TEMPLATE_PATIENT_LIST = "patientList";
    private static final String TEMPLATE_PATIENT_CREATE = "patientCreate";
    private static final String TEMPLATE_PATIENT_UPDATE = "patientUpdate";
    private static final String TEMPLATE_NOTE_CREATE = "patientNote";

    private static final String ATTRIBUTE_PATIENT_LIST = "patientList";
    private static final String ATTRIBUTE_PATIENT = "patient";
    private static final String ATTRIBUTE_RESULT = "result";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_SUCCESS_MESSAGE = "successMessage";
    private static final String ATTRIBUTE_DOCTOR_NOTE = "doctorNote";
    private static final String ATTRIBUTE_EXISTING_DOCTOR_NOTE = "existingNotes";

    @GetMapping("")
    @ResponseBody
    public String home() {
        return "Hi from Web Fronted Service!";
    }

    @GetMapping("/patient/list")
    public String patientList(Model model) {
        try {
            final List<Patient> patientList = patientClient.getAll();
            log.info("Fetching list of {} patients", patientList.size());
            final List<DoctorNote> doctorNoteList = noteClient.getAllNotes();
            log.info("Fetching list of {} notes", doctorNoteList.size());
            service.patientAndNoteJoiner(patientList, doctorNoteList);
            model.addAttribute(ATTRIBUTE_PATIENT_LIST, patientList);
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

    @GetMapping("/patient/{patientId}/addNote")
    public String addNote(@PathVariable Integer patientId, Model model) {
        try {
            final DoctorNote doctorNote = new DoctorNote();
            doctorNote.setPatientId(patientId);
            log.info("Creating new note with patient id: {}", patientId);
            model.addAttribute(ATTRIBUTE_DOCTOR_NOTE, doctorNote);
            model.addAttribute(ATTRIBUTE_EXISTING_DOCTOR_NOTE, getNotesForPatient(patientId));
            return TEMPLATE_NOTE_CREATE;
        } catch (Exception e) {
            log.error("Exception when fetching patient {}", e.getMessage());
            return TEMPLATE_NOTE_CREATE;
        }
    }

    @PostMapping("/patient/{patientId}/addNote")
    public String saveNote(@PathVariable Integer patientId, @Valid DoctorNote doctorNote, BindingResult result, Model model) {
        if (result.hasErrors() || !Objects.equals(patientId, doctorNote.getPatientId())) {
            final String errorMsg = result.hasErrors() ? "input form not valid !" : "input form corrupted";
            log.error(errorMsg);
            model.addAttribute(ATTRIBUTE_DOCTOR_NOTE, doctorNote);
            model.addAttribute(ATTRIBUTE_RESULT, result);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMsg);
            model.addAttribute(ATTRIBUTE_EXISTING_DOCTOR_NOTE, getNotesForPatient(patientId));
            return TEMPLATE_NOTE_CREATE;
        }
        try {
            final DoctorNote savedNote = noteClient.saveNote(doctorNote);
            log.info("note {} saved", savedNote.getId());
            doctorNote.setNote("");
            model.addAttribute(ATTRIBUTE_EXISTING_DOCTOR_NOTE, getNotesForPatient(patientId));
            model.addAttribute(ATTRIBUTE_DOCTOR_NOTE, doctorNote);
            model.addAttribute(ATTRIBUTE_SUCCESS_MESSAGE, "Note Created !");
            return TEMPLATE_NOTE_CREATE;
        } catch (Exception e) {
            final String note = doctorNote.getNote();
            final String cropped = note.length() > 100 ? note.substring(0, 100) + "..." : note;
            log.error("error when posting the note [{}]", cropped);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Technical error: Note was not saved correctly.");
            model.addAttribute(ATTRIBUTE_DOCTOR_NOTE, doctorNote);
            model.addAttribute(ATTRIBUTE_EXISTING_DOCTOR_NOTE, getNotesForPatient(patientId));
            return TEMPLATE_NOTE_CREATE;
        }
    }


    // old url : patient/update/{id}
    @GetMapping("/patient/{patientId}/update")
    public String showUpdateForm(@PathVariable Integer patientId, Model model) {
        try {
            final Patient patient = patientClient.getById(patientId);
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

    @PostMapping("/patient/{patientId}/update")
    public String updatePatient(
            @PathVariable Integer patientId,
            @Valid Patient patient,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors() || !Objects.equals(patientId, patient.getId())) {
            final String errorMsg = result.hasErrors() ? "input form not valid !" : "input form corrupted";
            log.error(errorMsg);
            // model.addAttribute(ATTRIBUTE_PATIENT, patient);
            model.addAttribute(ATTRIBUTE_RESULT, result);
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMsg);
            return TEMPLATE_PATIENT_UPDATE;
        }
        try {
            patientClient.update(patient);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_SUCCESS_MESSAGE,
                    String.format("Patient %s %s is updated !", patient.getFirstName(), patient.getLastName()));
            log.info("patient {} updated", patient.getId());
            return "redirect:/patient/list";
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

    @GetMapping("/patient/add")
    public String showAddPatient(Model model) {
        model.addAttribute(ATTRIBUTE_PATIENT, new Patient());
        log.info("Creating a new patient");
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
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "input form not valid !");
            log.error("input form not valid !");
            return TEMPLATE_PATIENT_CREATE;
        }
        try {
            final Patient savedPatient = patientClient.save(patient);
            log.info("Patient created successfully: {}", savedPatient.getId());
            redirectAttributes.addFlashAttribute(ATTRIBUTE_SUCCESS_MESSAGE, String.format(
                    "Patient %s %s created !", patient.getFirstName(), patient.getLastName()));
            return "redirect:/patient/list";
        } catch (HttpClientErrorException e) {
            log.error("Client error when creating patient: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, "Unable to create patient.");
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

    @GetMapping("/patient/{patientId}/delete")
    public String deletePatient(@PathVariable Integer patientId, RedirectAttributes redirectAttributes) {
        try {
            patientClient.delete(patientId);
            try {
                noteClient.deletePatientNotes(patientId);
            } catch (HttpClientErrorException.NotFound e) {
                log.error("no note to delete for patient {}", patientId);
            }
            log.info("Patient {} deleted", patientId);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_SUCCESS_MESSAGE, "Patient deleted");
            return "redirect:/patient/list";
        } catch (HttpClientErrorException e) {
            log.error("Client error when deleting patient {} {} - {}", patientId, e.getStatusCode(), e.getResponseBodyAsString());
            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE, "Client error when deleting");
            return "redirect:/patient/list";
        } catch (HttpServerErrorException e) {
            log.error("Server error when deleting patient {} {} - {}", patientId, e.getStatusCode(), e.getResponseBodyAsString());
            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE, "Server error when deleting patient");
            return "redirect:/patient/list";
        } catch (Exception e) {
            log.error("Unexpected error when deleting patient {} {}", patientId, e.getMessage(), e);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE, "Unexpected error when deleting patient");
            return "redirect:/patient/list";
        }
    }

    @ResponseBody
    @GetMapping("/patient/{patientId}/risk")
    public String showPatientRisk(@PathVariable Integer patientId) {
        try {
            final Risk risk = riskClient.getRiskForPatient(patientId);
            log.info("For the patient {} the risk assessed is {}", patientId, risk.getLabel());
            return "<div>" + risk.getLabel() + "</div>";
        } catch (Exception e) {
            log.error("Couldn't assess the patient {}", patientId);
            return "<div>Couldn't access the Assessment service, try again later</div>";
        }

    }

    private List<DoctorNote> getNotesForPatient(Integer patientId) {
        try {
            final List<DoctorNote> notesForPatient = List.copyOf(noteClient.getAllNotesByPatientId(patientId));
            log.info("For patient {}, fetched {} notes", patientId, notesForPatient.size());
            return notesForPatient;
        } catch (Exception e) {
            log.error("Failed to retrieve list for patient {}", patientId);
            return Collections.emptyList();
        }
    }

}
