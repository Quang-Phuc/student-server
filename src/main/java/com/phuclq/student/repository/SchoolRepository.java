package com.phuclq.student.repository;

import com.phuclq.student.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

    School findSchoolById(int id);

}
