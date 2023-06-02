package com.phuclq.student.service.impl;

import com.phuclq.student.domain.UserHistory;
import com.phuclq.student.domain.UserHistoryFile;
import com.phuclq.student.dto.baokim.BankResponseWrapper;
import com.phuclq.student.dto.baokim.BaoKimProperties;
import com.phuclq.student.exception.BusinessAssert;
import com.phuclq.student.exception.BusinessException;
import com.phuclq.student.exception.BusinessExceptionCode;
import com.phuclq.student.momo.shared.constants.RequestType;
import com.phuclq.student.repository.ActivityRepository;
import com.phuclq.student.repository.CategoryRepository;
import com.phuclq.student.repository.UserHistoryFileRepository;
import com.phuclq.student.repository.UserHistoryRepository;
import com.phuclq.student.service.ActivityService;
import com.phuclq.student.service.BaoKimService;
import com.phuclq.student.service.JwtEncryptSubService;
import com.phuclq.student.service.RequestHistorySubService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.StringUtils;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
@Slf4j
@Service
public class BaoKimServiceImpl implements BaoKimService {
    @Autowired
    private JwtEncryptSubService jwtEncryptSubService;
    @Autowired
    private BaoKimProperties baoKimProperties;

    @Autowired
    private RequestHistorySubService requestHistorySubService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private RestTemplate restTemplate;

    @Override
//    @Timed(value = "request_timeout", description = "Timeout")
    public BankResponseWrapper getBanks(Integer requestId) {
        BusinessAssert.notNull(requestId, "historyId cannot be null");

        String jwt = jwtEncryptSubService.createJWT(requestId);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baoKimProperties.getApiBankListUrl())
            .queryParam("jwt", jwt);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        int statusCode = 0;
        long startTimeCallWs = System.currentTimeMillis();
        String requestContent = null;
        String responseContent = null;

        try {
            ResponseEntity<BankResponseWrapper> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                BankResponseWrapper.class);

            responseContent = StringUtils.convertObjectToJson(responseEntity.getBody());
            statusCode = responseEntity.getStatusCode().value();

            return responseEntity.getBody();
        } catch (Exception e) {
            responseContent = e.getMessage();
            if (e instanceof HttpClientErrorException) {
                HttpClientErrorException clientError = (HttpClientErrorException) e;
                if (clientError.getRawStatusCode() == 400) {
                    throw new BusinessException(BusinessExceptionCode.INVALID_PARAM);
                }
            }
            throw new RuntimeException("Error call api get bank list", e);
        } finally {
            try {
                requestContent = StringUtils.convertObjectToJson(request);
                requestHistorySubService.saveLog(uriBuilder.toUriString(),
                    requestContent,
                    responseContent,
                    statusCode,
                    RequestType.BAOKIM_GET_BANK_LIST,
                    startTimeCallWs,
                    requestId);
            } catch (Exception ex) {
                log.error("Error convert object to json get bank list", ex);
            }
        }
    }
}

