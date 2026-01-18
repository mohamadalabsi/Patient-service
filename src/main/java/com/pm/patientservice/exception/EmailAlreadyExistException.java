package com.pm.patientservice.exception;

public class EmailAlreadyExistException extends RuntimeException {
//    if we do not use custom exception the error that would be thrown would be outside runtime
//    exception and could be anything
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
