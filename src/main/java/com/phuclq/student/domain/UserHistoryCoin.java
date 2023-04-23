package com.phuclq.student.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_history_coin", schema = "smdb", catalog = "")
public class UserHistoryCoin {
    

	private Integer id;
    private Integer userId;
    private Integer coin;
    private Timestamp activityDate;
    private Integer transaction;
    private String description;

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
    @Column(name = "coin")
    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
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
    @Column(name = "transaction")
    public Integer getTransaction() {
		return transaction;
	}

	public void setTransaction(Integer transaction) {
		this.transaction = transaction;
	}
	
	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHistoryCoin that = (UserHistoryCoin) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null)
            return false;
        if (coin != null ? !coin.equals(that.coin) : that.coin != null) return false;
        if (activityDate != null ? !activityDate.equals(that.activityDate) : that.activityDate != null) return false;
        if (transaction != null ? !transaction.equals(that.transaction) : that.transaction != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (coin != null ? coin.hashCode() : 0);
        result = 31 * result + (activityDate != null ? activityDate.hashCode() : 0);
        result = 31 * result + (transaction != null ? transaction.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
