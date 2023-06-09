package com.phuclq.student.repository;

import com.phuclq.student.domain.Category;
import com.phuclq.student.domain.RentalHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalHouseRepository extends JpaRepository<RentalHouse, Integer> {

  Page<RentalHouse> findAll(Pageable pageable);

  Category findAllById(int id);
}
