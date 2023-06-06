package com.phuclq.student.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "STUDENT_TOTAL")
@Getter
@Setter
@Builder
@Table(name = "STUDENT_TOTAL")
public class StudentTotal {
    @Id
    @SequenceGenerator(name = "STUDENT_TOTAL_SEQUENCE", sequenceName = "STUDENT_TOTAL_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDENT_TOTAL_SEQUENCE")
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "DETAIL", nullable = false)
    private String detail;



}
