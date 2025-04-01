package com.xneshi.patientservice.service;

import com.xneshi.patientservice.dto.PatientRequestDTO;
import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.dto.PatientUpdateDTO;
import com.xneshi.patientservice.exception.EmailAlreadyExistsException;
import com.xneshi.patientservice.exception.PatientNotFoundException;
import com.xneshi.patientservice.mapper.PatientMapper;
import com.xneshi.patientservice.model.Patient;
import com.xneshi.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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

  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    String email = patientRequestDTO.email();

    if (patientRepository.existsByEmail(email)) {
      throw new EmailAlreadyExistsException("A patient with email " + email + " already exists");
    }

    Patient patient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
    return PatientMapper.toResponseDTO(patient);
  }

  public PatientResponseDTO updatePatient(UUID id, PatientUpdateDTO patientUpdateDTO) {
    Patient patient = patientRepository.findById(id).orElseThrow(() ->
        new PatientNotFoundException("Patient not found with ID: " + id));

    String email = patientUpdateDTO.email();
    if (patientRepository.existsByEmail(email)) {
      throw new EmailAlreadyExistsException("A patient with email " + email + " already exists");
    }

    patient.setName(patientUpdateDTO.name());
    patient.setEmail(email);
    patient.setAddress(patientUpdateDTO.address());
    patient.setDateOfBirth(LocalDate.parse(patientUpdateDTO.dateOfBirth()));

    Patient updatedPatient = patientRepository.save(patient);

    return PatientMapper.toResponseDTO(updatedPatient);
  }
}
