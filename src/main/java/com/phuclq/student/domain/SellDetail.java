package com.phuclq.student.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sell_detail", schema = "smdb", catalog = "")
public class SellDetail {
    private int id;
    private int titleId;
    private int imageId;
    private int provinceId;
    private int districtId;
    private int wardId;
    private String detail;
    private Timestamp createDate;
    private Timestamp updatedDate;
    private int createdId;
    private Integer updateId;
    private String name;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title_id")
    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    @Basic
    @Column(name = "image_id")
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "province_id")
    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    @Basic
    @Column(name = "district_id")
    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    @Basic
    @Column(name = "ward_id")
    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    @Basic
    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
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
    @Column(name = "created_id")
    public int getCreatedId() {
        return createdId;
    }

    public void setCreatedId(int createdId) {
        this.createdId = createdId;
    }

    @Basic
    @Column(name = "update_id")
    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellDetail that = (SellDetail) o;

        if (id != that.id) return false;
        if (titleId != that.titleId) return false;
        if (imageId != that.imageId) return false;
        if (provinceId != that.provinceId) return false;
        if (districtId != that.districtId) return false;
        if (wardId != that.wardId) return false;
        if (createdId != that.createdId) return false;
        if (detail != null ? !detail.equals(that.detail) : that.detail != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (updateId != null ? !updateId.equals(that.updateId) : that.updateId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + titleId;
        result = 31 * result + imageId;
        result = 31 * result + provinceId;
        result = 31 * result + districtId;
        result = 31 * result + wardId;
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + createdId;
        result = 31 * result + (updateId != null ? updateId.hashCode() : 0);
        return result;
    }
}
