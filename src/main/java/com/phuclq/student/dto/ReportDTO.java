package com.phuclq.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    @NotBlank
    private int idFile;
    @NotBlank
    private String typeReport;
    private String contentReport;

}
