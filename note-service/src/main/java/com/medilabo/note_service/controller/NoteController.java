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
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository repo;

    @GetMapping
    public ResponseEntity<List<DoctorNote>> getAllNotes() {
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

    @GetMapping(params = "patientId")
    public ResponseEntity<List<DoctorNote>> getNotesByPatientId(
            @RequestParam Integer patientId
    ) {
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

    @PostMapping
    public ResponseEntity<DoctorNote> createNote(
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
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
        } catch (DataAccessException e) {
            log.error("Error with the database when saving note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when saving note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<DoctorNote> updateNote(
            @PathVariable String noteId,
            @RequestBody @Valid DoctorNote note,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.error("DoctorNote not valid");
            return ResponseEntity.badRequest().build();
        }
        if(!Objects.equals(noteId, note.getId())) {
            log.error("Corrupted request, note id and pathVariable not equal");
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<DoctorNote> optionalDoctorNote = repo.findById(noteId);
            if (optionalDoctorNote.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            DoctorNote existingNote = optionalDoctorNote.get();
            existingNote.setNote(note.getNote());
            DoctorNote savedNote = repo.save(existingNote);
            log.info("Updated note with patientId : {} and noteId {}",
                    savedNote.getPatientId(), savedNote.getId());
            return ResponseEntity.ok(savedNote);
        } catch (DataAccessException e) {
            log.error("Error with the database when updating note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when updating note {}", note.getPatientId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable String noteId) {
        try {
            if (!repo.existsById(noteId)) {
                log.info("Note(s) with id {} not found", noteId);
                return ResponseEntity.notFound().build();
            }
            repo.deleteById(noteId);
            log.info("Note deleted with noteId: {}", noteId);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            log.error("Error with the database when deleting note {}", noteId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error when deleting note {}", noteId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<Void> deletePatientNotes(@PathVariable Integer patientId) {
        try {
            if (!repo.existsByPatientId(patientId)) {
                log.info("Note(s) with patientId {} not found", patientId);
                return ResponseEntity.notFound().build();
            }
            repo.deleteAllByPatientId(patientId);
            log.info("Notes deleted with patientId: {}", patientId);
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
