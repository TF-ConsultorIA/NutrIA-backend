package com.nutria.nutria_api.medicalreport.service;

import com.nutria.nutria_api.medicalreport.dto.MedicalReportResponse;
import com.nutria.nutria_api.medicalreport.dto.UploadMedicalReportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMedicalReportService {
    MedicalReportResponse uploadMedicalReport(Long userId, UploadMedicalReportRequest medicalReportResponse);
    Page<MedicalReportResponse> getMedicalReports(Long userId, Pageable pageable);
}
