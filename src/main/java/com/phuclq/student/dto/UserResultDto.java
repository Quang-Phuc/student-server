package com.phuclq.student.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserResultDto {
	List<UserAdminResult> list;
	PaginationModel paginationModel;
	
}
