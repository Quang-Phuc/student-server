package com.phuclq.student.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phuclq.student.domain.UserCoin;
import com.phuclq.student.dto.UserPaymentInfor;
import com.phuclq.student.repository.UserCoinRepository;
import com.phuclq.student.service.UserCoinService;

@Service
public class UserCoinServiceImpl implements UserCoinService {
	@Autowired
	private UserCoinRepository userCoinRepository;

	@Override
	public UserCoin getUserCoin(Integer userId) {
		return userCoinRepository.findByUserId(userId);
	}

	@Override
	public boolean calculateCoin(Integer userId, Double coin) {
		UserCoin userCoin = userCoinRepository.findByUserId(userId);
		if (userCoin != null) {
			Double totalCoin = userCoin.getTotalCoin() == null ? userCoin.getTotalCoin() : 0d;
			totalCoin -= coin;
			userCoin.setTotalCoin(totalCoin);
			userCoinRepository.save(userCoin);
			return true;
		}
		return false;
	}

	@Override
	public UserPaymentInfor getUserPaymentInfor(Integer userId) {
		return userCoinRepository.getUserCoinInfor(userId);
	}

}
