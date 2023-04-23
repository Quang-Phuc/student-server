package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.Category;
import com.phuclq.student.dto.CategoryFileDTO;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.service.ActivityService;
import com.phuclq.student.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private RestEntityResponse restEntityRes;

    @GetMapping("/activity/like_file/{Id}")
    public ResponseEntity<?> likeFile(@PathVariable int Id) {
        activityService.updateLikeFile(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }



}
