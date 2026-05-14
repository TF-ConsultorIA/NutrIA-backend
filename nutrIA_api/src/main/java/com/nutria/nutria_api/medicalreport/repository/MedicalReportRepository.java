package com.nutria.nutria_api.medicalreport.repository;

import com.nutria.nutria_api.medicalreport.model.MedicalReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {
    Page<MedicalReport> findByUserId(Long userId, Pageable pageable);
}
