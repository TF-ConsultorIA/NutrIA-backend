package com.nutria.nutria_api.medicalreport.service.impl;

import com.nutria.nutria_api.medicalreport.dto.MedicalReportResponse;
import com.nutria.nutria_api.medicalreport.dto.UploadMedicalReportRequest;
import com.nutria.nutria_api.medicalreport.exception.NotSupportedFileTypeException;
import com.nutria.nutria_api.medicalreport.mapper.MedicalReportMapper;
import com.nutria.nutria_api.medicalreport.model.MedicalReport;
import com.nutria.nutria_api.medicalreport.repository.MedicalReportRepository;
import com.nutria.nutria_api.medicalreport.service.IMedicalReportService;
import com.nutria.nutria_api.shared.storage.AzureBlobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MedicalReportService implements IMedicalReportService {
    private final MedicalReportRepository medicalReportRepository;
    private final AzureBlobService azureBlobService;
    private final MedicalReportMapper medicalReportMapper;

    @Override
    @Transactional
    // US-08 : Gestión de exámenes médicos
    public MedicalReportResponse uploadMedicalReport(Long userId, UploadMedicalReportRequest medicalReportResponse){
        MultipartFile file = medicalReportResponse.file();

        // RN-04 : Formatos permitidos para exámenes
        if (file == null || file.isEmpty() || !file.getContentType().equals("application/pdf")) {
            throw new NotSupportedFileTypeException();
        }

        String documentUrl = azureBlobService.uploadFile(file, userId);

        MedicalReport medicalReport = MedicalReport.builder()
                .userId(userId)
                .documentUrl(documentUrl)
                .date(LocalDate.parse(medicalReportResponse.date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

        medicalReportRepository.save(medicalReport);

        return medicalReportMapper.toUploadMedicalReportResponse(medicalReport);
    }

    public Page<MedicalReportResponse> getMedicalReports(Long userId, Pageable pageable) {
        return medicalReportRepository.findByUserId(userId, pageable)
                .map(medicalReportMapper::toUploadMedicalReportResponse);
    }
}
