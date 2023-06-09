package com.phuclq.student.domain;

import javax.persistence.*;

@Entity
@Table(name = "user_history", schema = "smdb", catalog = "")
public class UserHistory extends Auditable<String>{
    private Integer id;
    private Integer userId;
    private Integer activityId;
    private String orderId;
    private String transctionId;
    private Integer status;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name = "activity_id")
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Basic
    @Column(name = "Order_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "Transction_ID")
    public String getTransctionId() {
        return transctionId;
    }

    public void setTransctionId(String transctionId) {
        this.transctionId = transctionId;
    }

    @Basic
    @Column(name = "Status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHistory that = (UserHistory) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (activityId != null ? !activityId.equals(that.activityId) : that.activityId != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (transctionId != null ? !transctionId.equals(that.transctionId) : that.transctionId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (activityId != null ? activityId.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (transctionId != null ? transctionId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public UserHistory(Integer userId, Integer activityId) {
        this.userId = userId;
        this.activityId = activityId;
    }

    public UserHistory(int id, String orderId, String transctionId, Integer status) {
        this.id = id;
        this.orderId = orderId;
        this.transctionId = transctionId;
        this.status = status;
    }
    public UserHistory() {

    }
}
