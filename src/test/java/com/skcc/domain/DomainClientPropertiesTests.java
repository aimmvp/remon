package com.skcc.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

// @SpringBootTest
@Slf4j
public class DomainClientPropertiesTests {

    @Autowired
    private DomainClientProperties properties;

    // @Test
    public void testProperties() {

        String actual =  properties.getDomainMap().get("httpbin.org:80").get(0);
        log.error("map {}", properties.getDomainMap());
        assertEquals("52.73.4.39:80", actual);
    }
    
}
