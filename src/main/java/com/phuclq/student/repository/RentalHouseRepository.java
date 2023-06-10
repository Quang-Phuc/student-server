package com.phuclq.student.repository;

import com.phuclq.student.domain.Category;
import com.phuclq.student.domain.RentalHouse;
import com.phuclq.student.dto.RentalHouseResult;
import java.sql.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalHouseRepository extends JpaRepository<RentalHouse, Integer> {

  Page<RentalHouse> findAll(Pageable pageable);

  Category findAllById(int id);

  @Query(" select rh.id as id , rh.title as title , rh.price as price ,rh.numberBathroom,rh.numberBeds, d.name as districtName from RENTING_HOUSE rh join District d on rh.idDistrict = d.id ")
  Page<RentalHouseResult> findAllHome(Pageable pageable);

}
