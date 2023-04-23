package com.phuclq.student.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Report {
    private Integer id;
    private Integer idUser;
    private Integer idFile;
    private String typeReport;
    private String contentReport;
    private Timestamp createdDate;

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
    @Column(name = "id_user")
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "id_file")
    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }

    @Basic
    @Column(name = "type_report")
    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

    @Basic
    @Column(name = "content_report")
    public String getContentReport() {
        return contentReport;
    }

    public void setContentReport(String contentReport) {
        this.contentReport = contentReport;
    }

    @Basic
    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id &&
                idUser == report.idUser &&
                idFile == report.idFile &&
                Objects.equals(typeReport, report.typeReport) &&
                Objects.equals(contentReport, report.contentReport) &&
                Objects.equals(createdDate, report.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idFile, typeReport, contentReport, createdDate);
    }
}
