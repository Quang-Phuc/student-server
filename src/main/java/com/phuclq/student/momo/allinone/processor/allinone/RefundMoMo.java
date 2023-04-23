package com.phuclq.student.momo.allinone.processor.allinone;

import com.phuclq.student.momo.allinone.models.RefundMoMoRequest;
import com.phuclq.student.momo.allinone.models.RefundMoMoResponse;
import com.phuclq.student.momo.shared.constants.Parameter;
import com.phuclq.student.momo.shared.constants.RequestType;
import com.phuclq.student.momo.shared.exception.MoMoException;
import com.phuclq.student.momo.shared.sharedmodels.AbstractProcess;
import com.phuclq.student.momo.shared.sharedmodels.Environment;
import com.phuclq.student.momo.shared.sharedmodels.HttpResponse;
import com.phuclq.student.momo.shared.utils.Encoder;

public class RefundMoMo extends AbstractProcess<RefundMoMoRequest, RefundMoMoResponse> {

    public RefundMoMo(Environment environment) {
        super(environment);
    }

    public static RefundMoMoResponse process(Environment env, String requestId, String orderId, String amount, String transId) throws Exception {
        try {
            RefundMoMo refundMoMo = new RefundMoMo(env);

            RefundMoMoRequest request = refundMoMo.createRefundRequest(requestId, orderId, amount, transId);
            RefundMoMoResponse response = refundMoMo.execute(request);
            return response;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public RefundMoMoResponse execute(RefundMoMoRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, RefundMoMoRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[RefundMoMoResponse] [" + request.getOrderId() + "] -> Error API");
            }

            RefundMoMoResponse refundMoMoResponse = getGson().fromJson(response.getData(), RefundMoMoResponse.class);

//            errorMoMoProcess(refundMoMoResponse.getErrorCode());
            String rawData = Parameter.PARTNER_CODE + "=" + refundMoMoResponse.getPartnerCode() +
                    "&" + Parameter.ACCESS_KEY + "=" + refundMoMoResponse.getAccessKey() +
                    "&" + Parameter.REQUEST_ID + "=" + refundMoMoResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + refundMoMoResponse.getOrderId() +
                    "&" + Parameter.ERROR_CODE + "=" + refundMoMoResponse.getErrorCode() +
                    "&" + Parameter.TRANS_ID + "=" + refundMoMoResponse.getTransId() +
                    "&" + Parameter.MESSAGE + "=" + refundMoMoResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + refundMoMoResponse.getLocalMessage() +
                    "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_MOMO_WALLET;

            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            //LogUtils.info("[RefundMoMoResponse] rawData: " + rawData + ", [Signature] -> " + signature + ", [MoMoSignature] -> " + refundMoMoResponse.getSignature());

            if (signature.equals(refundMoMoResponse.getSignature())) {
                return refundMoMoResponse;
            } else {
                throw new MoMoException("Wrong signature from MoMo side - please contact with us");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public RefundMoMoRequest createRefundRequest(String requestId, String orderId, String amount, String transId) {
        String signature = "";

        try {
            String rawData =
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                            "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                            "&" + Parameter.REQUEST_ID + "=" + requestId +
                            "&" + Parameter.AMOUNT + "=" + amount +
                            "&" + Parameter.ORDER_ID + "=" + orderId +
                            "&" + Parameter.TRANS_ID + "=" + transId +
                            "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_MOMO_WALLET;
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

            //LogUtils.debug("[RefundMoMoRequest] rawData: " + rawData + ", [Signature] -> " + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RefundMoMoRequest request = new RefundMoMoRequest(partnerInfo.getPartnerCode(),orderId,partnerInfo.getAccessKey(),amount,signature,requestId,RequestType.REFUND_MOMO_WALLET,transId);
        return request;
    }
}
