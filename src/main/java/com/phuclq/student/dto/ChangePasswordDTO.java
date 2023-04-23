package com.phuclq.student.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChangePasswordDTO {

   private String password;
   private String passwordNew;
   private String passwordConfirm;

}
