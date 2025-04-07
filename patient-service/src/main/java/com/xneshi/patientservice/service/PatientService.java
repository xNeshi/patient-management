package com.xneshi.patientservice.service;

import com.xneshi.patientservice.dto.PatientRequestDTO;
import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.exception.EmailAlreadyExistsException;
import com.xneshi.patientservice.exception.PatientNotFoundException;
import com.xneshi.patientservice.grpc.BillingServiceGrpcClient;
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
  private final BillingServiceGrpcClient billingServiceGrpcClient;

  public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
    this.billingServiceGrpcClient = billingServiceGrpcClient;
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
    billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());
    return PatientMapper.toResponseDTO(patient);
  }

  public PatientResponseDTO updatePatient(UUID id, PatientResponseDTO patientResponseDTO) {
    Patient patient = patientRepository.findById(id).orElseThrow(() ->
        new PatientNotFoundException("Patient not found with ID: " + id));

    String email = patientResponseDTO.email();
    if (patientRepository.existsByEmailAndIdNot(email, id)) {
      throw new EmailAlreadyExistsException("A patient with email " + email + " already exists");
    }

    patient.setName(patientResponseDTO.name());
    patient.setEmail(email);
    patient.setAddress(patientResponseDTO.address());
    patient.setDateOfBirth(LocalDate.parse(patientResponseDTO.dateOfBirth()));

    Patient updatedPatient = patientRepository.save(patient);

    return PatientMapper.toResponseDTO(updatedPatient);
  }

  public void deletePatient(UUID id) {
    Patient patient = patientRepository.findById(id).orElseThrow(() ->
        new PatientNotFoundException("Patient not found with ID: " + id));

    patientRepository.delete(patient);
  }
}
