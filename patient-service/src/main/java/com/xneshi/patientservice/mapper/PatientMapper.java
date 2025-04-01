package com.xneshi.patientservice.mapper;

import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.model.Patient;

public class PatientMapper {
  public static PatientResponseDTO toResponseDTO(Patient patient) {
    return new PatientResponseDTO(
        patient.getId().toString(),
        patient.getName(),
        patient.getEmail(),
        patient.getAddress(),
        patient.getDateOfBirth().toString()
    );
  }
}
