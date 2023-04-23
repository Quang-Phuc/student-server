package com.phuclq.student.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "image", schema = "smdb", catalog = "")
public class Image {
    private int id;
    private String name;
    private String link;
    private int idAction;
    private Timestamp createDate;
    private Timestamp updatedDate;
    private int createdId;
    private Integer updateDate;

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
    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Basic
    @Column(name = "id_action")
    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
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
    @Column(name = "update_date")
    public Integer getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Integer updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image that = (Image) o;

        if (id != that.id) return false;
        if (idAction != that.idAction) return false;
        if (createdId != that.createdId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + idAction;
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + createdId;
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
