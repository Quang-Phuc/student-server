package com.phuclq.student.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "home_title", schema = "smdb", catalog = "")
public class HomeTitle {
    private int id;
    private String name;
    private Timestamp createDate;
    private Timestamp updateDate;
    private int createdId;
    private Integer updateId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
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

    public void setUpdateId(Integer updateDate) {
        this.updateId = updateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HomeTitle that = (HomeTitle) o;

        if (id != that.id) return false;
        if (createdId != that.createdId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (updateId != null ? !updateId.equals(that.updateId) : that.updateId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + createdId;
        result = 31 * result + (updateId != null ? updateId.hashCode() : 0);
        return result;
    }
}
