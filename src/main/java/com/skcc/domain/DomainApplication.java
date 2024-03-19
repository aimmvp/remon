package com.skcc.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URISyntaxException;

@SpringBootApplication
@EnableScheduling
public class DomainApplication {
    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }
    @Autowired
    private DomainClient domainClient;

    @Autowired
    private GetCookieExtention getCookieExtention;

    @Scheduled(cron = "0/5 * * * * *") // 5분마다 실행
    public void remonDomainClient() {
        try {
            String remonUrl = "https://partnersso.sktelecom.com/swing/skt/login.html";
            domainClient.execute(getCookieExtention.getSessionVal(remonUrl));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}