package com.phuclq.student.service.impl;

import com.phuclq.student.dto.EmailEvent;
import com.phuclq.student.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);


    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    @EventListener
    public void sendMailSendAppointment(EmailEvent emailEvent) {
        System.out.println("==EmailListener 1 ===" + emailEvent);
        String[] recipients = emailEvent.getReceivers().split(",");
        MimeMessagePreparator mimeMessage = message -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(recipients);
            messageHelper.setSubject(emailEvent.getSubject());
            messageHelper.setText(emailEvent.getText(), true);
        };
        try {
            mailSender.send(mimeMessage);
        } catch (MailException e) {
            LOGGER.error(e.getMessage());
        }
    }


}
