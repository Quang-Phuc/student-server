package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.Comment;
import com.phuclq.student.domain.Rate;
import com.phuclq.student.service.CommentService;
import com.phuclq.student.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateController {
    @Autowired
    private RateService rateService;
    @Autowired
    private RestEntityResponse restEntityRes;

    @PostMapping("/rate")
    public ResponseEntity<?> comment(@RequestBody Rate rate) {
        Rate comment1 = rateService.rate(rate);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(comment1).getResponse();
    }





}
