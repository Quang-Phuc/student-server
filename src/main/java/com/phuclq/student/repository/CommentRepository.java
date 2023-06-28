package com.phuclq.student.repository;

import com.phuclq.student.domain.Activity;
import com.phuclq.student.domain.Comment;
import io.swagger.models.auth.In;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  List<Comment> findAllByRequestIdAndType(Integer requestId,String type);


}
