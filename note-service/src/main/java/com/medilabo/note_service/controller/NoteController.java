package com.medilabo.note_service.controller;

import com.medilabo.note_service.model.DoctorNote;
import com.medilabo.note_service.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {
    @Autowired
    private NoteRepository repo;

    @PostMapping("/addNote")
    public String saveNote(@RequestBody DoctorNote note) {
        final var savedNote = repo.save(note);
        return "Added note with id : " + savedNote.getPatientId();
    }

    @GetMapping("/findAllNotes")
    public List<DoctorNote> getNotes() {
        return repo.findAll();
    }

    @GetMapping("/findAllNotes/{id}")
    public List<DoctorNote> getNotesById(@PathVariable int id) {
        return repo.findBypatientId(id);
    }

    @GetMapping("/findBook/{id}")
    public Optional<DoctorNote> getNote(@PathVariable int id) {
        return repo.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNote(@PathVariable int id) {
        repo.deleteById(id);
        return "Note deleted with id: " + id;
    }

}
