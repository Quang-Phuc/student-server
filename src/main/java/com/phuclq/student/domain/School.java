package com.phuclq.student.domain;

import javax.persistence.*;

@Entity
public class School extends Auditable<String>{
    private Integer id;
    private String schoolName;

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
    @Column(name = "school_name")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;

        School school = (School) o;

        return getSchoolName() != null ? getSchoolName().equals(school.getSchoolName()) : school.getSchoolName() == null;
    }

    @Override
    public int hashCode() {
        return getSchoolName() != null ? getSchoolName().hashCode() : 0;
    }
}
