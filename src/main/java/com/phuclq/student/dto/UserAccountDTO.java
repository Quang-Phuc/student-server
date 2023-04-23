package com.phuclq.student.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Data
public class UserAccountDTO {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String fullName;
    @NotBlank
    private String rePassword;
    @NotBlank
    String captchaId;
    @NotBlank
    String captcha;
    @NotBlank
    String clientId;

}
