package com.xneshi.patientservice.exception;

public class PatientNotFoundException extends RuntimeException {
  public PatientNotFoundException(String message) {
    super(message);
  }
}
