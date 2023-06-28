package com.phuclq.student.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RATE")
@Getter
@Setter
@Builder
@Table(name = "RATE")
public class Rate extends Auditable<String>{

  @Id
  @SequenceGenerator(name = "RATE_SEQUENCE", sequenceName = "RATE_SEQUENCE", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATE_SEQUENCE")
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "REQUEST_ID", nullable = false)
  private Integer requestId;

  @Column(name = "RATE", nullable = false)
  private Double rate;

  @Column(name = "TYPE", nullable = false)
  private String type;


}
