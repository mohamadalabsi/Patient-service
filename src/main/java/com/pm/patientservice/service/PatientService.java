package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepo patientRepo;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
//    @Autowired this is not needed with constructor injection
    public PatientService(PatientRepo patientRepo , BillingServiceGrpcClient billingServiceGrpcClient) {
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.patientRepo = patientRepo;
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if  (patientRepo.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("A patient with this email "+"already exist" +
                    patientRequestDTO.getEmail());
        }
//       return patientRepo.save(patientRequestDTO); it expects a Patient entity not a DTO
          Patient newPatient= patientRepo.save(PatientMapper.toEntity(patientRequestDTO)) ;

//        after adding this service as grpc client we can call the billing service from here
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString() ,
                newPatient.getName().toString() , newPatient.getEmail().toString());

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

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient existingPatient = patientRepo.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
//     !    costume exception if the patient not found ,  important and return to it later

//         now lets say the patient exist and they want to update the email we need to check if the
//         new email already exist for another patient but the problem if we do not change the email and
//         just update other fields the check will fail because the email exist for this patient

//         we should change this to method where it ignores the email address of the current
//         patient id and checks for other patients only
        if  (patientRepo.existsByEmailAndIdNot(patientRequestDTO.getEmail() , id )) {
            throw new EmailAlreadyExistException("A patient with this email "+"already exist" +
                    patientRequestDTO.getEmail());
        }
//      we can not use the mapper because it is update and we can not update the registration date
            existingPatient.setName(patientRequestDTO.getName());
            existingPatient.setEmail(patientRequestDTO.getEmail());
            existingPatient.setAddress(patientRequestDTO.getAddress());
            existingPatient.setBirthDate(LocalDate.parse( patientRequestDTO.getBirthDate()));

            Patient updatedPatient = patientRepo.save(existingPatient);
            return PatientMapper.toDto(updatedPatient);


    }

    public void deletePatient(UUID id) {
        patientRepo.deleteById(id);
    }
}
