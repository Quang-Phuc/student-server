package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.dto.HistoryFileResult;
import com.phuclq.student.dto.ReportDTO;
import com.phuclq.student.service.ActivityService;
import com.phuclq.student.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private RestEntityResponse restEntityRes;
    @Autowired
    private ReportService reportService;

    @PostMapping("/report")
    public ResponseEntity<?> getFileWithUser(@RequestBody ReportDTO reportDTO){
        reportService.createReport(reportDTO);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }


}
