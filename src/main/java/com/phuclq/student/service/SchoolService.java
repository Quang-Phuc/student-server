package com.phuclq.student.service;

import com.phuclq.student.domain.School;

import java.util.List;

public interface SchoolService {
    List<School> findAll();

    School findAllById(int id);

    School save(School school);

    School update(School school);

    void deleteById(int id);

    List<School> saveSchools();
}
