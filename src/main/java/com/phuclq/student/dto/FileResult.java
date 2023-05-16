package com.phuclq.student.dto;

import lombok.Data;

@Data
public class FileResult {
	private Integer id;
	private String title;
	private Integer view;
	private Integer download;
	private Float price;
	private String image;
	private String createDate;
	private String fileHashCode;
	public FileResult(Object[] obj) {
		this.id = (Integer) obj[0];
		this.title = (String) obj[1];
		this.view = (Integer) obj[2];
		this.download = (Integer) obj[3];
		this.price = (Float) obj[4];
		this.image = (String) obj[5];
		this.createDate = (String) obj[6];
	}
	
	public FileResult() {
	
	}
	
}
