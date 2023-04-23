package com.phuclq.student.service;

import com.phuclq.student.domain.Industry;

import java.util.List;

public interface IndustryService {
    List<Industry> findAll();

    Industry findAllById(int id);

    Industry save(Industry industry);

    Industry update(Industry industry);

    void deleteById(int id);
}
