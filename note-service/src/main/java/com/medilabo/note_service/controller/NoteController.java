package com.medilabo.note_service.controller;

import com.medilabo.note_service.model.DoctorNote;
import com.medilabo.note_service.repository.NoteRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class NoteController {

    @Autowired
    private NoteRepository repo;

    @PostMapping("/note")
    public ResponseEntity<DoctorNote> saveNote(
            @RequestBody @Valid DoctorNote note,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.error("DoctorNote not valid");
            return ResponseEntity.badRequest().build();
        }
        try {
            DoctorNote savedNote = repo.save(note);
            log.info("Added note with patientId : {}", savedNote.getPatientId());
            log.info("Added note with id: {}", savedNote.getId());
            return ResponseEntity.ok(savedNote);
        } catch (DataAccessException e) {
            log.error("Error with the database when saving note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when saving note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/note")
    public ResponseEntity<DoctorNote> updateNote(
            @RequestBody @Valid DoctorNote note,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.error("DoctorNote not valid");
            return ResponseEntity.badRequest().build();
        }
        try {
            DoctorNote existingNote = repo.findById(note.getId()).get();
            existingNote.setNote(note.getNote());
            DoctorNote savedNote = repo.save(existingNote);
            log.info("Updated note with patientId : {}", savedNote.getPatientId());
            log.info("Updated note with id: {}", savedNote.getId());
            return ResponseEntity.ok(savedNote);
        } catch (DataAccessException e) {
            log.error("Error with the database when updating note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when updating note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/note/list")
    public ResponseEntity<List<DoctorNote>> getNotes() {
        try {
            List<DoctorNote> noteList = repo.findAll();
            log.info("Returning a note list of {} items", noteList.size());
            return ResponseEntity.ok(noteList);
        } catch (DataAccessException e) {
            log.error("Database error when fetching all notes from the database");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when when fetching all notes");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/notes/patient/{patientId}")
    public ResponseEntity<List<DoctorNote>> getNotesByPatientId(@PathVariable int patientId) {
        try {
            List<DoctorNote> noteList = repo.findByPatientId(patientId);
            log.info("returning a list of {} item(s) belonging to patient {}",
                    noteList.size(), patientId);
            return ResponseEntity.ok(noteList);
        } catch (DataAccessException e) {
            log.error("Error with the database when fetching notes for patients {}", patientId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when fetching notes for patients {}", patientId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        try {
            repo.deleteById(id);
            log.info("Note deleted with id: {}", id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            log.error("Error with the database when deleting note {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when deleting note {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/note/patient/{id}")
    public ResponseEntity<Void> deletePatientNote(@PathVariable int id) {
        try {
            repo.deleteAllByPatientId(id);
            log.info("Notes deleted with patientId: {}", id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            log.error("Error with the database when deleting notes {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when deleting notes {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
