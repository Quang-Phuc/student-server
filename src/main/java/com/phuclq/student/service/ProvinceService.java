package com.phuclq.student.service;

import com.phuclq.student.domain.Category;
import com.phuclq.student.domain.Province;
import com.phuclq.student.dto.CategoryHomeDTO;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {
    List<Province> findAll();

    Optional<Province> findAllById(Integer id);

    Province save(Province province);

    Province update(Province province);

    void deleteById(int id);

}
