package com.phuclq.student.dto.baokim;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSendParamDto {

    @JsonProperty("mrc_order_id")
    private String mrcOrderId;

    @JsonProperty("merchant_id")
    private Integer merchantId;

    @JsonProperty("total_amount")
    private Integer totalAmount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("url_success")
    private String urlSuccess;

    @JsonProperty("url_detail")
    private String urlDetail;

    @JsonProperty("url_cancel")
    private String urlCancel;

    @JsonProperty("webhooks")
    private String webhooks;

    @JsonProperty("bpm_id")
    private Integer bpmId;

    public String getMrcOrderId() {
        return mrcOrderId;
    }

    public void setMrcOrderId(String mrcOrderId) {
        this.mrcOrderId = mrcOrderId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlSuccess() {
        return urlSuccess;
    }

    public void setUrlSuccess(String urlSuccess) {
        this.urlSuccess = urlSuccess;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }

    public String getUrlCancel() {
        return urlCancel;
    }

    public void setUrlCancel(String urlCancel) {
        this.urlCancel = urlCancel;
    }

    public String getWebhooks() {
        return webhooks;
    }

    public void setWebhooks(String webhooks) {
        this.webhooks = webhooks;
    }

    public Integer getBpmId() {
        return bpmId;
    }

    public void setBpmId(Integer bpmId) {
        this.bpmId = bpmId;
    }

}
