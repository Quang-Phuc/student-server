package com.phuclq.student.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Thời gian và người tiến hành create, update
 *
 * @author lptech
 * @since 23/02/2022
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
  @CreatedBy
  @Column(name = "CREATED_BY", updatable = false)
  private U createdBy;

  @CreatedDate
  @Column(name = "CREATED_DATE", updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedBy
  @Column(name = "LAST_UPDATED_BY")
  private U lastUpdatedBy;

  @LastModifiedDate
  @Column(name = "LAST_UPDATED_DATE")
  private LocalDateTime lastUpdatedDate;
}