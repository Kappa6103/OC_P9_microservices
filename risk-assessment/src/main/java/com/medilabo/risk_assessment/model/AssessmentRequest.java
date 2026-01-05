package com.medilabo.risk_assessment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssessmentRequest(
        @NotNull int patientId,
        @NotBlank String noteId)
{}
