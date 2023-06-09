package com.phuclq.student.controller;

import com.phuclq.student.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.request.PaymentRequest;
import com.phuclq.student.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RestEntityResponse restEntityRes;
	
	@Value("${student.account.admin}")
	private String value;
	
	@PostMapping("/payment-for-download")
	public ResponseEntity<?> payment(@RequestBody PaymentRequest paymentRequest) {
		paymentService.payment(paymentRequest, value);
		return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
	}
	@RequestMapping(value = "/get-banks", method = RequestMethod.GET)
	public ResponseEntity<?> getBanks() {
		return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(paymentService.getBanks()).getResponse();
	}

	@RequestMapping(value = "/send-order-payment", method = RequestMethod.POST)
	public ResponseEntity<?> sendOrderPayment(@RequestBody OrderDto dto) {
		return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(paymentService.sendOrderPayment(dto)).getResponse();
	}


}
