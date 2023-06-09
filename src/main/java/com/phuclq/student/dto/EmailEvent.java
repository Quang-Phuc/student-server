package com.phuclq.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailEvent {

  private String receivers;
  private String subject;
  private String text;

}
