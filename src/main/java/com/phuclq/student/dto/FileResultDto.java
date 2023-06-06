package com.phuclq.student.dto;

import java.util.List;
import lombok.Data;

@Data
public class FileResultDto {
	List<FileResult> list;
	PaginationModel paginationModel;
	
}
