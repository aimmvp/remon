package com.skcc.domain;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicDomainMapper {
    
    private Map<String, String> mapper = new HashMap<>();

    public void update(String url, String realUrl) {
        mapper.put(url, realUrl);
    }

    public InetSocketAddress map(InetSocketAddress address) {

        String key = new StringBuilder(address.getHostName()).append(":").append(address.getPort()).toString();

        String[] ipPort = parseIpPort(Optional.ofNullable(mapper.get(key)).orElse(key));
        String ip = ipPort[0];
        
        int port;
        try{
            port = Integer.parseInt(ipPort[1]);
        } catch(NumberFormatException ex) {
            return address;
        }

        log.debug("call ip {}, port {}", ip, port);

        return new InetSocketAddress(ip, port);
    }

    private String[] parseIpPort(String url) {
        return url.split(":");
    }
}
