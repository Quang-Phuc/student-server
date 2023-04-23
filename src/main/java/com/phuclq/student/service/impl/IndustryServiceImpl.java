package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Industry;
import com.phuclq.student.repository.IndustryRepository;
import com.phuclq.student.repository.UserRepository;
import com.phuclq.student.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryServiceImpl implements IndustryService {
    @Autowired
    private IndustryRepository industryRepository;

    @Override
    public List<Industry> findAll() {
        return industryRepository.findAll();
    }

    @Override
    public Industry findAllById(int id) {
        return industryRepository.getOne(id);
    }

    @Override
    public Industry save(Industry industry) {
        return industryRepository.save(industry);
    }

    @Override
    public Industry update(Industry industry) {
        return industryRepository.save(industry);
    }

    @Override
    public void deleteById(int id) {
        industryRepository.deleteById(id);

    }
}
