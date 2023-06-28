package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Comment;
import com.phuclq.student.domain.Rate;
import com.phuclq.student.exception.NotFoundException;
import com.phuclq.student.repository.CommentRepository;
import com.phuclq.student.repository.RateRepository;
import com.phuclq.student.service.CommentService;
import com.phuclq.student.service.RateService;
import com.phuclq.student.service.UserService;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {

  @Autowired
  RateRepository rateRepository;
  @Autowired
  UserService userService;

  @Override
  public Rate rate(Rate rate) {
    rate.setCreatedDate(LocalDateTime.now());
    rate.setCreatedBy(userService.getUserLogin().getId().toString());
    return rateRepository.save(rate);
  }
}

