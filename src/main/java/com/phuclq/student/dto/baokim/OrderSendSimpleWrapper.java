package com.phuclq.student.dto.baokim;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSendSimpleWrapper {

    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("redirect_url")
    private String redirectUrl;

    @JsonProperty("payment_url")
    private String paymentUrl;

    @JsonProperty("bank_account")
    private BankAccountWrapper bankAccount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public BankAccountWrapper getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountWrapper bankAccount) {
        this.bankAccount = bankAccount;
    }

}
