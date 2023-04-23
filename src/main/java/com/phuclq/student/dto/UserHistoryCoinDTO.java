package com.phuclq.student.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserHistoryCoinDTO {
	private Integer id;
	private Integer userId;
	private Integer coin;
	private Timestamp activityDate;
	private Integer transaction;
	private String description;
	private String email;
	private String userName;
}
