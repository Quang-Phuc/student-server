package com.phuclq.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.phuclq.student.domain.UserHistoryCoin;
import com.phuclq.student.dto.UserHistoryCoinResult;
import com.phuclq.student.service.UserHistoryCoinService;

import lombok.Data;

@RestController
public class UserHistoryCoinController {
	@Autowired
	private UserHistoryCoinService coinService;
	
	@PostMapping("/api/user/get-history-transaction")
	public ResponseEntity<?> getHistoryTransaction(@RequestBody UserHistoryCoinRequest historyCoinRequest, Pageable pageable) {
		Page<UserHistoryCoin> page = coinService.getHistoryTransaction(historyCoinRequest, pageable);
		return new ResponseEntity<Page<UserHistoryCoin>>(page, HttpStatus.OK);
	}
	
	@PostMapping("/api/manager/get-history-transaction")
	public ResponseEntity<?> getManagerHistoryTransaction(@RequestBody UserHistoryCoinRequest historyCoinRequest, Pageable pageable) {
		Page<UserHistoryCoinResult> page = coinService.getManagerHistoryTransaction(historyCoinRequest, pageable);
		return new ResponseEntity<Page<UserHistoryCoinResult>>(page, HttpStatus.OK);
	}

	@Data
	public static class UserHistoryCoinRequest {
		private Integer transaction;
		private String dateFrom;
		private String dateTo;
		private String email;
	}
}
