package com.phuclq.student.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryFileDTO {

    private String nameCategory;
    private Integer id;
    private List<FileDTO> fileDTOList;

}
