package com.phuclq.student.dto;

import com.phuclq.student.domain.Attachment;
import lombok.Data;

import java.util.List;

@Data
public class FileResult {
	private Integer id;
	private String title;
	private Integer view;
	private Integer download;
	private Float price;
	private String image;
	private String fileView;
	private String createDate;
	private String userName;
	private String urlAuthor;
	private String fileHashCode;
	private Integer totalComment;
	private Integer totalLike;
	private Boolean isLike;
	private Boolean isCard;
	private String category;
	private Boolean isVip;

	private Integer categoryId;
	private Integer schoolId;
	private Integer industryId;
	private String description;
	private Integer startPageNumber;
	private Integer endPageNumber;
	private Integer languageId;
	private Integer specializationId;

	List<Attachment> attachmentOptional;

	AttachmentDTO attachmentDTO;
	List<CommentDTO> commentDTO;
	private Double totalRate;
	public FileResult(Object[] obj) {
		this.id = (Integer) obj[0];
		this.title = (String) obj[1];
		this.view = (Integer) obj[2];
		this.download = (Integer) obj[3];
		this.price = (Float) obj[4];
		this.image = (String) obj[5];
		this.createDate = (String) obj[6];
		this.totalComment = (Integer) obj[7];
		this.category = (String) obj[8];
		this.totalLike = (Integer) obj[9];
		this.isLike = (Boolean) obj[10];
		this.isCard = (Boolean) obj[11];
		this.isVip = (Boolean) obj[12];
		this.categoryId = (Integer) obj[13];
		this.userName = (String) obj[14];
		this.urlAuthor = (String) obj[15];
		this.schoolId = (Integer) obj[16];
		this.industryId = (Integer) obj[17];
		this.description = (String) obj[18];
		this.startPageNumber = (Integer) obj[19];
		this.endPageNumber = (Integer) obj[20];
		this.languageId = (Integer) obj[21];
		this.specializationId = (Integer) obj[22];
	}
	
	public FileResult() {
	
	}
	
}
