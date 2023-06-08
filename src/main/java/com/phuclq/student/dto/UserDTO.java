package com.phuclq.student.dto;

import java.sql.Date;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;

import com.phuclq.student.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Integer id;
	@NotBlank
    private String userName;
	@NotBlank
    private String password;
	@NotBlank
    private String email;
	@NotBlank
    private String phone;
    private Boolean isDeleted;
    private Integer roleId;
    private Boolean isEnable;
    private Double totalCoin;
	private Date birthDay;

	private String fullName;
	private String gender;
	private String address;
	private String introduction;
	private String image;
	private Integer industryId;

	public UserDTO(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.isDeleted = user.getIsDeleted();
		this.roleId = user.getRoleId();
		this.isEnable = user.getIsEnable();
		this.birthDay = user.getBirthDay();

	}
    
    
}
