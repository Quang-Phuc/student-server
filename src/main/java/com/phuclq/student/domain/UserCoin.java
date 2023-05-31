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


}
