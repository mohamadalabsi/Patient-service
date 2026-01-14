package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepo patientRepo;
    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    public Patient createPatient(Patient patient) {
       return patientRepo.save(patient);
    }

    public List <PatientResponseDTO> getAllPatients() {

        List<Patient>  patients = patientRepo.findAll();


//        return patients.stream().map(patient -> {
//            PatientResponseDTO dto = new PatientResponseDTO();
//            dto.setId(patient.getId().toString());
//            dto.setName(patient.getName());
//            dto.setEmail(patient.getEmail());
//            dto.setAddress(patient.getAddress());
//            dto.setBirthDate(patient.getBirthDate().toString());
//            return dto;
//        }).toList();


//        List <PatientResponseDTO> patientsdto=
//                patients.stream().map(patient -> PatientMapper.toDto(patient)).toList();

//        List <PatientResponseDTO> patientsdto=
//                patients.stream().map(PatientMapper::toDto).toList(); // second way of writing the above line


// u can not return it like above because the method return type is List<PatientResponseDTO> and
// here is a lambda expression so we need to assign it to a variable first

//        return patientsdto ;


        return patients.stream().map(PatientMapper::toDto).toList();
    }
}
