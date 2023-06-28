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
    Comment commentSave = new Comment();
    commentSave.setCreatedBy(userLogin.toString());
    commentSave.setCreatedDate(LocalDateTime.now());
    commentSave.setRequestId(comment.getRequestId());
    commentSave.setType(comment.getType());
    commentSave.setContent(comment.getContent());
    commentSave.setIsDelete(false);
     return commentRepository.save(commentSave);
  }

  @Override
  public Comment like(Integer id) {
    Comment comment = commentRepository.findById(id).orElseThrow(NotFoundException::new);
    comment.setTotalLike(Objects.nonNull(comment.getTotalLike())?comment.getTotalLike()+1:1);
   return commentRepository.save(comment);
  }
}

