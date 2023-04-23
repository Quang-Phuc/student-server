package com.phuclq.student.repository;

import com.phuclq.student.domain.HomeTitle;
import com.phuclq.student.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {


}
