package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Comment;
import com.phuclq.student.domain.UserHistory;
import com.phuclq.student.domain.UserHistoryFile;
import com.phuclq.student.exception.NotFoundException;
import com.phuclq.student.repository.ActivityRepository;
import com.phuclq.student.repository.CategoryRepository;
import com.phuclq.student.repository.CommentRepository;
import com.phuclq.student.repository.UserHistoryFileRepository;
import com.phuclq.student.repository.UserHistoryRepository;
import com.phuclq.student.service.ActivityService;
import com.phuclq.student.service.CommentService;
import com.phuclq.student.service.UserService;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  CommentRepository commentRepository;
  @Autowired
  UserService userService;

  @Override
  public void comment(Comment comment) {

      Integer userLogin = userService.getUserLogin().getId();
      comment.setCreatedBy(userLogin.toString());
      comment.setCreatedDate(LocalDateTime.now());
      commentRepository.save(comment);
  }

  @Override
  public void like(Integer id) {
    Comment comment = commentRepository.findById(id).orElseThrow(NotFoundException::new);
    comment.setTotalLike(Objects.nonNull(comment.getTotalLike())?comment.getTotalLike()+1:1);
    commentRepository.save(comment);
  }
}

