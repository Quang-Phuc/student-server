package com.phuclq.student.momo.pay.processor.notallinone;

import com.phuclq.student.momo.pay.models.MoMoJson;
import com.phuclq.student.momo.pay.models.PayConfirmationRequest;
import com.phuclq.student.momo.pay.models.PayConfirmationResponse;
import com.phuclq.student.momo.shared.constants.Parameter;
import com.phuclq.student.momo.shared.exception.MoMoException;
import com.phuclq.student.momo.shared.sharedmodels.AbstractProcess;
import com.phuclq.student.momo.shared.sharedmodels.Environment;
import com.phuclq.student.momo.shared.sharedmodels.HttpResponse;
import com.phuclq.student.momo.shared.utils.Encoder;

public class PayConfirmation extends AbstractProcess<PayConfirmationRequest, PayConfirmationResponse> {

    public PayConfirmation(Environment environment) {
        super(environment);
    }

    public static PayConfirmationResponse process(Environment env, String partnerRefId, String requestType, String requestId, String momoTransId, String customerNumber, String description) throws Exception {
        try {
            PayConfirmation payConfirmation = new PayConfirmation(env);

            PayConfirmationRequest payConfirmationRequest = payConfirmation.createAppAppConfirmRequest(partnerRefId, requestType, requestId,
                    momoTransId, customerNumber, description);
            PayConfirmationResponse payConfirmationResponse = payConfirmation.execute(payConfirmationRequest);
            return payConfirmationResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PayConfirmationResponse execute(PayConfirmationRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, PayConfirmationRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[PayConfirmationResponse] [" + request.getPartnerRefId() + "] -> Error API");
            }

            PayConfirmationResponse payConfirmationResponse = getGson().fromJson(response.getData(), PayConfirmationResponse.class);

            if (payConfirmationResponse.getStatus() == 0) {
                MoMoJson data = payConfirmationResponse.getData();

                String rawData = Parameter.AMOUNT + "=" + data.getAmount() +
                        "&" + Parameter.MOMO_TRANS_ID + "=" + data.getMomoTransId() +
                        "&" + Parameter.PARTNER_CODE + "=" + data.getPartnerCode() +
                        "&" + Parameter.PARTNER_REF_ID + "=" + data.getPartnerRefId();

                String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
                //LogUtils.info("[PayConfirmationResponse] rawData: " + rawData + ", [Signature] -> " + signature + ", [MoMoSignature] -> " + payConfirmationResponse.getSignature());

                if (!signature.equals(payConfirmationResponse.getSignature())) {
                    throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
                }
            } else {
                //LogUtils.warn("[PayConfirmationResponse] -> Status: " + payConfirmationResponse.getStatus() + ", Message: " + payConfirmationResponse.getMessage());
            }
            return payConfirmationResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PayConfirmationRequest createAppAppConfirmRequest(String partnerRefId, String requestType, String requestId, String momoTransId,
                                                             String customerNumber, String description) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.PARTNER_REF_ID).append("=").append(partnerRefId).append("&")
                    .append(Parameter.REQUEST_TYPE).append("=").append(requestType).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.MOMO_TRANS_ID).append("=").append(momoTransId)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            //LogUtils.debug("[PayConfirmationResponse] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new PayConfirmationRequest(partnerInfo.getPartnerCode(), partnerRefId, customerNumber, description, momoTransId, requestType,requestId, signRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
