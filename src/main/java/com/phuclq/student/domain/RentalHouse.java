package com.phuclq.student.domain;

import java.io.IOException;
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
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RENTING_HOUSE")
@Getter
@Setter
@Builder
@Table(name = "RENTING_HOUSE")
public class RentalHouse extends Auditable<String> {

  @Id
  @SequenceGenerator(name = "RENTING_HOUSE_SEQUENCE", sequenceName = "RENTING_HOUSE_SEQUENCE", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RENTING_HOUSE_SEQUENCE")
  @Column(name = "ID", nullable = false)
  private Long id;


}
