package com.phuclq.student.service;

import com.phuclq.student.dto.OrderDto;
import com.phuclq.student.dto.baokim.BankResponseWrapper;
import com.phuclq.student.request.PaymentRequest;

public interface PaymentService {
	
	public void payment(PaymentRequest paymentRequest, String value);

	public BankResponseWrapper getBanks();

  Object sendOrderPayment(OrderDto dto);

}
