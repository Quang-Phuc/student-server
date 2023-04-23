package com.phuclq.student.repository;

import com.phuclq.student.domain.Activity;
import com.phuclq.student.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {


}
