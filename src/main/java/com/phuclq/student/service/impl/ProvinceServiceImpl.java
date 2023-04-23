package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Category;
import com.phuclq.student.domain.Province;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.repository.CategoryRepository;
import com.phuclq.student.repository.ProvinceRepository;
import com.phuclq.student.service.CategoryService;
import com.phuclq.student.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    ProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Optional<Province> findAllById(Integer id) {
        return  provinceRepository.findById(id);
    }

    @Override
    public Province save(Province province) {
        return provinceRepository.save(province);
    }

    @Override
    public void deleteById(int id) {
        provinceRepository.deleteById(id);
    }



    @Override
    public Province update(Province province) {



        return null;
    }
}
