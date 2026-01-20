package com.pm.patientservice.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
//    let us handle cross coding consumes such as error handling outside of controllers and
//    services and keep them clean


    @ExceptionHandler(MethodArgumentNotValidException.class)
//    this going to handle any of the validation errors that might occur when jpa validates the
//    request DTOs in the requests such as email not valid or required fields missing
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField() , error.getDefaultMessage());
        });
//  we are taking all the errors from ex var and we are getting all the field errors from that var

        return ResponseEntity.badRequest().body(errors); // it returns the error to the client

    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {

        log.warn("Email address already exists: {}", ex.getMessage());
//     to display our own costume exception in the terminal so this is for us and down here for
//     client

        Map<String,String> error = new HashMap<>();
        error.put("Message", "Email already exists ");
        return ResponseEntity.badRequest().body(error);
//        here like we changed the 500 error (server error) to 400 error (bad request)

    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFoundException(PatientNotFoundException ex) {

//        same thing as above
        log.warn("Patient not found: {}", ex.getMessage());

        Map<String,String> error = new HashMap<>();
        error.put("Message", "Patient not found ");
        return ResponseEntity.badRequest().body(error);
    }

}
