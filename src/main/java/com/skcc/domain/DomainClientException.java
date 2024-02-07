package com.skcc.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainClientException extends RuntimeException {

    public DomainClientException() {
        super();
    }

    public DomainClientException(String message) {
        super(message);
    }

    public DomainClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainClientException(String url, String port, String message, Throwable cause) {
         super(message, cause);
    }

    public DomainClientException(Throwable cause) {
        super(cause);
    }

}