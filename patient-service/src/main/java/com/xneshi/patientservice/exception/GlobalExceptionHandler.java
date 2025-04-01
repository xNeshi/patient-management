package com.xneshi.patientservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(
      MethodArgumentNotValidException ex
  ) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(
      EmailAlreadyExistsException ex
  ) {
    log.warn(ex.getMessage());
    Map<String, String> errors = new HashMap<>();
    errors.put("email", ex.getMessage());
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(PatientNotFoundException.class)
  public ResponseEntity<Map<String, String>> handlePatientNotFoundException(
      PatientNotFoundException ex
  ) {
    log.warn(ex.getMessage());
    Map<String, String> errors = new HashMap<>();
    errors.put("patient", ex.getMessage());
    return ResponseEntity.badRequest().body(errors);
  }
}
