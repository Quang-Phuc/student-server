package com.phuclq.student.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.phuclq.student.controller.UserHistoryCoinController.UserHistoryCoinRequest;
import com.phuclq.student.domain.User;
import com.phuclq.student.domain.UserHistoryCoin;
import com.phuclq.student.dto.UserHistoryCoinResult;
import com.phuclq.student.repository.UserHistoryCoinRepository;
import com.phuclq.student.service.UserHistoryCoinService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.DateTimeUtils;
import com.phuclq.student.utils.StringUtils;

@Service
public class UserHistoryCoinServiceImpl implements UserHistoryCoinService{
	@Autowired
	private UserHistoryCoinRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Page<UserHistoryCoin> getHistoryTransaction(UserHistoryCoinRequest historyCoinRequest, Pageable pageable) {
		
		Timestamp dateFrom = null;
		Timestamp dateTo = null;
		User user = userService.getUserLogin();
		if (StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom())) {
			dateFrom = DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(historyCoinRequest.getDateFrom(), DateTimeUtils.yyyy_MM_dd));
			dateTo = !StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateTo()) ?
					DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr("9999-12-31", DateTimeUtils.yyyy_MM_dd))
					: DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(historyCoinRequest.getDateTo(), DateTimeUtils.yyyy_MM_dd));
		}
		if (!StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) && StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateTo())) {
			dateFrom = DateTimeUtils.toTimestampFromStr("1900-01-01", DateTimeUtils.yyyy_MM_dd);
			dateTo = DateTimeUtils.toTimestampFromStr(historyCoinRequest.getDateTo(), DateTimeUtils.yyyy_MM_dd);
		}
		
		Page<UserHistoryCoin> page = null;
		if (historyCoinRequest.getTransaction() == null) {
			page = StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) 
					|| StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) ? 
					repository.findByActiveDate(user.getId(), dateFrom, dateTo, pageable)
					: repository.findByUserId(user.getId(), pageable);
		}
		else {
			page = StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) 
					|| StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) ? 
					repository.findByTransactionAndActiveDate(historyCoinRequest.getTransaction(), user.getId(), dateFrom, dateTo, pageable)
					: repository.findByTransactionAndUserId(historyCoinRequest.getTransaction(), user.getId(), pageable);
		}
		return page;
	}

	@Override
	public Page<UserHistoryCoinResult> getManagerHistoryTransaction(UserHistoryCoinRequest historyCoinRequest,
			Pageable pageable) {
		Timestamp dateFrom = null;
		Timestamp dateTo = null;
		if (StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom())) {
			dateFrom = DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(historyCoinRequest.getDateFrom(), DateTimeUtils.yyyy_MM_dd));
			dateTo = !StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateTo()) ?
					DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr("9999-12-31", DateTimeUtils.yyyy_MM_dd))
					: DateTimeUtils.convertDateToTimestamp(DateTimeUtils.toDateFromStr(historyCoinRequest.getDateTo(), DateTimeUtils.yyyy_MM_dd));
		}
		if (!StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) && StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateTo())) {
			dateFrom = DateTimeUtils.toTimestampFromStr("1900-01-01", DateTimeUtils.yyyy_MM_dd);
			dateTo = DateTimeUtils.toTimestampFromStr(historyCoinRequest.getDateTo(), DateTimeUtils.yyyy_MM_dd);
		}
		Page<UserHistoryCoinResult> page = null;
		if (historyCoinRequest.getTransaction() == null) {
			page = StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) 
					|| StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) ? 
					repository.getManagerTransaction(dateFrom, dateTo, historyCoinRequest.getEmail(), pageable)
					: repository.getManagerTransaction(historyCoinRequest.getEmail(), pageable);
		}
		else {
			page = StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) 
					|| StringUtils.isStringNotNullAndHasValue(historyCoinRequest.getDateFrom()) ? 
					repository.getManagerTransaction(historyCoinRequest.getTransaction(), dateFrom, dateTo, historyCoinRequest.getEmail(), pageable)
					: repository.getManagerTransaction(historyCoinRequest.getTransaction(), historyCoinRequest.getEmail(), pageable);
		}
		return page;
	}

}
