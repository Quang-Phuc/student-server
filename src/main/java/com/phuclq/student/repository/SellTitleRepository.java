package com.phuclq.student.repository;

import com.phuclq.student.domain.HomeTitle;
import com.phuclq.student.domain.SellTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellTitleRepository extends JpaRepository<SellTitle, Integer> {


}
