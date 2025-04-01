package com.xneshi.patientservice.service;

import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.mapper.PatientMapper;
import com.xneshi.patientservice.model.Patient;
import com.xneshi.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
  private final PatientRepository patientRepository;

  public PatientService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public List<PatientResponseDTO> getPatients () {
    List<Patient> patients = patientRepository.findAll();
    return patients.stream()
        .map(PatientMapper::toResponseDTO)
        .collect(Collectors.toList());
  }
}
