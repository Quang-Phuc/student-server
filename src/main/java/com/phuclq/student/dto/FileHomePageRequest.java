package com.phuclq.student.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class FileHomePageRequest {
  private String title;
  private Boolean isVip;
  private Integer priceStart;
  private Integer priceEnd;
  private Integer priceTo;
  private Integer school;
  private Integer industry;
  private Integer page;
  private Integer size;
  private Integer sizeFile;
  private String order;
  private Integer orderType;
  private Integer orderBy;
  private Integer categoryId;
  private String search;
  private List<Integer> categoryIds;
  private LocalDateTime dateFrom;
  private LocalDateTime dateTo;
  private Integer approve;
  private Boolean isLike;
  private Boolean isDownload;
  private Boolean isUser;
  private List<Integer> fileIds;
  private Integer activityId;

}
