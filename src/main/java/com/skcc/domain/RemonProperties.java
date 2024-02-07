package com.skcc.domain;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("remon")
@Getter
public class RemonProperties {
    
    private List<String> urls = new ArrayList<>();
    private Map<String, List<String>> domains = new HashMap<>();
    private Long connectionTimeout = 1000L;
    private Long responseTimeout = 5000L;

}
