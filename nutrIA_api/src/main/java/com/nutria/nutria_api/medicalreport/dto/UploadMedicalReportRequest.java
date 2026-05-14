package com.nutria.nutria_api.medicalreport.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public record UploadMedicalReportRequest(
        @NotNull MultipartFile file,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date,
        Long userId
) {}
