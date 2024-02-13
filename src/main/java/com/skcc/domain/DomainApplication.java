package com.skcc.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URISyntaxException;

@SpringBootApplication
@EnableScheduling
//@EnableConfigurationProperties(RemonProperties.class)
//@Slf4j
public class DomainApplication {
    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }

    @Autowired
    private DomainClient domainClient;


    @Scheduled(cron = "0/5 * * * * *")
    public void remonDomainClient() {

        try {
            domainClient.execute();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}