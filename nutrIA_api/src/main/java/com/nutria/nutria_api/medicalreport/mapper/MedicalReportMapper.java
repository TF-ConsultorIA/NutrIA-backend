package com.nutria.nutria_api.medicalreport.mapper;

import com.nutria.nutria_api.medicalreport.dto.MedicalReportResponse;
import com.nutria.nutria_api.medicalreport.model.MedicalReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalReportMapper {
    @Mapping(target = "date", source = "date")
    @Mapping(target = "documentUrl", source = "documentUrl")
    @Mapping(target = "MedicalReportId", source = "id")
    MedicalReportResponse toUploadMedicalReportResponse(MedicalReport medicalReport);
}
