package com.pm.patientservice.controller;


import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping("/")
    public String version() {
        return "Patient Service - Version 1.0";
    }

    @GetMapping("/patients")
    public ResponseEntity < List<PatientResponseDTO> > getAllPatients(){
//        return new ResponseEntity<>(patientService.getAllPatients() , HttpStatus.OK) ;
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }
    @PostMapping("/patients")
    public ResponseEntity < Patient >createPatient(@RequestBody Patient
                                            patient) {
        return  new ResponseEntity<>(patientService.createPatient(patient) , HttpStatus.CREATED)  ;
    }
}
