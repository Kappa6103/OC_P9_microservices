package com.medilabo.note_service.repository;

import com.medilabo.note_service.model.DoctorNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<DoctorNote, String> {
    List<DoctorNote> findByPatientId(Integer id);
    void deleteAllByPatientId(Integer id);
}
