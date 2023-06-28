package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Comment;
import com.phuclq.student.exception.NotFoundException;
import com.phuclq.student.repository.CommentRepository;
import com.phuclq.student.service.CommentService;
import com.phuclq.student.service.UserService;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  CommentRepository commentRepository;
  @Autowired
  UserService userService;

  @Override
  public Comment comment(Comment comment) {

    Integer userLogin = userService.getUserLogin().getId();
    comment.setCreatedBy(userLogin.toString());
    comment.setCreatedDate(LocalDateTime.now());
    comment.setRequestId(comment.getRequestId());
    comment.setType(comment.getType());
    comment.setContent(comment.getContent());
     return commentRepository.save(comment);
  }

  @Override
  public Comment like(Integer id) {
    Comment comment = commentRepository.findById(id).orElseThrow(NotFoundException::new);
    comment.setTotalLike(Objects.nonNull(comment.getTotalLike())?comment.getTotalLike()+1:1);
   return commentRepository.save(comment);
  }
}

