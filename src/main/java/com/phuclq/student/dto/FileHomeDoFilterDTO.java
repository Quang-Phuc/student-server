package com.phuclq.student.dto;

import java.util.List;

import lombok.Data;

@Data
public class FileHomeDoFilterDTO {
	private Integer id;
	private String category;
	List<FileResult> listFile;
}
