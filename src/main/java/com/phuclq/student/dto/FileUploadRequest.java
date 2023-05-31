package com.phuclq.student.dto;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadRequest {
	private Integer id;
    private String title;
    private Integer categoryId;
    private Integer specializationId;
    private Integer industryId;
    private Integer languageId;
    private Integer schoolId;
    private Double filePrice;
    private Integer startPageNumber;
    private Integer endPageNumber;
    private String description;
    private MultipartFile attachment;
    private MultipartFile file;
    private Boolean isVip;
    List<RequestFileDTO> files;


}
