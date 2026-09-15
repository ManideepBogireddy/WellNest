package com.healthmate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.Report;

public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findByStatus(String status);
}
