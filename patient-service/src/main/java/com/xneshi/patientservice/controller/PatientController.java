package com.xneshi.patientservice.controller;

import com.xneshi.patientservice.dto.PatientRequestDTO;
import com.xneshi.patientservice.dto.PatientResponseDTO;
import com.xneshi.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
      @Valid @RequestBody PatientRequestDTO patientRequestDTO
  ) {
    return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
  }
}
