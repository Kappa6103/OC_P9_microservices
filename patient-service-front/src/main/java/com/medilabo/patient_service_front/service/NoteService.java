package com.medilabo.patient_service_front.service;

import com.medilabo.patient_service_front.models.DoctorNote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class NoteService {

    @Autowired
    RestTemplate restTemplate;

    public void sendRequest() {


    }
}
