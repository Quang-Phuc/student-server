package com.phuclq.student.dto;

import java.util.List;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CategoryHomeFileResult {
    List<FileHomeDoFilterDTO> fileHomeDoFilterDTOS;
    PaginationModel paginationModel;

}
