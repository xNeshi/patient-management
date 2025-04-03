package com.xneshi.patientservice.controller;

import com.xneshi.patientservice.dto.PatientRequestDTO;
import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.dto.validators.CreatePatientValidationGroup;
import com.xneshi.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }


  @GetMapping
  @Operation(summary = "Get Patients")
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    return ResponseEntity.ok().body(patientService.getPatients());
  }

  @PostMapping
  @Operation(summary = "Post Patient")
  public ResponseEntity<PatientResponseDTO> createPatients(
      @Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO
  ) {
    return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Put Patient")
  public ResponseEntity<PatientResponseDTO> updatePatient(
      @PathVariable UUID id,
      @Validated({Default.class}) @RequestBody PatientResponseDTO patientResponseDTO
      ) {
    return ResponseEntity.ok().body(patientService.updatePatient(id, patientResponseDTO));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Patient")
  public ResponseEntity<Void> deletePatient(
      @PathVariable UUID id
  ) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }

}
