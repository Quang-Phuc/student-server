package com.phuclq.student.dto;

import com.phuclq.student.domain.Auditable;
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

@Data
public class RentalHouseDto extends Auditable<String> {

  private Long id;

  private Integer idProvince;

  private Integer idDistrict;

  private Integer idWard;

  private Integer idStreet;

  private String address;

  private String title;

  private String districtName;

  private Integer numberBeds;

  private Double price;

  private Integer numberBathroom;

  private Boolean closed;

  private Boolean sharedRoom;

  private Integer userId;

  private Integer acreage;

  private Integer numberToilet;
}
