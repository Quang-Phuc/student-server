package com.phuclq.student.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class UserInformationDTO {


    private String birthDate;//yyyy-MM-dd

    private Short gender;

    private Integer schoolId;

    private Integer address;

    private String phone ;

    String userName;

    Integer specialize;
    String yourself;
}

