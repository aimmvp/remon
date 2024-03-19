package com.skcc.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class GetCookieExtentionTest {

    @Autowired
    private GetCookieExtention getCookieExtention;


    @Test
    public void testGetCookieTest() throws Exception {
        String smsession = getCookieExtention.getSessionVal();
        log.error("Test : [{}]", smsession);
    }

}