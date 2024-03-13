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
    // 호출 에러
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

    public DomainClientLogManager(String reqUrl, CloseableHttpResponse response, String domain, String ipPort) {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // HttpEntity를 사용하여 본문 처리
            String responseBody = null;
            try {
                responseBody = EntityUtils.toString(entity);
                int statusCode = response.getCode();
                if (statusCode >= 300 && statusCode < 400 ) {
                    String location = response.getFirstHeader("Location").getValue();
                    log.error("{}을 {}로 호출하여 resopnse code 로 {}을 받아서 {}로 redirection 되었습니다.", reqUrl, ipPort, response.getCode(), location);
                } else {
                    log.error("{}을 {}로 호출하여 response code 로 {}을 받았습니다.", reqUrl, ipPort, response.getCode() );
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("##### entity is not null");
        }


    }
}
