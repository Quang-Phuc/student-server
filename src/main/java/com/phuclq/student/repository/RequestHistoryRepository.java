package com.phuclq.student.repository;

import com.phuclq.student.domain.Report;
import com.phuclq.student.domain.RequestHistory;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Integer> {


}
