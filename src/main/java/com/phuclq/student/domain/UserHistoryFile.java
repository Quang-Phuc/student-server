package com.phuclq.student.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_history_file", schema = "smdb", catalog = "")
public class UserHistoryFile extends Auditable<String>{
    private Integer id;
    private Integer userHisotyId;
    private Integer fileId;
    private Timestamp activityDate;
    private Boolean isFileUpdate;

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
    @Column(name = "user_hisoty_id")
    public Integer getUserHisotyId() {
        return userHisotyId;
    }

    public void setUserHisotyId(Integer userHisotyId) {
        this.userHisotyId = userHisotyId;
    }

    @Basic
    @Column(name = "file_id")
    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "activity_date")
    public Timestamp getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Timestamp activityDate) {
        this.activityDate = activityDate;
    }
    
    @Basic
    @Column(name = "is_file_update")
    public Boolean getIsFileUpdate() {
        return isFileUpdate;
    }

    public void setIsFileUpdate(Boolean isFileUpdate) {
        this.isFileUpdate = isFileUpdate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHistoryFile that = (UserHistoryFile) o;

        if (id != that.id) return false;
        if (userHisotyId != null ? !userHisotyId.equals(that.userHisotyId) : that.userHisotyId != null) return false;
        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false;
        if (activityDate != null ? !activityDate.equals(that.activityDate) : that.activityDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userHisotyId != null ? userHisotyId.hashCode() : 0);
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        result = 31 * result + (activityDate != null ? activityDate.hashCode() : 0);
        return result;
    }

    public UserHistoryFile() {
    }

    public UserHistoryFile(Integer userHisotyId, Integer fileId, Timestamp activityDate) {
        this.userHisotyId = userHisotyId;
        this.fileId = fileId;
        this.activityDate = activityDate;
    }
}
