package com.phuclq.student.service;

import com.phuclq.student.request.PaymentRequest;

public interface PaymentService {
	
	public void payment(PaymentRequest paymentRequest, String value);
		
}
