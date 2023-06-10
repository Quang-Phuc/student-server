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

  @Column(name = "id_province", nullable = false)
  private Integer idProvince;

  @Column(name = "id_district", nullable = false)
  private Integer idDistrict;

  @Column(name = "id_ward", nullable = false)
  private Integer idWard;

  @Column(name = "id_street", nullable = false)
  private Integer idStreet;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "number_beds", nullable = false)
  private Integer numberBeds;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "number_bathroom", nullable = false)
  private Integer numberBathroom;

  @Column(name = "closed", nullable = false)
  private Boolean closed;

  @Column(name = "shared_room", nullable = false)
  private Boolean sharedRoom;

  @Column(name = "user_id", nullable = false)
  private Integer userId;

  @Column(name = "acreage", nullable = false)
  private Integer acreage;

  @Column(name = "number_toilet ")
  private Integer numberToilet ;
}
