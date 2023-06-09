package com.phuclq.student.service;

import com.phuclq.student.dto.EmailEvent;

public interface EmailService {

  void sendMailSendAppointment(EmailEvent emailEvent);
}
