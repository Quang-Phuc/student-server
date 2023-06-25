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
public class CommentDTO {
  private Long id;
  private Integer fileId;
  private String imageUser;
  private String userName;
  private String content;
  private String type;
  private Integer totalLike;
  private LocalDateTime createdDate;


}
