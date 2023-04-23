package com.phuclq.student.dto;

import javax.validation.constraints.NotBlank;

import com.phuclq.student.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
	public UserDTO(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.isDeleted = user.getIsDeleted();
		this.roleId = user.getRoleId();
		this.isEnable = user.getIsEnable();
	}
    
    
}
