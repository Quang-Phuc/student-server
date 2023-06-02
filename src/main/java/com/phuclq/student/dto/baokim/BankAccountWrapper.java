package com.phuclq.student.dto.baokim;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankAccountWrapper {

    @JsonProperty("acc_name")
    private Integer accName;

    @JsonProperty("acc_no")
    private String accNo;

    @JsonProperty("bank_name")
    private Integer bankName;

    @JsonProperty("branch")
    private String branch;

    @JsonProperty("amount")
    private Integer amount;

    public Integer getAccName() {
        return accName;
    }

    public void setAccName(Integer accName) {
        this.accName = accName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Integer getBankName() {
        return bankName;
    }

    public void setBankName(Integer bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
