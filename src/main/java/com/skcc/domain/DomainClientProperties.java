package com.skcc.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@ConfigurationProperties("domain")
@Getter
public class DomainClientProperties {
    
    private Map<String, List<String>> domainMap = new HashMap<>();
    private Long connectionTimeout = 1000L;
    private Long responseTimeout = 5000L;

}
