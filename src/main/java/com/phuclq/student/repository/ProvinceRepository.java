package com.phuclq.student.repository;

import com.phuclq.student.domain.Activity;
import com.phuclq.student.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {


}
