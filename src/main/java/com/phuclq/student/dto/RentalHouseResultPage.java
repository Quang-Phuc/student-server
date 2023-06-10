package com.phuclq.student.dto;

import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class RentalHouseResultPage  {
  List<RentalHouseDto> rentalHouseDtos;
  PaginationModel paginationModel  ;



}
