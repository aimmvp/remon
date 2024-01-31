package com.skcc.domain;

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

    public DomainClientException(Throwable cause) {
        super(cause);
    }

}