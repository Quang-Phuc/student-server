package com.phuclq.student.momo.pay.processor.notallinone;

import com.google.gson.Gson;
import com.phuclq.student.momo.pay.models.POSPayRequest;
import com.phuclq.student.momo.pay.models.POSPayResponse;
import com.phuclq.student.momo.shared.constants.Parameter;
import com.phuclq.student.momo.shared.exception.MoMoException;
import com.phuclq.student.momo.shared.sharedmodels.AbstractProcess;
import com.phuclq.student.momo.shared.sharedmodels.Environment;
import com.phuclq.student.momo.shared.sharedmodels.HttpResponse;
import com.phuclq.student.momo.shared.utils.Encoder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class POSPay extends AbstractProcess<POSPayRequest, POSPayResponse> {
    public POSPay(Environment environment) {
        super(environment);
    }

    public static POSPayResponse process(Environment env, String partnerRefId, long amount, String storeId, String storeName, String publicKey, String description, String paymentCode, double version, int payType) throws Exception {
        try {
            POSPay posPay = new POSPay(env);

            POSPayRequest posPayProcessingRequest = posPay.createPOSPayProcessingRequest(partnerRefId, amount, storeId, storeName, publicKey, paymentCode, description, version, payType);
            POSPayResponse posPayResponse = posPay.execute(posPayProcessingRequest);
            return posPayResponse;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public POSPayResponse execute(POSPayRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, POSPayRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[POSPayResponse] [" + request.getPartnerRefId() + "] -> Error API");
            }

            POSPayResponse posPayResponse = getGson().fromJson(response.getData(), POSPayResponse.class);
            if (posPayResponse.getStatus() != 0) {
                //LogUtils.warn("[POSPayResponse] -> Status: " + posPayResponse.getStatus() + ", Message: " + posPayResponse.getMessage().getDescription());
            }

            return posPayResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public POSPayRequest createPOSPayProcessingRequest(String partnerRefId, long amount, String storeId, String storeName, String publicKey,
                                                       String paymentCode, String description, double version, int payType) {

        try {

            Map<String, Object> rawData = new HashMap<>();
            rawData.put(Parameter.PARTNER_REF_ID, partnerRefId);
            rawData.put(Parameter.PARTNER_CODE, partnerInfo.getPartnerCode());
            rawData.put(Parameter.AMOUNT, amount);
            rawData.put(Parameter.PAYMENT_CODE, paymentCode);
            rawData.put(Parameter.STORE_ID, storeId);
            rawData.put(Parameter.STORE_NAME, storeName);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(rawData);
            byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
            String hashRSA = Encoder.encryptRSA(testByte, publicKey);

            //LogUtils.debug("[POSPayRequest] rawData: " + rawData + ", [Signature] -> " + hashRSA);

            return new POSPayRequest(partnerInfo.getPartnerCode(),partnerRefId,description,version,payType,hashRSA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
