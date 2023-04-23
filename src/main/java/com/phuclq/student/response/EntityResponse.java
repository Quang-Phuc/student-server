package com.phuclq.student.response;

import java.io.Serializable;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EntityResponse<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  protected String timeStamp;
  
  protected String securityVersion;
  
  protected String result;
  
  protected String message;

  protected String errorCode;

  protected T data;


}
