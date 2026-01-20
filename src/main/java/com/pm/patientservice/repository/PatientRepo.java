package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PatientRepo extends JpaRepository<Patient, UUID> {

//    important
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email , UUID id);
//    it will check the Db for any patient that have an email address equal to the given email
//    but ignore the patient with the given id
}
