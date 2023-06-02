package com.phuclq.student.service.impl;

import com.phuclq.student.dto.baokim.BankResponseWrapper;
import com.phuclq.student.service.BaoKimService;
import com.phuclq.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phuclq.student.domain.User;
import com.phuclq.student.domain.UserCoin;
import com.phuclq.student.repository.UserCoinRepository;
import com.phuclq.student.repository.UserRepository;
import com.phuclq.student.request.PaymentRequest;
import com.phuclq.student.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCoinRepository userCoinRepository;
	@Autowired
	private BaoKimService baoKimService ;
	@Autowired
	private UserService userService;
	
	public void payment(PaymentRequest paymentRequest, String value) {
		User userTakeCoin = userRepository.findByEmailIgnoreCaseAndIsDeleted(paymentRequest.getEmailTakeCoin(),false);
		UserCoin takeCoin = userCoinRepository.findByUserId(userTakeCoin.getId());
		Double totalTake = Math.floor((takeCoin.getTotalCoin() + paymentRequest.getCoin() * 0.7f) * 100)/100;
		takeCoin.setTotalCoin(totalTake);
		userCoinRepository.save(takeCoin);
		User userPaymentCoin = userRepository.findByEmailIgnoreCaseAndIsDeleted(paymentRequest.getEmailTakeCoin(),false);
		UserCoin paymentCoin = userCoinRepository.findByUserId(userPaymentCoin.getId());
		Double totalPayment = Math.floor((paymentCoin.getTotalCoin() - paymentRequest.getCoin()) * 100)/100;
		paymentCoin.setTotalCoin(totalPayment);
		userCoinRepository.save(takeCoin);
		User userAdmin = userRepository.findByEmailIgnoreCaseAndIsDeleted(value,false);
		UserCoin userAdminCoin = userCoinRepository.findByUserId(userAdmin.getId());
		Double totalTakeAdmin = Math.floor((userAdminCoin.getTotalCoin() + paymentRequest.getCoin() * 0.3f) * 100)/100;
		userAdminCoin.setTotalCoin(totalTakeAdmin);
		userCoinRepository.save(takeCoin);
		
	}

	@Override
	public BankResponseWrapper getBanks() {
		return  baoKimService.getBanks(userService.getUserLogin().getId());
	}

	public static void main(String args[]) {
		Double total1 = (double) 8f;
		Double total2 = (double) 9.56f;
		Double total = total1 + total2;
		System.out.println(Math.floor(total * 0.7f * 100)/100);
	}
}