package com.phuclq.student.service.impl;

//import com.itextpdf.text.DocumentException;

import com.phuclq.student.common.Constants;
import com.phuclq.student.domain.Attachment;
import com.phuclq.student.dto.AttachmentDTO;
import com.phuclq.student.dto.RequestFileDTO;
import com.phuclq.student.exception.ExceptionUtils;
import com.phuclq.student.repository.AttachmentRepository;
import com.phuclq.student.service.AttachmentService;
import com.phuclq.student.service.S3StorageService;
import com.phuclq.student.utils.Base64ToMultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {


  private final S3StorageService s3StorageService;

  private final AttachmentRepository attachmentRepository;


  @Override
  @Transactional
  public Long createListAttachmentsFromBase64S3(List<RequestFileDTO> files, Integer requestId)
      throws IOException {
    files.forEach(x -> {
     deleteAttachmentByRequestId(requestId);

      Map<String, List<RequestFileDTO>> collect = files.stream().collect(Collectors.groupingBy(
          e -> e.getType() == null ? Constants.DEFAULT_FILE_TYPE : e.getType()));

      String base64String;
      Optional<Attachment> existAttOptional;
      Attachment existAtt;
      for (Map.Entry<String, List<RequestFileDTO>> entry : collect.entrySet()) {
        String typeFile = entry.getKey();
        base64String = x.getContent();
        String  dataUir = String.format(Constants.START_BASE64_STRING, Constants.APPLICATION_PDF);
        MultipartFile base64ToMultipartFile = new Base64ToMultipartFile(base64String, dataUir,
            String.format(Constants.STRING_FORMAT_2_VARIABLE_WITH_UNDERLINED, requestId,

                typeFile,x.getName()));

        Attachment attachment = null; // upload to s3
        try {
          attachment = attachmentRepository.save(
              new Attachment(x.getName(), StringUtils.EMPTY, x.getType(), requestId,
                  x.getAttachmentTypeCode(),
                  s3StorageService.uploadFileToS3(base64ToMultipartFile)));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
    return null;
  }

  public List<Attachment> getListAttachmentByRequestId(Integer id) {
    return this.attachmentRepository.findAllByRequestId(id);
  }

  public void deleteAttachmentByRequestId(Integer id) {
     this.attachmentRepository.deleteAll(getListAttachmentByRequestId(id));
  }

  @Override
  public AttachmentDTO getAttachmentByIdFromS3(Long id, HttpServletRequest request)
      throws  IOException {
    Attachment attachment = getById(id, request);
    String base64FromS3 = s3StorageService.downloadFileFromS3(attachment.getFileNameS3());
    AttachmentDTO attachmentDTO = new AttachmentDTO(attachment);
    attachmentDTO.setMainDocument(base64FromS3);
    return attachmentDTO;
  }
  public Attachment getById(Long id, HttpServletRequest request)  {
    Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
    if (Objects.isNull(attachmentOptional)) {
      throw new IllegalArgumentException((ExceptionUtils.ATTACHMENT_NOT_EXIST));
    }
    return attachmentOptional.get();
  }

  @Override
  public AttachmentDTO getAttachmentByRequestIdFromS3(Integer requestId,String fileType)
      throws IOException {

    List<Attachment> attachmentOptional = attachmentRepository.findAllByRequestIdAndFileType(requestId,fileType);
    if (Objects.isNull(attachmentOptional)||attachmentOptional.size()==0) {
     return null;
    }
    Attachment attachment= attachmentOptional.get(0);
    String base64FromS3 = s3StorageService.downloadFileFromS3(attachment.getFileNameS3());
    AttachmentDTO attachmentDTO = new AttachmentDTO(attachment);
    attachmentDTO.setMainDocument(base64FromS3);
    return attachmentDTO;
  }
}
