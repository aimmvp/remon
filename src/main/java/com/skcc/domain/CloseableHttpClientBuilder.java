package com.skcc.domain;

import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
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
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
        cookie.setDomain(".sktelecom.com");
        cookie.setAttribute("SM_USER", "P064065");
        cookie.setAttribute("SM_COMPANY", "1000");
        cookie.setAttribute("SMSESSION", "abcdef");
        cookieStore.addCookie(cookie);

        return HttpClientBuilder.create().disableCookieManagement().useSystemProperties()
                .setConnectionManager(new DomainHttpClientConnectionManager(getDomainRegistry(plainConnectionSocketFactory))).evictExpiredConnections()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(
                                        Timeout.of(properties.getConnectionTimeout(), TimeUnit.MILLISECONDS))
                                .setResponseTimeout(
                                        Timeout.of(properties.getConnectionTimeout(), TimeUnit.MILLISECONDS))
                                .build())
                .setDefaultCookieStore(cookieStore)
                .build();
    }

}
