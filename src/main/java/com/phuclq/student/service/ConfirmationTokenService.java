package com.phuclq.student.service;

import com.phuclq.student.domain.ConfirmationToken;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.EmailEvent;
import com.phuclq.student.repository.ConfirmationTokenRepository;
import com.phuclq.student.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;

@Service
@Transactional
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Environment environment;

    @Autowired
    EmailService emailService;

    public SimpleMailMessage sendEmail(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());

        String port = environment.getProperty("serverConfig.port");
        String host = environment.getProperty("serverConfig.host");

        // Remote address
        //String hostAddress =  InetAddress.getLoopbackAddress().getHostAddress();
        //String hostName = InetAddress.getLoopbackAddress().getHostName();


        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("quang.phuc.777290596@gmail.com");
        String sub = "To confirm your account, please click here : "
            + "http://"+ host +":"+ port +"/api/activate-account?token=" + confirmationToken.getConfirmationToken();
        System.out.println(mailMessage.getText());
        EmailEvent emailEvent = new EmailEvent(user.getEmail(),"Complete Registration!",sub);
        emailService.sendMailSendAppointment(emailEvent);
        return mailMessage;
    }
    
    public SimpleMailMessage sendEmailUser(User user, String mess) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());

        mailMessage.setSubject("Complete change password!");
        mailMessage.setFrom("quang.phuc.777290596@gmail.com");
        mailMessage.setText(mess);

        return mailMessage;
    }

    public User confirmUserAccount(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        User user = userRepository.findByEmailIgnoreCaseAndIsDeleted(token.getUser().getEmail(),false);
        user.setIsEnable(true);
        userRepository.save(user);
        User saveUser = userRepository.findByEmailIgnoreCaseAndIsDeleted(token.getUser().getEmail(),false);
        return saveUser;
    }
    
    public SimpleMailMessage sendEmailFileHashcode(User user, String mess) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());

        mailMessage.setSubject("Mã code tài liệu!");
        mailMessage.setFrom("quang.phuc.777290596@gmail.com");
        mailMessage.setText(mess);

        return mailMessage;
    }
}
