package com.phuclq.student.repository;

import com.phuclq.student.domain.Specialization;
import com.phuclq.student.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    List<Specialization> findAllByIndustryId(Integer industryId);

}
