package com.skcc.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @SpringBootTest
class DemoApplicationTests {

    // @Autowired
    private CloseableHttpClient httpclient;

    @Autowired
    private DomainClient domainclient;

    @Autowired
    private DynamicDomainMapper mapper;
    
    // @Test
    public void testDomainClient() {
        String url = "http://httpbin.org:80/get";

        HttpGet httpGet = new HttpGet(url);

        String url2 = "http://httpbin.org:80/get";

        HttpGet httpGet2 = new HttpGet(url2);

        
        try {
            List<CloseableHttpResponse> responses = domainclient.execute(httpGet);

            responses.forEach(response -> {

                assertEquals(200, response.getCode());
                assertEquals("OK", response.getReasonPhrase());
            });

            responses = domainclient.execute(httpGet2);

            responses.forEach(response -> {

                assertEquals(200, response.getCode());
                assertEquals("OK", response.getReasonPhrase());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            
        } 
    }

    // @Test
    public void testHttpClient() {
        String url = "http://httpbin.org/get";

        String resultContent = null;
        HttpGet httpGet = new HttpGet(url);

        mapper.update("httpbin.org:80", "52.73.4.39:80");

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {

            assertEquals(200, response.getCode());
            assertEquals("OK", response.getReasonPhrase());
            
        } catch (Exception ex) {
            
        }
        log.error("{}",  resultContent);
    }
}
