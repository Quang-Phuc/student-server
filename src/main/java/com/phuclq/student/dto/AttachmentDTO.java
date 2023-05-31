package com.phuclq.student.dto;

import com.phuclq.student.domain.Attachment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {
  private Long id;
  private String mainDocument;
  private String description;
  private String attachmentName;
  private String type;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public AttachmentDTO(Attachment attachment) {
    this.id = attachment.getId();
    this.mainDocument = attachment.getLicenseBase64();
    this.description = attachment.getFileType();
    this.attachmentName = attachment.getFileName();
    this.type = attachment.getType();
    this.createdDate = attachment.getCreatedDate();
    this.updatedDate = attachment.getLastUpdatedDate();
  }
}
