package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
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


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
//       return patientRepo.save(patientRequestDTO); it expects a Patient entity not a DTO
          Patient newPatient= patientRepo.save(PatientMapper.toEntity(patientRequestDTO)) ;

          return PatientMapper.toDto(newPatient);

//           in get and post method we use PatientResponseDTO to return the data
//        no matter what the request from the client they will get PatientResponseDTO

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
// here is a lambda expression so we need to assign it to a variable first and when we are done
// we return it in other words it should not be like this return something = somethingelse

//        return patientsdto ;


        return patients.stream().map(PatientMapper::toDto).toList();
    }
}
