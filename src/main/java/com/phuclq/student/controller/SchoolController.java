package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.School;
import com.phuclq.student.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private RestEntityResponse restEntityRes;

    @GetMapping("/school")
    public ResponseEntity<?> getAllSchool() {

        List<School> result = schoolService.findAll();
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
    }

    @PostMapping("/school/create")
    public ResponseEntity<?> createSchool(@RequestBody School school) {
        School schoolResult = schoolService.save(school);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(schoolResult).getResponse();
    }

    @PutMapping("/school/update")
    public ResponseEntity<?> updateSchool(@RequestBody School school) {

        School schoolResult = schoolService.update(school);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(schoolResult).getResponse();
    }

    @DeleteMapping("/school/delete/{Id}")
    public ResponseEntity<?> deleteSchool(@PathVariable int Id) {

        schoolService.deleteById(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }

    @GetMapping("/school/id")
    public ResponseEntity<?> findByIdSchool(@PathVariable int Id) {
        School school = schoolService.findAllById(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(school).getResponse();
    }

    @PutMapping("/school/createFromExcel")
    public ResponseEntity<?> saveSchools() {
        List<School> schools = schoolService.saveSchools();
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(schools).getResponse();
    }
}
