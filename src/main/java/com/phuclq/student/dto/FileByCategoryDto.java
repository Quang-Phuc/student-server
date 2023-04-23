package com.phuclq.student.dto;

import java.util.List;

import lombok.Data;

@Data
public class FileByCategoryDto {
	private Integer id;
	private String category;
	List<FileResultInterface> listFile;

}
