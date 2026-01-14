package com.pm.patientservice.dto;


import lombok.Data;

@Data
public class PatientResponseDTO {
//    most of the time is the same as the model class or most of it at least and we use it to
//    send data to the client without converting like Dateofbirth to json string and we can do it
//    here with the response dto , that why they all are string type
    private String id ;
    private String name ;
    private String email ;
    private String address ;
    private String birthDate ;

}
