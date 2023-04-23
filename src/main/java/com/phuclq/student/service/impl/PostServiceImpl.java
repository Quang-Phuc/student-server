package com.phuclq.student.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.phuclq.student.controller.PostController.RequestPost;
import com.phuclq.student.domain.File;
import com.phuclq.student.domain.User;
import com.phuclq.student.repository.FileRepository;
import com.phuclq.student.service.PostService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.DateTimeUtils;
import com.phuclq.student.utils.StringUtils;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileRepository fileRepo;

	@Override
	public void approvalPost(Integer idPost) {
		File file = fileRepo.findById(idPost).get();
		User user = userService.getUserLogin();
		file.setApprovedDate(DateTimeUtils.convertDateToTimestamp(new Date()));
		file.setApproverId(user.getId());
		fileRepo.save(file);
	}

	@Override
	public void deletePost(Integer idPost) {
		File file = fileRepo.findById(idPost).get();
		User user = userService.getUserLogin();
		file.setDeleteDate(DateTimeUtils.convertDateToTimestamp(new Date()));
		file.setDeleteId(user.getId());
		fileRepo.save(file);
	}

	@Override
	public Page<File> searchPost(RequestPost requestPost, Pageable pageable) {
		User user = userService.getUserLogin();
		Timestamp createDateFrom = null;
		Timestamp createDateTo = null;
		if (StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateFrom())) {//toDateFromStr convertDateToTimestamp
			createDateFrom = DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(requestPost.getCreateDateFrom(), DateTimeUtils.yyyy_MM_dd));
			createDateTo = !StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateTo()) ?
					DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr("9999-12-31", DateTimeUtils.yyyy_MM_dd))
					: DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(requestPost.getCreateDateTo(), DateTimeUtils.yyyy_MM_dd));
		}
		if (!StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateFrom()) && StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateTo())) {
			createDateFrom = DateTimeUtils.toTimestampFromStr("1900-01-01", DateTimeUtils.yyyy_MM_dd);
			createDateTo = DateTimeUtils.toTimestampFromStr(requestPost.getCreateDateTo(), DateTimeUtils.yyyy_MM_dd);
		}
		Page<File> page = null;
		if (StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateFrom()) || 
				StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateTo())) {
			if (user.getRoleId() == 1) {
				page = fileRepo.searchPostAdmin(createDateFrom, createDateTo, requestPost.getTitle(), pageable);
			}else {
				page = fileRepo.searchPostUser(createDateFrom, createDateTo, requestPost.getTitle(), user.getId(), pageable);
			}
		}
		if (!StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateFrom()) && 
				!StringUtils.isStringNotNullAndHasValue(requestPost.getCreateDateTo())) {
			if (user.getRoleId() == 1) {
				page = fileRepo.searchPostAdminNotDate(requestPost.getTitle(), pageable);
			}
			else {
				page = fileRepo.searchPostUserNotDate(requestPost.getTitle(), user.getId(), pageable);
			}
		}
		
		return page;
	}
}
