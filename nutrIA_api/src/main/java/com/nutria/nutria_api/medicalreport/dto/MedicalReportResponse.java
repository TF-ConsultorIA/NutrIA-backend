package com.nutria.nutria_api.medicalreport.dto;

import java.time.LocalDate;

public record MedicalReportResponse(
        LocalDate date,
        String documentUrl,
        Long MedicalReportId
) {}
