package com.phuclq.student.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity(name = "USER_HISTORY_COIN")
@Getter
@Setter
@Table(name = "USER_HISTORY_COIN")
public class UserHistoryCoin extends Auditable<String> {

  @Id
  @SequenceGenerator(name = "USER_HISTORY_COIN_SEQUENCE", sequenceName = "USER_HISTORY_COIN_SEQUENCE", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_HISTORY_COIN_SEQUENCE")
  @Column(name = "ID", nullable = false)
  private Integer id;

  @Column(name = "USER_ID", nullable = false)
  private Integer userId;

  @Column(name = "COIN", nullable = false)
  private Double coin;

  @Column(name = "TRANSACTION", nullable = false)
  private Integer transaction;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "TYPE", nullable = false)
  private String type;

  public UserHistoryCoin( Integer userId, Double coin, Integer transaction,
      String description,Integer loginId,String type) {
    this.userId = userId;
    this.coin = coin;
    this.transaction = transaction;
    this.description = description;
    this.type = type;
    this.setCreatedBy(loginId.toString());
    this.setCreatedDate(LocalDateTime.now());
  }
}
