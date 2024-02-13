package com.skcc.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

@Slf4j
public class DomainClientLogManager {
    public DomainClientLogManager(String domain, String ipPort) {
        log.error("{}을 {}로 호출에 성공했습니다.", domain, ipPort);
    }
    public DomainClientLogManager(String domain, String ipPort, Exception ex) {
        String errorMessage = "";
        String[] messages = ex.getLocalizedMessage().split("failed: ");
        errorMessage = messages.length > 1 ? messages[1] : "";
        log.error("########## Fail Start ###########");

        log.error("{}/{} 호출에서 에러 발생하였습니다. 원인은 {}입니다.",domain, ipPort, errorMessage);
        log.error("ex.cause[{}]", ex.getCause());
        log.error("ex.local message[{}]", ex.getLocalizedMessage());
        log.error("{}", ex.getMessage());
        log.error("########## Fail End ###########");
    }

    public DomainClientLogManager(CloseableHttpResponse response, String domain, String ipPort) {
        log.error("########## Success ###########");
        log.error("[{}] / [{}]", domain, ipPort);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // HttpEntity를 사용하여 본문 처리
            String responseBody = null;
            try {
                responseBody = EntityUtils.toString(entity);
                int statusCode = response.getCode();
                if (statusCode >= 300 && statusCode < 400 ) {
                    log.error("Location : [{}]", response.getFirstHeader("Location").getValue());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            log.error("Response Code : {}", response.getCode());
            log.error("Response Reason : {}", response.getReasonPhrase());
            log.error("Response body : {}", responseBody);
        } else {
            log.error("##### entity is not null");
        }


    }
}
