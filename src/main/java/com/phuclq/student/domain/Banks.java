package com.phuclq.student.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Banks {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="bank_name")
    private String bankName;

}
