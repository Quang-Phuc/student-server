package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.Comment;
import com.phuclq.student.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> comment(@RequestBody Comment comment) {
        Comment comment1 = commentService.comment(comment);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(comment1).getResponse();
    }

    @GetMapping("/comment/like/{id}")
    public ResponseEntity<?> like(@PathVariable("id") Integer id) {
        Comment like = commentService.like(id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(like).getResponse();
    }

    @DeleteMapping("/comment/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        commentService.delete(id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }


}
