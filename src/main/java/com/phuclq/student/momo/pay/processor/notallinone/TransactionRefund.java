package com.phuclq.student.momo.pay.processor.notallinone;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.phuclq.student.momo.pay.models.TransactionRefundRequest;
import com.phuclq.student.momo.pay.models.TransactionRefundResponse;
import com.phuclq.student.momo.shared.constants.Parameter;
import com.phuclq.student.momo.shared.exception.MoMoException;
import com.phuclq.student.momo.shared.sharedmodels.AbstractProcess;
import com.phuclq.student.momo.shared.sharedmodels.Environment;
import com.phuclq.student.momo.shared.sharedmodels.HttpResponse;
import com.phuclq.student.momo.shared.utils.Encoder;

public class TransactionRefund extends AbstractProcess<TransactionRefundRequest, TransactionRefundResponse> {
    public TransactionRefund(Environment environment) {
        super(environment);
    }

    public static TransactionRefundResponse process(Environment env, String partnerRefId, String storeId, String publicKey, String momoTransId, Long amount, String description, String requestId, double version) throws Exception {
        try {
            TransactionRefund transactionRefund = new TransactionRefund(env);

            TransactionRefundRequest transactionRefundRequest = transactionRefund.createTransactionRefundRequest(partnerRefId, storeId, publicKey, momoTransId, amount, description, requestId, version);
            TransactionRefundResponse transactionRefundResponse = transactionRefund.execute(transactionRefundRequest);
            return transactionRefundResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TransactionRefundResponse execute(TransactionRefundRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, TransactionRefundRequest.class);
            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[TransactionRefundResponse] [" + request.getPartnerRefId() + "] -> Error API");
            }

            TransactionRefundResponse transactionRefundResponse = getGson().fromJson(response.getData(), TransactionRefundResponse.class);
            if (transactionRefundResponse.getStatus() != 0) {
                //LogUtils.warn("[TransactionRefundResponse] -> Status: " + transactionRefundResponse.getStatus() + ", Message: " + transactionRefundResponse.getMessage());
            }

            return transactionRefundResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TransactionRefundRequest createTransactionRefundRequest(String partnerRefId, String storeId, String publicKey, String momoTransId, Long amount, String description, String requestId, double version) {

        try {
            Map<String, Object> rawData = new HashMap<>();
            rawData.put(Parameter.PARTNER_REF_ID, partnerRefId);
            rawData.put(Parameter.PARTNER_CODE, partnerInfo.getPartnerCode());
            rawData.put(Parameter.AMOUNT, amount);
            rawData.put(Parameter.MOMO_TRANS_ID, momoTransId);
            rawData.put(Parameter.STORE_ID, storeId);
            rawData.put(Parameter.DESCRIPTION, description);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(rawData);
            byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
            String hashRSA = Encoder.encryptRSA(testByte, publicKey);

            //LogUtils.debug("[TransactionRefundRequest] rawData: " + rawData + ", [Signature] -> " + hashRSA);

            return new TransactionRefundRequest(partnerInfo.getPartnerCode(),version,requestId,hashRSA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
