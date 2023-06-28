package com.phuclq.student.service;

import com.phuclq.student.domain.Comment;

public interface CommentService {

  void comment(Comment comment);

  void like(Integer id);


}
