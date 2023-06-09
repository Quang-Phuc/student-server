package com.phuclq.student.service;

import com.phuclq.student.domain.RentalHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentalHouseService {
    Page<RentalHouse> findAll(Pageable pageable);

    RentalHouse findAllById(int id);

    RentalHouse save(RentalHouse RentalHouse);

    RentalHouse update(RentalHouse RentalHouse);

    void deleteById(int id);
}
