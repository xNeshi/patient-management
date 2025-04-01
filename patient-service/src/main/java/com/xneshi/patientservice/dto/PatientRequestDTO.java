package com.xneshi.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatientRequestDTO(
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Address is required")
    String address,

    @NotBlank(message = "Date of Birth is required")
    String dateOfBirth,

    @NotNull(message = "Registered Date is required")
    String registeredDate

) {
}
