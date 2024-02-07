package com.skcc.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

@Slf4j
public class DomainClientLogManager {
    public DomainClientLogManager(String domain, String ipPort) {
        log.error("{}을 {}로 호출에 성공했습니다.", domain, ipPort);
    }
    public DomainClientLogManager(String domain, String ipPort, Exception ex) {
        String errorMessage = "";
        String[] messages = ex.getLocalizedMessage().split("failed: ");
        errorMessage = messages.length > 1 ? messages[1] : "";

        log.error("{}:{} 호출에서 에러 발생하였습니다. 원인은 {}입니다.",domain, ipPort, errorMessage);
        log.error("ex.cause[{}]", ex.getCause());
        log.error("ex.local message[{}]", ex.getLocalizedMessage());
    }

    public DomainClientLogManager(CloseableHttpResponse response, String domain, String ipPort) {
        log.error("########## Success ###########");
        log.error("[{}] / [{}]", domain, ipPort);
        log.error("[{}] / [{}]", response.getCode(), response.getReasonPhrase());

        int statusCode = response.getCode();
        if (statusCode >= 300 && statusCode < 400 ) {
            log.error("Location : [{}]", response.getFirstHeader("Location").getValue());
        }

    }
}
