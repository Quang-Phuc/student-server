package com.phuclq.student.domain;

import java.time.Instant;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class File {
    private Integer id;
    private String title;
    private String description;
    private String image;
    private String attachment;
    private Integer authorId;
    private Integer approverId;
    private Boolean isVip;
    private Boolean isDeleted;
    private Double rating;
    private Integer reading;
    private Integer dowloading;
    private Integer view;
    private Integer startPageNumber;
    private Integer endPageNumber;
    private Integer totalPageNumber;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Timestamp approvedDate;
    private Integer fileGroup;
    private Integer languageId;
    private Integer categoryId;
    private Integer schoolId;
    private Integer specializationId;
    private Integer industryId;
    private String file;
    private String fileCut;
    private String fileHashcode;
    private Integer totalComment;
    private Integer totalLike;
    private Boolean isLike;
    private Boolean isCard;
    @Column(name = "delete_id")
    private Integer deleteId;
    @Column(name = "delete_date")
    private Timestamp deleteDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "attachment")
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Basic
    @Column(name = "author_id")
    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "approver_id")
    public Integer getApproverId() {
        return approverId;
    }

    public void setApproverId(Integer approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "is_vip")
    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    @Basic
    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Basic
    @Column(name = "rating")
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "reading")
    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }

    @Basic
    @Column(name = "dowloading")
    public Integer getDowloading() {
        return dowloading;
    }

    @Basic
    @Column(name = "view")
    public Integer getView() {
        return view;
    }

    public void setDowloading(Integer dowloading) {
        this.dowloading = dowloading;
    }
    public void setView(Integer view) {
        this.view = view;
    }


    @Basic
    @Column(name = "start_page_number")
    public Integer getStartPageNumber() {
        return startPageNumber;
    }

    public void setStartPageNumber(Integer startPageNumber) {
        this.startPageNumber = startPageNumber;
    }

    @Basic
    @Column(name = "end_page_number")
    public Integer getEndPageNumber() {
        return endPageNumber;
    }

    public void setEndPageNumber(Integer endPageNumber) {
        this.endPageNumber = endPageNumber;
    }

    @Basic
    @Column(name = "total_page_number")
    public Integer getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(Integer totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    @Basic
    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "updated_date")
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Basic
    @Column(name = "approved_date")
    public Timestamp getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Timestamp approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Basic
    @Column(name = "file_group")
    public Integer getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(Integer fileGroup) {
        this.fileGroup = fileGroup;
    }

    @Basic
    @Column(name = "language_id")
    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    @Basic
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "total_comment")
    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }

    @Basic
    @Column(name = "total_like")
    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    @Basic
    @Column(name = "is_like")
    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    @Basic
    @Column(name = "is_card")
    public Boolean getCard() {
        return isCard;
    }

    public void setCard(Boolean card) {
        isCard = card;
    }

    @Basic
    @Column(name = "school_id")
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "specialization_id")
    public Integer getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    @Basic
    @Column(name = "industry_id")
    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }
    
    public Integer getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(Integer deleteId) {
		this.deleteId = deleteId;
	}

	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (id != file.id) return false;
        if (authorId != file.authorId) return false;
        if (title != null ? !title.equals(file.title) : file.title != null) return false;
        if (description != null ? !description.equals(file.description) : file.description != null) return false;
        if (image != null ? !image.equals(file.image) : file.image != null) return false;
        if (attachment != null ? !attachment.equals(file.attachment) : file.attachment != null) return false;
        if (approverId != null ? !approverId.equals(file.approverId) : file.approverId != null) return false;
        if (isVip != null ? !isVip.equals(file.isVip) : file.isVip != null) return false;
        if (isDeleted != null ? !isDeleted.equals(file.isDeleted) : file.isDeleted != null) return false;
        if (rating != null ? !rating.equals(file.rating) : file.rating != null) return false;
        if (reading != null ? !reading.equals(file.reading) : file.reading != null) return false;
        if (dowloading != null ? !dowloading.equals(file.dowloading) : file.dowloading != null) return false;
        if (startPageNumber != null ? !startPageNumber.equals(file.startPageNumber) : file.startPageNumber != null)
            return false;
        if (endPageNumber != null ? !endPageNumber.equals(file.endPageNumber) : file.endPageNumber != null)
            return false;
        if (totalPageNumber != null ? !totalPageNumber.equals(file.totalPageNumber) : file.totalPageNumber != null)
            return false;
        if (createdDate != null ? !createdDate.equals(file.createdDate) : file.createdDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(file.updatedDate) : file.updatedDate != null) return false;
        if (approvedDate != null ? !approvedDate.equals(file.approvedDate) : file.approvedDate != null) return false;
        if (fileGroup != null ? !fileGroup.equals(file.fileGroup) : file.fileGroup != null) return false;
        if (languageId != null ? !languageId.equals(file.languageId) : file.languageId != null) return false;
        if (categoryId != null ? !categoryId.equals(file.categoryId) : file.categoryId != null) return false;
        if (schoolId != null ? !schoolId.equals(file.schoolId) : file.schoolId != null) return false;
        if (specializationId != null ? !specializationId.equals(file.specializationId) : file.specializationId != null)
            return false;
        if (industryId != null ? !industryId.equals(file.industryId) : file.industryId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (attachment != null ? attachment.hashCode() : 0);
        result = 31 * result + authorId;
        result = 31 * result + (approverId != null ? approverId.hashCode() : 0);
        result = 31 * result + (isVip != null ? isVip.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (reading != null ? reading.hashCode() : 0);
        result = 31 * result + (dowloading != null ? dowloading.hashCode() : 0);
        result = 31 * result + (startPageNumber != null ? startPageNumber.hashCode() : 0);
        result = 31 * result + (endPageNumber != null ? endPageNumber.hashCode() : 0);
        result = 31 * result + (totalPageNumber != null ? totalPageNumber.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (approvedDate != null ? approvedDate.hashCode() : 0);
        result = 31 * result + (fileGroup != null ? fileGroup.hashCode() : 0);
        result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (schoolId != null ? schoolId.hashCode() : 0);
        result = 31 * result + (specializationId != null ? specializationId.hashCode() : 0);
        result = 31 * result + (industryId != null ? industryId.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "file")
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @Column(name = "file_cut")
    public String getFileCut() {
        return fileCut;
    }

    public void setFileCut(String fileCut) {
        this.fileCut = fileCut;
    }

    @Basic
    @Column(name = "file_hashcode")
    public String getFileHashcode() {
        return fileHashcode;
    }

    public void setFileHashcode(String fileHashcode) {
        this.fileHashcode = fileHashcode;
    }
  public File( Integer loginId) {
    this.dowloading = 0;
    this.view = 0;
    this.reading = 0;
    this.rating = 0.0;
    Instant instant = Instant.now();
    Timestamp timestamp = Timestamp.from(instant);
    this.createdDate = timestamp;
    this.updatedDate = timestamp;
    this.authorId = loginId;
    this.view = 0;
    this.authorId = loginId;
  }

    public File() {
    }
}
