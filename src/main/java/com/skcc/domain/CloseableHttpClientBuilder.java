package com.skcc.domain;

import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.util.Timeout;

public class CloseableHttpClientBuilder {

    private DomainClientProperties properties;
    private DomainPlainConnectionSocketFactory plainConnectionSocketFactory;

    public CloseableHttpClientBuilder(DomainClientProperties properties, DomainPlainConnectionSocketFactory plainConnectionSocketFactory) {
        this.properties = properties;
        this.plainConnectionSocketFactory = plainConnectionSocketFactory;
    }
    private Registry<ConnectionSocketFactory> getDomainRegistry(DomainPlainConnectionSocketFactory plainConnectionSocketFactory) {
         return RegistryBuilder.<ConnectionSocketFactory>create()
                 .register(URIScheme.HTTP.id, plainConnectionSocketFactory)
                //  .register(URIScheme.HTTPS.id, SSLConnectionSocketFactory.getSocketFactory())
                 .build();
     }

    public CloseableHttpClient build() {

        return HttpClientBuilder.create().disableCookieManagement().useSystemProperties()
                .setConnectionManager(new DomainHttpClientConnectionManager(getDomainRegistry(plainConnectionSocketFactory))).evictExpiredConnections()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(
                                        Timeout.of(properties.getConnectionTimeout(), TimeUnit.MILLISECONDS))
                                .setResponseTimeout(
                                        Timeout.of(properties.getConnectionTimeout(), TimeUnit.MILLISECONDS))
                                .build())
                .build();
    }

}
