package com.phuclq.student.service;

import com.phuclq.student.domain.Comment;

public interface CommentService {

  Comment comment(Comment comment);

  Comment like(Integer id);


  void delete(Integer id);
}
