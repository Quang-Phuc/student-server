package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Report;
import com.phuclq.student.dto.ReportDTO;
import com.phuclq.student.repository.*;
import com.phuclq.student.service.ReportService;
import com.phuclq.student.service.UserService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserService userService;

    @Autowired
    private ReportRepository reportRepository;



    @Override
    public void createReport(ReportDTO reportDTO) {
        Report report = new Report();
        report.setRequestId(reportDTO.getRequestId());

        Integer idLogin = userService.getUserLogin().getId();
        report.setCreatedBy(idLogin.toString());
        report.setType(reportDTO.getType());
        report.setContent(reportDTO.getContent());
        report.setCreatedDate(LocalDateTime.now());
        reportRepository.save(report);


    }
}

