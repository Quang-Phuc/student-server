package com.phuclq.student.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.phuclq.student.controller.UserHistoryCoinController.UserHistoryCoinRequest;
import com.phuclq.student.domain.UserHistoryCoin;
import com.phuclq.student.dto.UserHistoryCoinResult;

public interface UserHistoryCoinService {
	Page<UserHistoryCoin> getHistoryTransaction(UserHistoryCoinRequest historyCoinRequest, Pageable pageable);
	
	Page<UserHistoryCoinResult> getManagerHistoryTransaction(UserHistoryCoinRequest historyCoinRequest, Pageable pageable);
}
