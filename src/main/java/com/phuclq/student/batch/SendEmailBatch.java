package com.phuclq.student.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.phuclq.student.dto.UserInfoDTO;
import com.phuclq.student.service.EmailSenderService;
import com.phuclq.student.service.UserService;

@Component
public class SendEmailBatch {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@Scheduled(cron="0 00 08 * * *", zone="Asia/Bangkok")
	public void sendMail(){
		List<UserInfoDTO> userInfoDTOs = userService.getUserInfos();
		emailSenderService.sendEmailsAuto(userInfoDTOs);
	}

}
