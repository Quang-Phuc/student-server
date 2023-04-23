package com.phuclq.student.momo.allinone.processor.allinone;

import com.phuclq.student.momo.allinone.models.RefundATMRequest;
import com.phuclq.student.momo.allinone.models.RefundATMResponse;
import com.phuclq.student.momo.shared.constants.Parameter;
import com.phuclq.student.momo.shared.constants.RequestType;
import com.phuclq.student.momo.shared.exception.MoMoException;
import com.phuclq.student.momo.shared.sharedmodels.AbstractProcess;
import com.phuclq.student.momo.shared.sharedmodels.Environment;
import com.phuclq.student.momo.shared.sharedmodels.HttpResponse;
import com.phuclq.student.momo.shared.utils.Encoder;

public class RefundATM extends AbstractProcess<RefundATMRequest, RefundATMResponse> {

    public RefundATM(Environment environment) {
        super(environment);
    }

    public static RefundATMResponse process(Environment env, String orderId, String requestId, String amount, String transId, String bankCode) throws Exception {
        try {
            RefundATM refundATM = new RefundATM(env);

            RefundATMRequest request = refundATM.createRefundATMRequest(requestId, orderId, amount, transId, bankCode);
            RefundATMResponse response = refundATM.execute(request);
            return response;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public RefundATMResponse execute(RefundATMRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, RefundATMRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[RefundATMResponse] [" + request.getOrderId() + "] -> Error API");
            }

            RefundATMResponse refundATMResponse = getGson().fromJson(response.getData(), RefundATMResponse.class);

//            errorMoMoProcess(refundATMResponse.getErrorCode());

            String rawData = Parameter.PARTNER_CODE + "=" + refundATMResponse.getPartnerCode() +
                    "&" + Parameter.ACCESS_KEY + "=" + refundATMResponse.getAccessKey() +
                    "&" + Parameter.REQUEST_ID + "=" + refundATMResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + refundATMResponse.getOrderId() +
                    "&" + Parameter.ERROR_CODE + "=" + refundATMResponse.getErrorCode() +
                    "&" + Parameter.TRANS_ID + "=" + refundATMResponse.getTransId() +
                    "&" + Parameter.MESSAGE + "=" + refundATMResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + refundATMResponse.getLocalMessage() +
                    "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_ATM;

            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            //LogUtils.info("[RefundATMResponse] rawData: " + rawData + ", [Signature] -> " + signature + ", [MoMoSignature] -> " + refundATMResponse.getSignature());

            if (signature.equals(refundATMResponse.getSignature())) {
                return refundATMResponse;
            } else {
                throw new MoMoException("Wrong signature from MoMo side - please contact with us");
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public RefundATMRequest createRefundATMRequest(String requestId, String orderId, String amount, String transId, String bankCode) {
        String signature = "";

        try {
            String rawData =
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                            "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                            "&" + Parameter.REQUEST_ID + "=" + requestId +
                            "&" + Parameter.BANK_CODE + "=" + bankCode +
                            "&" + Parameter.AMOUNT + "=" + amount +
                            "&" + Parameter.ORDER_ID + "=" + orderId +
                            "&" + Parameter.TRANS_ID + "=" + transId +
                            "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_ATM;
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

            //LogUtils.debug("[RefundATMRequest] rawData: " + rawData + ", [Signature] -> " + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RefundATMRequest request = new RefundATMRequest(partnerInfo.getPartnerCode(),orderId,partnerInfo.getAccessKey(),amount,signature,requestId,RequestType.REFUND_ATM,transId,bankCode);
        return request;
    }
}
