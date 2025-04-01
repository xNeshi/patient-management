package com.xneshi.patientservice.mapper;

import com.xneshi.patientservice.dto.PatientRequestDTO;
import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.model.Patient;

import java.time.LocalDate;

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

  public static Patient toPatient(PatientRequestDTO patientRequestDTO) {
    Patient patient = new Patient();
    patient.setName(patientRequestDTO.name());
    patient.setEmail(patientRequestDTO.email());
    patient.setAddress(patientRequestDTO.address());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.dateOfBirth()));
    patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.registeredDate()));
    return patient;
  }
}
