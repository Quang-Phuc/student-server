package com.phuclq.student.service;

import com.phuclq.student.dto.AttachmentDTO;
import com.phuclq.student.dto.RequestFileDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

public interface AttachmentService {

  @Transactional(rollbackFor = {Exception.class, Throwable.class, RuntimeException.class})
  Long createListAttachmentsFromBase64S3(List<RequestFileDTO> files, Integer requestId,Integer loginId)
      throws IOException;

  AttachmentDTO getAttachmentByIdFromS3Update(Long id,String fileType, HttpServletRequest request) throws IOException;

  AttachmentDTO getAttachmentByRequestIdFromS3(Integer requestId,String fileType)
      throws IOException;

  List<AttachmentDTO> getAttachmentByRequestIdFromS3AndTypes(Integer requestId, List<String> fileType);

}
