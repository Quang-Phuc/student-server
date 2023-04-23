package com.phuclq.student.service;

import com.phuclq.student.domain.Industry;
import com.phuclq.student.domain.Specialization;

import java.util.List;
import java.util.Optional;

public interface SpecializationService {
    List<Specialization> findAll();

    Optional<Specialization> findAllById(int id);

    Specialization save(Specialization specialization);

    Specialization update(Specialization specialization);

    void deleteById(int id);

    List<Specialization> findAllIndistry(Integer id);
}
