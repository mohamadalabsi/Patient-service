package com.pm.patientservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PatientRequestDTO {
//     one of the reasons to use dto instead of our entity  is to validate the input data from
//     the client could be different from the validation that we save in the DB like this
//    in general we do not use the entity class directly to interact with the client

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name ;
    @NotBlank(message = "Email is mandatory")
    @Size( message = "Email should be valid" )
    private String email ;

    @NotBlank(message = "Address is mandatory")
    private String address ;

    @NotBlank(message = "Birth date is mandatory")
    private String birthDate ;

    @NotBlank(message = "Registration date is mandatory")
    private String registrationDate ;

//    request dto and response dto are different in case we need to change  the data that gets
//    input vs the data that gets output


}
