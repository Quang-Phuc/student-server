package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Comment;
import com.phuclq.student.dto.UserDTO;
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

    UserDTO userLogin1 = userService.getUserResultLogin();
    Comment commentSave = new Comment();
    commentSave.setCreatedBy(userLogin1.getId().toString());
    commentSave.setCreatedDate(LocalDateTime.now());
    commentSave.setRequestId(comment.getRequestId());
    commentSave.setType(comment.getType());
    commentSave.setContent(comment.getContent());
    commentSave.setIsDelete(false);
    commentSave.setUserName(userLogin1.getUserName());
    commentSave.setImageUser(userLogin1.getImage());
     return commentRepository.save(commentSave);
  }

  @Override
  public Comment like(Integer id) {
    Comment comment = commentRepository.findById(id).orElseThrow(NotFoundException::new);
    comment.setTotalLike(Objects.nonNull(comment.getTotalLike())?comment.getTotalLike()+1:1);
   return commentRepository.save(comment);
  }

  @Override
  public void delete(Integer id) {
    commentRepository.delete(commentRepository.findById(id).get());

  }
}

