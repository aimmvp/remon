package com.skcc.domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainClient {

    private CloseableHttpClientBuilder httpclientBuilder;
    private DomainClientProperties properties;
    private DynamicDomainMapper mapper;

    public DomainClient(CloseableHttpClientBuilder httpclientBuilder, DomainClientProperties properties, DynamicDomainMapper mapper) {
        this.httpclientBuilder = httpclientBuilder;
        this.properties = properties;
        this.mapper = mapper;
    }

    public List<CloseableHttpResponse> execute(
            final ClassicHttpRequest request) {

        List<CloseableHttpResponse> results = new ArrayList<>();

        Assert.notNull(request, "request must be not null");

        try {
            String key = new StringBuffer(request.getUri().getHost()).append(":").append(request.getUri().getPort())
                    .toString();

            Optional.ofNullable(properties.getDomainMap().get(key)).orElse(List.of(key)).forEach(it -> {
                log.error("it {}", it);
                try (CloseableHttpClient httpclient = httpclientBuilder.build()) {
                    mapper.update(key, Optional.ofNullable(it).orElse(key));

                    try (CloseableHttpResponse result = httpclient.execute(request)) {
                        results.add(result);
                    } catch (IOException ex) {
                        throw new DomainClientException(ex);
                    }
                } catch (IOException ex) {
                    throw new DomainClientException(ex);
                } 

            });
           
        } catch (URISyntaxException ex) {
            throw new DomainClientException(ex);
        }
        return results;
    }
}
