package com.phuclq.student.dto;

import java.util.List;
import lombok.Data;

@Data
public class HistoryFileResultResult {

  List<HistoryFileResultDto> historyFileResultDtos;
  PaginationModel paginationModel;
}
