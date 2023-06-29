package com.phuclq.student.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.SendFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phuclq.student.dto.UserDTO;
import com.phuclq.student.dto.UserInfoDTO;

@Service("emailSenderService")
@Transactional
public class EmailSenderService {

	private static String TITLE = "TITLE";

	private final Logger log = LoggerFactory.getLogger(EmailSenderService.class);

	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String emailFrom;

	@Autowired
	public EmailSenderService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}

	public SimpleMailMessage sendOptCode(UserDTO user, Integer otp) {
		log.debug("Sending opt email to '{}'", user.getEmail());
		if (user.getEmail() == null) {
			log.debug("Email OPT doesn't exist for user '{}'", user.getId());
			return null;
		}

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());

		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom(emailFrom);
		mailMessage.setText("OTP of you : " + otp);

		return mailMessage;
	}

	public SimpleMailMessage configEmail(UserInfoDTO user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject(TITLE);
		mailMessage.setFrom(emailFrom);
		mailMessage.setText(user.getUserName());
		return mailMessage;
	}

	public List<SimpleMailMessage> getMails(List<UserInfoDTO> userInfoDTOs) {
		List<SimpleMailMessage> simpleMailMessages = new ArrayList<SimpleMailMessage>();
		userInfoDTOs.forEach(user -> {
			simpleMailMessages.add(configEmail(user));
		});
		return simpleMailMessages;
	}

	public boolean sendEmailsAuto(List<UserInfoDTO> userInfoDTOs) {
		try {
			List<SimpleMailMessage> simpleMailMessages = getMails(userInfoDTOs);
			simpleMailMessages.forEach(mail -> {
				sendEmail(mail);
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public SimpleMailMessage sendEmailUser(String email,String sub, String mess) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);

		mailMessage.setSubject(sub);
		mailMessage.setFrom(emailFrom);
		mailMessage.setText(mess);
		sendEmail(mailMessage);
		return mailMessage;
	}
}
