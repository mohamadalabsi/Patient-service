package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {

//    we will use this mapper to convert from patient entity to patient response dto instead of
//    doing it in service  we will do it here to keep the code clean

    public static PatientResponseDTO toDto(Patient patient) {
        PatientResponseDTO patientdto = new PatientResponseDTO();
        patientdto.setId(patient.getId().toString());
        patientdto.setName(patient.getName());
        patientdto.setEmail(patient.getEmail());
        patientdto.setAddress(patient.getAddress());
        patientdto.setBirthDate(patient.getBirthDate().toString());
        return patientdto;
    }
}
