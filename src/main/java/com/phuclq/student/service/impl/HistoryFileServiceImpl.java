package com.phuclq.student.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.phuclq.student.controller.FileHistoryController.HistoryFileRequest;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.HistoryFileResult;
import com.phuclq.student.repository.FileRepository;
import com.phuclq.student.service.HistoryFileService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.DateTimeUtils;
import com.phuclq.student.utils.StringUtils;

@Service
public class HistoryFileServiceImpl implements HistoryFileService{
	
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Page<HistoryFileResult> getFile(HistoryFileRequest request, Pageable pageable) {
		User user = userService.getUserLogin();
		Timestamp dateFrom = null;
		Timestamp dateTo = null;
		if (StringUtils.isStringNotNullAndHasValue(request.getDateFrom())) {
			dateFrom = DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(request.getDateFrom(), DateTimeUtils.yyyy_MM_dd));
			dateTo = !StringUtils.isStringNotNullAndHasValue(request.getDateTo()) ?
					DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr("9999-12-31", DateTimeUtils.yyyy_MM_dd))
					: DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(request.getDateTo(), DateTimeUtils.yyyy_MM_dd));
		}
		if (!StringUtils.isStringNotNullAndHasValue(request.getDateFrom()) && StringUtils.isStringNotNullAndHasValue(request.getDateTo())) {
			dateFrom = DateTimeUtils.toTimestampFromStr("1900-01-01", DateTimeUtils.yyyy_MM_dd);
			dateTo = DateTimeUtils.toTimestampFromStr(request.getDateTo(), DateTimeUtils.yyyy_MM_dd);
		}
		
		Page<HistoryFileResult> page = null;
		
		if (StringUtils.isStringNotNullAndHasValue(request.getDateFrom()) 
				|| StringUtils.isStringNotNullAndHasValue(request.getDateTo())) {
			page = request.getApprove() == 1 ? fileRepo.getFileByUserApprovedFile(dateFrom, dateTo, request.getTitle(), user.getId(), pageable)
					: request.getApprove() == 0 ? fileRepo.getFileByUserApprovingFile(dateFrom, dateTo, request.getTitle(), user.getId(), pageable)
					: fileRepo.getFileByUser(dateFrom, dateTo, request.getTitle(), user.getId(), pageable);
		}else {
			page = request.getApprove() == 1 ? fileRepo.getFileByUserApprovedFile(request.getTitle(), user.getId(), pageable)
					: request.getApprove() == 0 ? fileRepo.getFileByUserApprovingFile(request.getTitle(), user.getId(), pageable)
					: fileRepo.getFileByUser(request.getTitle(), user.getId(), pageable);
		}
		
		return page;
	}

	@Override
	public Page<HistoryFileResult> getFileDownload(HistoryFileRequest request, Pageable pageable) {
		User user = userService.getUserLogin();
		Timestamp dateFrom = null;
		Timestamp dateTo = null;
		if (StringUtils.isStringNotNullAndHasValue(request.getDateFrom())) {
			dateFrom = DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(request.getDateFrom(), DateTimeUtils.yyyy_MM_dd));
			dateTo = !StringUtils.isStringNotNullAndHasValue(request.getDateTo()) ?
					DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr("9999-12-31", DateTimeUtils.yyyy_MM_dd))
					: DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(request.getDateTo(), DateTimeUtils.yyyy_MM_dd));
		}
		if (!StringUtils.isStringNotNullAndHasValue(request.getDateFrom()) && StringUtils.isStringNotNullAndHasValue(request.getDateTo())) {
			dateFrom = DateTimeUtils.toTimestampFromStr("1900-01-01", DateTimeUtils.yyyy_MM_dd);
			dateTo = DateTimeUtils.toTimestampFromStr(request.getDateTo(), DateTimeUtils.yyyy_MM_dd);
		}
		
		Page<HistoryFileResult> page = null;
		
		if (StringUtils.isStringNotNullAndHasValue(request.getDateFrom()) 
				|| StringUtils.isStringNotNullAndHasValue(request.getDateTo())) {
			page = fileRepo.getFileByUserDownloaded(dateFrom, dateTo, request.getTitle(), user.getId(), pageable);
		}else {
			page = fileRepo.getFileByUserDownloaded(request.getTitle(), user.getId(), pageable);
		}
		return page;
	}

	@Override
	public Page<HistoryFileResult> getFileFavorite(String title, Pageable pageable) {
		if (title == null) {
			title = "";
		}
//		Page<HistoryFileResult> page = fileRepo.getFileByUserFavorite(title, pageable);
//		return page;
		return null;
	}

	@Override
	public Page<HistoryFileResult> getFileFavoriteByDate(String dateFromStr, String dateToStr, Pageable pageable) {
		User user = userService.getUserLogin();
		Timestamp dateFrom = null;
		Timestamp dateTo = null;
		if (!dateFromStr.isEmpty()) {
			dateFrom = DateTimeUtils.toTimestampFromStr(dateFromStr, DateTimeUtils.DATE_TIME_MYSQL_FORMAT);
		}
		if (!dateToStr.isEmpty()) {
		dateTo = DateTimeUtils.toTimestampFromStr(dateToStr, DateTimeUtils.DATE_TIME_MYSQL_FORMAT);
		}
		if (dateFrom != null && dateTo != null) {
			return fileRepo.getFileUserFavoriteByDate(dateFrom, dateTo, user.getId(), pageable);
		} else {
			return fileRepo.getFileByUserFavorite(user.getId(), pageable);
		}
	}

}
