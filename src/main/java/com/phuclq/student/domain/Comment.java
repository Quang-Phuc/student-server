package com.phuclq.student.domain;

import com.phuclq.student.types.AttachmentStatusType;
import java.io.IOException;
import java.time.LocalDateTime;
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
@Entity(name = "COMMENT")
@Getter
@Setter
@Builder
@Table(name = "COMMENT")
public class Comment extends Auditable<String>{

  @Id
  @SequenceGenerator(name = "COMMENT_SEQUENCE", sequenceName = "COMMENT_SEQUENCE", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQUENCE")
  @Column(name = "ID")
  private Long id;

  @Column(name = "IMAGE_USER")
  private String imageUser;

  @Column(name = "REQUEST_ID")
  private Integer requestId;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "CONTENT")
  private String content;

  @Column(name = "TYPE")
  private String type;

  @Column(name = "totalLike")
  private Integer totalLike;

  @Column(name = "IS_DELETE")
  private Boolean isDelete;


}
