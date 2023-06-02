package com.phuclq.student.dto.baokim;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSendResponseWrapper {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private List<String> message;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("data")
    private OrderSendSimpleWrapper orderInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public OrderSendSimpleWrapper getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderSendSimpleWrapper orderInfo) {
        this.orderInfo = orderInfo;
    }

}
