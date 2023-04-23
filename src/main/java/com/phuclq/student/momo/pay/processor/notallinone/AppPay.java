package com.phuclq.student.momo.pay.processor.notallinone;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.phuclq.student.momo.pay.models.AppPayRequest;
import com.phuclq.student.momo.pay.models.AppPayResponse;
import com.phuclq.student.momo.shared.constants.Parameter;
import com.phuclq.student.momo.shared.exception.MoMoException;
import com.phuclq.student.momo.shared.sharedmodels.AbstractProcess;
import com.phuclq.student.momo.shared.sharedmodels.Environment;
import com.phuclq.student.momo.shared.sharedmodels.HttpResponse;
import com.phuclq.student.momo.shared.utils.Encoder;

public class AppPay extends AbstractProcess<AppPayRequest, AppPayResponse> {

    public AppPay(Environment environment) {
        super(environment);
    }

    public static AppPayResponse process(Environment env, String partnerRefId, String partnerTransId, long amount, String partnerName, String storeId, String storeName, String publicKey, String customerNumber, String appData, String description, double version, int payType) throws Exception {
        try {
            AppPay appPay = new AppPay(env);
            AppPayRequest appPayRequest = appPay.createAppPayProcessingRequest(partnerRefId,
                    partnerTransId, amount, partnerName, storeId, storeName, publicKey, customerNumber, appData, description, version, payType);
            AppPayResponse appPayResponse = appPay.execute(appPayRequest);
            return appPayResponse;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public AppPayResponse execute(AppPayRequest request) throws MoMoException {

        try {
            String payload = getGson().toJson(request, AppPayRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[AppPayResponse] [" + request.getPartnerRefId() + "] -> Error API");
            }

            AppPayResponse appPayResponse = getGson().fromJson(response.getData(), AppPayResponse.class);

            if (appPayResponse.getStatus() == 0) {
                String rawData = Parameter.STATUS + "=" + appPayResponse.getStatus() +
                        "&" + Parameter.MESSAGE + "=" + appPayResponse.getMessage() +
                        "&" + Parameter.AMOUNT + "=" + appPayResponse.getAmount() +
                        "&" + Parameter.PAY_TRANS_ID + "=" + appPayResponse.getTransid();

                String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

                if (signature.equals(appPayResponse.getSignature())) {
                    return appPayResponse;
                } else {
                    throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
                }
            } else {
                //LogUtils.warn("[AppPayResponse] -> Process Failed: " + appPayResponse.getError().toString());
            }
        } catch (Exception e) {
            //LogUtils.error("[AppPayResponse] "+ e);
        }

        return null;
    }

    public AppPayRequest createAppPayProcessingRequest(String partnerRefId, String partnerTransId, long amount, String partnerName,
                                                       String storeId, String storeName, String publicKey, String customerNumber, String appData,
                                                       String description, double version, int payType) {

        try {

            Map<String, Object> rawData = new HashMap<>();
            rawData.put(Parameter.PARTNER_REF_ID, partnerRefId);
            rawData.put(Parameter.PARTNER_CODE, partnerInfo.getPartnerCode());
            rawData.put(Parameter.AMOUNT, amount);
            rawData.put(Parameter.PARTNER_NAME, partnerName);
            rawData.put(Parameter.PARTNER_TRANS_ID, partnerTransId);
            rawData.put(Parameter.STORE_ID, storeId);
            rawData.put(Parameter.STORE_NAME, storeName);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(rawData);
            byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
            String hashRSA = Encoder.encryptRSA(testByte, publicKey);

            //LogUtils.debug("[AppPayRequest] rawData: " + rawData + ", [Signature] -> " + hashRSA);

            return new AppPayRequest(partnerInfo.getPartnerCode(), partnerRefId, customerNumber,description,version,payType,appData,hashRSA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
