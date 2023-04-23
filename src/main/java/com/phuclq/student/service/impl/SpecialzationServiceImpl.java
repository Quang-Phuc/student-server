package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Industry;
import com.phuclq.student.domain.Specialization;
import com.phuclq.student.repository.IndustryRepository;
import com.phuclq.student.repository.SpecializationRepository;
import com.phuclq.student.service.IndustryService;
import com.phuclq.student.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialzationServiceImpl implements SpecializationService {
    @Autowired
    private SpecializationRepository specializationRepository;


    @Override
    public List<Specialization> findAll() {
        return specializationRepository.findAll();
    }

    @Override
    public Optional<Specialization> findAllById(int id) {
        return specializationRepository.findById(id);
    }

    @Override
    public Specialization save(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public Specialization update(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public void deleteById(int id) {
        specializationRepository.deleteById(id);
    }

    @Override
    public List<Specialization> findAllIndistry(Integer id) {
        return specializationRepository.findAllByIndustryId(id);
    }
}
