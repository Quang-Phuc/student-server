package com.phuclq.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.phuclq.student.dto.HistoryFileResult;
import com.phuclq.student.service.HistoryFileService;

import lombok.Data;

@RestController
public class FileHistoryController {
	
	@Autowired
	private HistoryFileService fileService;
	
	@PostMapping("/api/get-file-user")
	public ResponseEntity<?> getFileWithUser(@RequestBody HistoryFileRequest request, Pageable pageable){
		Page<HistoryFileResult> page = fileService.getFile(request, pageable);
		return new ResponseEntity<Page<HistoryFileResult>>(page, HttpStatus.OK);
	}
	
	@PostMapping("/api/get-file-user-download")
	public ResponseEntity<?> getFileWithUserDownloaded(@RequestBody HistoryFileRequest request, Pageable pageable){
		Page<HistoryFileResult> page = fileService.getFileDownload(request, pageable);
		return new ResponseEntity<Page<HistoryFileResult>>(page, HttpStatus.OK);
	}
	
	@GetMapping("/api/get-file-user-favorite")
	public ResponseEntity<?> getFileWithUserFavorite(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo , Pageable pageable){
		Page<HistoryFileResult> page = fileService.getFileFavoriteByDate(dateFrom, dateTo, pageable);
		return new ResponseEntity<Page<HistoryFileResult>>(page, HttpStatus.OK);
	}
	
	@Data
	public static class HistoryFileRequest{
		private String dateFrom;
		private String dateTo;
		private String title;
		private int approve;
	}
}