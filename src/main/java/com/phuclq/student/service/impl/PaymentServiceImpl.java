package com.phuclq.student.service.impl;

import com.phuclq.student.domain.UserHistoryCoin;
import com.phuclq.student.dto.OrderDto;
import com.phuclq.student.dto.baokim.BankResponseWrapper;
import com.phuclq.student.dto.baokim.BaoKimProperties;
import com.phuclq.student.dto.baokim.OrderSendDto;
import com.phuclq.student.dto.baokim.OrderSendParamDto;
import com.phuclq.student.dto.baokim.OrderSendResponseWrapper;
import com.phuclq.student.exception.BusinessAssert;
import com.phuclq.student.exception.BusinessException;
import com.phuclq.student.exception.BusinessExceptionCode;
import com.phuclq.student.repository.UserHistoryCoinRepository;
import com.phuclq.student.service.BaoKimService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.DateTimeUtils;
import com.phuclq.student.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phuclq.student.domain.User;
import com.phuclq.student.domain.UserCoin;
import com.phuclq.student.repository.UserCoinRepository;
import com.phuclq.student.repository.UserRepository;
import com.phuclq.student.request.PaymentRequest;
import com.phuclq.student.service.PaymentService;
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String ACCESS_TOKEN_LOCK_KEY = "business:access-token-lock-send-order";
	private static final String MBAL_DATE_SDF_PATTERN = "dd/MM/yyyy";
	public static final String AMOUNT_PAYMENT_MAX = "200000000";
	public static final String AMOUNT_PAYMENT_MIN = "1000";
	// public static final String DESCRIPTION_SEND_ORDER = "Thanh toán hồ sơ yêu cầu bảo hiểm số %s ngày %s";
	public static final String DESCRIPTION_SEND_ORDER = "Nap tien vao tai khoan %s ngay %s";
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCoinRepository userCoinRepository;
	@Autowired
	private BaoKimService baoKimService ;
	@Autowired
	private UserService userService;
	@Autowired
	private BaoKimProperties baoKimProperties;
	@Autowired
	private UserHistoryCoinRepository repository;
	@Autowired
	OrderPaymentSubService orderPaymentSubService;

	
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

	@Override
	public Object sendOrderPayment(OrderDto dto) {
//		log.info("===========================");
//
//		UserHistoryCoin historyCoin = new UserHistoryCoin();
//		User userLogin = userService.getUserLogin();
//		historyCoin.setCoin();
//		historyCoin.setDescription();
//		historyCoin.setActivityDate();
//		historyCoin.setUserId(userLogin.getId());
//
//
//
//
//		try {
//
//			OrderSendParamDto orderSendParamDto = new OrderSendParamDto();
//			String mrcOrderId = genMrcOrderId(userLogin.getPhone());
//			orderSendParamDto.setMrcOrderId(mrcOrderId);
//			orderSendParamDto.setMerchantId(!StringUtils.isEmpty(baoKimProperties.getMerchantId())
//					? Integer.valueOf(baoKimProperties.getMerchantId())
//					: 0);
//			orderSendParamDto.setTotalAmount(dto.getTotalAmount());
//
//			String description = String.format(DESCRIPTION_SEND_ORDER,
//					userLogin.getPhone(),
//					new SimpleDateFormat(DateTimeUtils.ddMMyyyy).format( LocalDateTime.now()));
//			orderSendParamDto.setDescription(description);
//			String urlSuccess = null;
//			String callBackDetail = null;
//			String callBackCancelUrl = null;
//				urlSuccess = baoKimProperties.getApiDomain()
//						+ String.format(baoKimProperties.getCallBackSuccessUrl(), userLogin.getPhone());
//				callBackDetail = baoKimProperties.getApiDomain() + baoKimProperties.getCallBackDetailUrl();
//				callBackCancelUrl = baoKimProperties.getApiDomain() + baoKimProperties.getCallBackCancelUrl();
//
//			orderSendParamDto.setUrlSuccess(urlSuccess);
//
//			orderSendParamDto.setUrlDetail(callBackDetail);
//			orderSendParamDto.setWebhooks(baoKimProperties.getWebhookUrl());
//
//			orderSendParamDto.setUrlCancel(callBackCancelUrl);
//			orderSendParamDto.setBpmId(dto.getBpmId());
//
//			String historyId = longIdGenerator.getNewId();
//			OrderSendResponseWrapper orderSendResponse = null;
//
//			try {
//				log.info("send payment info to partner");
//				orderSendResponse = orderPaymentSubService.sendOrderPayment(orderSendParamDto,
//						userService.(),
//						historyId);
//				log.info("Send successfully");
//			}
//			catch (Exception e) {
//				log.error("Send unsuccessfully", e);
//				throw new BusinessException("");
//			}
//			if (orderSendResponse != null && orderSendResponse.getOrderInfo() != null) {
//				log.info("update order {}", mrcOrderId);
//				order.setPaymentDesc(description);
//				order.setPaymentOrderId(orderSendResponse.getOrderInfo().getOrderId());
//				order.setPaymentMrcOrderId(mrcOrderId);
//
//				order = orderRepository.update(order.getId(), order);
//
//				log.info("update order successfully");
//				return new OrderSendDto(orderSendResponse.getOrderInfo());
//
//			}
//			else {
//				log.info("orderSendResponse != null && orderSendResponse.getOrderInfo() != null");
//				throw new BusinessException("");
//			}
//
//		}
//		catch (Exception e) {
//			log.error("error", e);
//			throw e;
//		}
		return  null;
	}
	private String genMrcOrderId(String phone) {
		String mrcOrderId = "HD" + phone + "_" + DateTimeUtils.getCurrentTime().getTime();

		return mrcOrderId;
	}
}