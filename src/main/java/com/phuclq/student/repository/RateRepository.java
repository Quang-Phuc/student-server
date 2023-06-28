package com.phuclq.student.repository;

import com.phuclq.student.domain.Comment;
import com.phuclq.student.domain.Rate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

  List<Rate> findAllByRequestIdAndType(Integer requestId,String type);


}
