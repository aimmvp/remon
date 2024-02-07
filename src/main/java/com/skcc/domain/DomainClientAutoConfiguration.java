package com.skcc.domain;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({ DomainClientProperties.class, RemonProperties.class})
@Configuration
public class DomainClientAutoConfiguration {

    @Bean
    public DomainClient domainClient(CloseableHttpClientBuilder httpclientBuilder, RemonProperties properties, DynamicDomainMapper mapper) {
        return new DomainClient(httpclientBuilder, properties, mapper);
    }     
    
    
    @Bean
    public DynamicDomainMapper dynamicDomainMapper() {
        return new DynamicDomainMapper();
    }

    @Bean
    public DomainPlainConnectionSocketFactory domainPlainConnectionSocketFactory(DynamicDomainMapper mapper) {
        return new DomainPlainConnectionSocketFactory(mapper);
    }
    
    @Bean
    public CloseableHttpClientBuilder httpclient(DomainClientProperties properties, DomainPlainConnectionSocketFactory plainConnectionSocketFactory) {
        return new CloseableHttpClientBuilder(properties, plainConnectionSocketFactory);
    }
	

}