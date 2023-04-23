package com.phuclq.student.domain;

import javax.persistence.*;

@Entity
@Table(name = "user_coin", schema = "smdb", catalog = "")
public class UserCoin {
    private Integer id;
    private Integer userId;
    private Double totalCoin;

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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "total_coin")
    public Double getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(Double totalCoin) {
        this.totalCoin = totalCoin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCoin userCoin = (UserCoin) o;

        if (id != userCoin.id) return false;
        if (userId != userCoin.userId) return false;
        if (totalCoin != null ? !totalCoin.equals(userCoin.totalCoin) : userCoin.totalCoin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (totalCoin != null ? totalCoin.hashCode() : 0);
        return result;
    }
}
