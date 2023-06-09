package com.phuclq.student.service.impl;

import com.phuclq.student.domain.RentalHouse;
import com.phuclq.student.repository.RentalHouseRepository;
import com.phuclq.student.service.RentalHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RentalHouseServiceImpl implements RentalHouseService {

  @Autowired
  private RentalHouseRepository rentalHouseRepository;

  @Override
  public Page<RentalHouse> findAll(Pageable pageable) {
    return rentalHouseRepository.findAll(pageable);
  }

  @Override
  public RentalHouse findAllById(int id) {
    return rentalHouseRepository.getOne(id);
  }

  @Override
  public RentalHouse save(RentalHouse RentalHouse) {
    return rentalHouseRepository.save(RentalHouse);
  }

  @Override
  public RentalHouse update(RentalHouse RentalHouse) {
    return rentalHouseRepository.save(RentalHouse);
  }

  @Override
  public void deleteById(int id) {
    rentalHouseRepository.deleteById(id);

  }
}
