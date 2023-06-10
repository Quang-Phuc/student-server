package com.phuclq.student.service.impl;

import com.phuclq.student.domain.UserHistoryCoin;
import com.phuclq.student.dto.baokim.BankResponseWrapper;
import com.phuclq.student.dto.baokim.BaoKimProperties;
import com.phuclq.student.dto.baokim.OrderSendDto;
import com.phuclq.student.dto.baokim.OrderSendParamDto;
import com.phuclq.student.exception.BusinessAssert;
import com.phuclq.student.exception.BusinessException;
import com.phuclq.student.exception.BusinessExceptionCode;
import com.phuclq.student.repository.UserHistoryCoinRepository;
import com.phuclq.student.service.BaoKimService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.DateTimeUtils;
import com.phuclq.student.utils.StringUtils;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
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
	public Object sendOrderPayment(String idFile, Integer bpmId) {
//		log.info("===========================");
//		log.info("orderId={}", idFile);
//		BusinessAssert.notNull(idFile);
//		BusinessAssert.notNull(bpmId);
//
//		UserHistoryCoin historyCoin = new UserHistoryCoin();
//		User userLogin = userService.getUserLogin();
//		historyCoin.setCoin();
//		historyCoin.setDescription();
//		historyCoin.setActivityDate();
//		historyCoin.setUserId(userLogin.getId());
//
//		Order order = orderRepository.getById(orderId);
//
//		BusinessAssert.notNull(userLogin, BusinessExceptionCode.ORDER_NOT_FOUND, BusinessExceptionCode.ORDER_NOT_FOUND);
//
//
//		try {
//
//			OrderSendParamDto orderSendParamDto = new OrderSendParamDto();
//			String mrcOrderId = genMrcOrderId(userLogin.getId());
//			orderSendParamDto.setMrcOrderId(mrcOrderId);
//			orderSendParamDto.setMerchantId(!StringUtils.isEmpty(baoKimProperties.getMerchantId())
//					? Integer.valueOf(baoKimProperties.getMerchantId())
//					: 0);
//			orderSendParamDto.setTotalAmount(order.getPaymentAmount().intValue());
//
//			String description = String.format(DESCRIPTION_SEND_ORDER,
//					order.getApplNo(),
//					new SimpleDateFormat(MBAL_DATE_SDF_PATTERN).format(order.getCreatedTime()));
//			orderSendParamDto.setDescription(description);
//			String urlSuccess = null;
//			String callBackDetail = null;
//			String callBackCancelUrl = null;
//				urlSuccess = baoKimProperties.getApiDomain()
//						+ String.format(baoKimProperties.getCallBackSuccessUrl(), order.getApplNo());
//				callBackDetail = baoKimProperties.getApiDomain() + baoKimProperties.getCallBackDetailUrl();
//				callBackCancelUrl = baoKimProperties.getApiDomain() + baoKimProperties.getCallBackCancelUrl();
//
//			orderSendParamDto.setUrlSuccess(urlSuccess);
//
//			orderSendParamDto.setUrlDetail(callBackDetail);
//			orderSendParamDto.setWebhooks(baoKimProperties.getWebhookUrl());
//
//			orderSendParamDto.setUrlCancel(callBackCancelUrl);
////            orderSendParamDto.setBpmId(bpmId);
//
//			String historyId = longIdGenerator.getNewId();
//			OrderSendResponseWrapper orderSendResponse = null;
//
//			try {
//				log.info("send payment info to partner");
//				orderSendResponse = orderPaymentSubService.sendOrderPayment(orderSendParamDto,
//						order.getIdCardNo(),
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
	private String genMrcOrderId(String appNo) {
		String mrcOrderId = "HSYCBH_" + appNo.replaceAll("/", "") + "_" + DateTimeUtils.getCurrentTime().getTime();

		return mrcOrderId;
	}
}