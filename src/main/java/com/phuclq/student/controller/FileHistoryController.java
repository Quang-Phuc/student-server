package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.dto.FileHomePageRequest;
import com.phuclq.student.dto.FileResultDto;
import com.phuclq.student.dto.TotalMyFileDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phuclq.student.dto.HistoryFileResult;
import com.phuclq.student.service.HistoryFileService;

import lombok.Data;

@RestController
@RequestMapping("/api/history-file")
public class FileHistoryController {

  @Autowired
  private HistoryFileService fileService;
  @Autowired
  private RestEntityResponse restEntityRes;


  @PostMapping("/get-file-user")
  public ResponseEntity<?> getFileWithUser(@RequestBody FileHomePageRequest request,
      Pageable pageable) {
    FileResultDto page = fileService.getFile(request, pageable);
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(page).getResponse();
  }

  @GetMapping("/total")
  public ResponseEntity<?> total() {
    TotalMyFileDTO page = fileService.total();
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(page).getResponse();
  }

  @PostMapping("/api/get-file-user-download")
  public ResponseEntity<?> getFileWithUserDownloaded(@RequestBody HistoryFileRequest request,
      Pageable pageable) {
    Page<HistoryFileResult> page = fileService.getFileDownload(request, pageable);
    return new ResponseEntity<Page<HistoryFileResult>>(page, HttpStatus.OK);
  }

  @GetMapping("/api/get-file-user-favorite")
  public ResponseEntity<?> getFileWithUserFavorite(@Param("dateFrom") String dateFrom,
      @Param("dateTo") String dateTo, Pageable pageable) {
    Page<HistoryFileResult> page = fileService.getFileFavoriteByDate(dateFrom, dateTo, pageable);
    return new ResponseEntity<Page<HistoryFileResult>>(page, HttpStatus.OK);
  }
  @DeleteMapping("/delete")
  public ResponseEntity<?> deleteFileHistory(@RequestBody FileHomePageRequest request) {
    Page<HistoryFileResult> page = fileService.deleteFileHistory(request);
    return new ResponseEntity<Page<HistoryFileResult>>(page, HttpStatus.OK);
  }

  @Data
  public static class HistoryFileRequest {

    private String dateFrom;
    private String dateTo;
    private String title;
    private int approve;
  }
}
