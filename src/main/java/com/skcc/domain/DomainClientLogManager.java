package com.skcc.domain;

import lombok.extern.slf4j.Slf4j;

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
}
