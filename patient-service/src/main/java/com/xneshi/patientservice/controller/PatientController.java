package com.xneshi.patientservice.controller;

import com.xneshi.patientservice.dto.PatientRequestDTO;
import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.dto.validators.CreatePatientValidationGroup;
import com.xneshi.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    return ResponseEntity.ok().body(patientService.getPatients());
  }

  @PostMapping
  public ResponseEntity<PatientResponseDTO> createPatients(
      @Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO
  ) {
    return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PatientResponseDTO> updatePatient(
      @PathVariable UUID id,
      @Validated({Default.class}) @RequestBody PatientResponseDTO patientResponseDTO
      ) {
    return ResponseEntity.ok().body(patientService.updatePatient(id, patientResponseDTO));
  }


}
