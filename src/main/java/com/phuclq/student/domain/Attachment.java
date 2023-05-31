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
@Entity(name = "ATTACHMENT")
@Getter
@Setter
@Builder
@Table(name = "ATTACHMENT")
public class Attachment extends Auditable<String>{

  @Id
  @SequenceGenerator(name = "ATTACHMENT_SEQUENCE", sequenceName = "ATTACHMENT_SEQUENCE", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACHMENT_SEQUENCE")
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "FILE_NAME", nullable = false)
  private String fileName;

  @Column(name = "LICENSE_BASE64", columnDefinition = "TEXT")
  private String licenseBase64;

  @Column(name = "FILE_TYPE", nullable = false)
  private String fileType;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "TYPE")
  private String type;

  @Column(name = "FILE_NAME_S3")
  private String fileNameS3;

  @Column(name = "REQUEST_ID")
  private Integer requestId;


  public Attachment(MultipartFile file) throws IOException {
    this.licenseBase64 = new String(Base64.decodeBase64(file.getBytes()));
    this.fileType = file.getContentType();
  }

  public Attachment(String fileName, String fileBase64, String fileType, Integer requestId,
      String type, String fileNameS3) {
    this.fileName = fileName;
    this.licenseBase64 = fileBase64;
    this.fileType = fileType;
    this.type = type;
    this.requestId = requestId;

    this.fileNameS3 = fileNameS3;
  }

}
