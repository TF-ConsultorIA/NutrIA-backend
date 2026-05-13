package com.nutria.nutria_api.medicalreport.controller;

import com.nutria.nutria_api.medicalreport.dto.MedicalReportResponse;
import com.nutria.nutria_api.medicalreport.dto.UploadMedicalReportRequest;
import com.nutria.nutria_api.medicalreport.service.impl.MedicalReportService;
import com.nutria.nutria_api.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/medical-reports")
@RequiredArgsConstructor
@Tag(name = "Medical Reports", description = "Manejo de archivos de reportes medicos")
public class MedicalReportController {

    private final MedicalReportService medicalReportService;

    @Operation(summary = "Subir un archivo de reporte medico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Archivo subido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "No Autenticado")
            ,@ApiResponse(responseCode = "403", description = "Sin Permiso"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping("user/{userId}")
    public ResponseEntity<MedicalReportResponse> uploadMedicalReport(
            @Valid @ModelAttribute UploadMedicalReportRequest medicalReportRequest,
            @PathVariable Long userId ) {
        return ResponseEntity.ok(medicalReportService.uploadMedicalReport(userId, medicalReportRequest));
    }

    @Operation(summary = "Listar archivos subidos por el usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de archivos"),
            @ApiResponse(responseCode = "401", description = "No Autenticado")
            ,@ApiResponse(responseCode = "403", description = "Sin Permiso")
    })
    @GetMapping("user/{userId}")
    public ResponseEntity<PageResponse<MedicalReportResponse>> getMedicalReport(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable,
            @PathVariable Long userId) {
        return ResponseEntity.ok(PageResponse.from(medicalReportService.getMedicalReports(userId, pageable)));
    }
}
