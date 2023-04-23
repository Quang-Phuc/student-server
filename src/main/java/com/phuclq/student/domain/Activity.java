package com.phuclq.student.domain;

import javax.persistence.*;

@Entity
public class Activity {
    private Integer id;
    private String activity;
    private String name;

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
    @Column(name = "activity")
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity1 = (Activity) o;

        if (id != activity1.id) return false;
        if (activity != null ? !activity.equals(activity1.activity) : activity1.activity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        return result;
    }
}
