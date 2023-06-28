package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.Comment;
import com.phuclq.student.service.ActivityService;
import com.phuclq.student.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RestEntityResponse restEntityRes;

    @PostMapping("/comment")
    public ResponseEntity<?> comment(Comment comment) {
        commentService.comment(comment);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }

    @PostMapping("/comment/like")
    public ResponseEntity<?> like(@PathVariable int Id) {
        commentService.like(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }



}
