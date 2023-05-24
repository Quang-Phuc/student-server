package com.phuclq.student.dto;

import java.sql.Timestamp;
import org.joda.time.DateTime;

public interface UserResult {
	 Integer getId();
     String getUser_name();
     String getPassword();
     String getEmail();
     String getPhone();
     Boolean getIs_deleted();
     Integer getRole_id();
     Boolean getIs_enable();
     Timestamp getCreated_date();
     Double getTotal_coin();
     Timestamp getBirthDay();
}
