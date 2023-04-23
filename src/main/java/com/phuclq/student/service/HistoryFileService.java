package com.phuclq.student.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.phuclq.student.controller.FileHistoryController.HistoryFileRequest;
import com.phuclq.student.dto.HistoryFileResult;

public interface HistoryFileService {
	Page<HistoryFileResult> getFile(HistoryFileRequest request, Pageable pageable);
	
	Page<HistoryFileResult> getFileDownload(HistoryFileRequest request, Pageable pageable);
	
	Page<HistoryFileResult> getFileFavorite(String title, Pageable pageable);
	
	Page<HistoryFileResult> getFileFavoriteByDate(String dateFrom, String dateTo, Pageable pageable);
}
