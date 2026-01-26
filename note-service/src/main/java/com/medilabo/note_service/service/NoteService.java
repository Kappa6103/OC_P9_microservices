package com.medilabo.note_service.service;

import com.medilabo.note_service.model.DoctorNote;
import com.medilabo.note_service.repository.NoteRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoteService {

    @Autowired
    private NoteRepository repo;

    @PostConstruct
    private void cleanDataBase() {
        List<DoctorNote> all = repo.findAll();
        log.info("there was {} notes, removing all of them", all.size());
        repo.deleteAll();
    }

}
