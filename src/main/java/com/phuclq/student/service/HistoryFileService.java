package com.phuclq.student.service;

import com.phuclq.student.dto.FileHomePageRequest;
import com.phuclq.student.dto.FileResultDto;
import com.phuclq.student.dto.TotalMyFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.phuclq.student.controller.FileHistoryController.HistoryFileRequest;
import com.phuclq.student.dto.HistoryFileResult;

public interface HistoryFileService {
	FileResultDto getFile(FileHomePageRequest request, Pageable pageable);
	TotalMyFileDTO total();

	Page<HistoryFileResult> getFileDownload(HistoryFileRequest request, Pageable pageable);
	
	Page<HistoryFileResult> getFileFavorite(String title, Pageable pageable);
	
	Page<HistoryFileResult> getFileFavoriteByDate(String dateFrom, String dateTo, Pageable pageable);

	Page<HistoryFileResult> deleteFileHistory(FileHomePageRequest request);
}
