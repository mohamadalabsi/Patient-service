package com.pm.patientservice.controller;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1")
@RestController
@Tag(name="Patient", description = "Patient management APIs")
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
    @Operation(summary = "Get Patients Version 1.0")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
//        return new ResponseEntity<>(patientService.getAllPatients() , HttpStatus.OK) ;
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @PostMapping("/patients")
    @Operation(summary = "Create new Patient Version 1.0")

    // this  annotation is used to reform the
    // validation on our PatientRequestDTO to make sure all the properties match the validation annotations
    // rules we added there in the PatientRequestDTO object (valid annotation)
//    but validated annotation works as same as valid annotation but is used when we want to
//    specify a validation group we have to change it to work with put and post methods
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO
                                                                    patientRequestDTO) {
        return new ResponseEntity<>(patientService.createPatient(patientRequestDTO), HttpStatus.CREATED);
    }
//    delete and put

    @PutMapping("/patients/{id}")
    @Operation(summary = "Update Patient Version 1.0")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
                                                            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
//        we are using the same dto so the validation is triggered again here for all fields, we do
//        not want registration date , here we used default group only to skip that field
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDTO));

    }

    @DeleteMapping("/patients/{id}")
    @Operation(summary = "Delete Patient Version 1.0")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

//    now all these new annotations are for swagger documentation to generate documentation for
//    our endpoints to describes what our endpoints (API) do
//    this allows developers and if third party want to use our api they can see the docs
//     so open api and swagger is  a tool and standard for designing and interacting with api

//    next step is dockerizing this service and creating GRPC and understand how microservices communicate

}