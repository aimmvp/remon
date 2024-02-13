package com.skcc.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DomainClient {

    private CloseableHttpClientBuilder httpclientBuilder;
    private RemonProperties properties;
    private DynamicDomainMapper mapper;

    public DomainClient(CloseableHttpClientBuilder httpclientBuilder, RemonProperties properties, DynamicDomainMapper mapper) {
        this.httpclientBuilder = httpclientBuilder;
        this.properties = properties;
        this.mapper = mapper;
    }

    public void execute() throws URISyntaxException {
        List<CloseableHttpResponse> results = new ArrayList<>();

        properties.getUrls().forEach(url -> {
            HttpGet req = new HttpGet(url);
            log.error("url ::: [{}]", url);
            try {
                String key = new StringBuffer(req.getUri().getHost()).append(":").append(req.getUri().getPort()).toString();

                log.error("Optional If : [{}]", properties.getDomains().get(key));
                Optional.ofNullable(properties.getDomains().get(key)).orElse(List.of(key)).forEach(it -> {
                    try (CloseableHttpClient httpClient = httpclientBuilder.build()){

                        log.error("key : [{}] / Optional : [{}]", key, Optional.ofNullable(it).orElse(key));
                        mapper.update(key, Optional.ofNullable(it).orElse(key));

                        try (CloseableHttpResponse result = httpClient.execute(req) ){
                            results.add(result);

                            log.error("==> Success");
                            new DomainClientLogManager(result, key, it);
                        } catch (IOException ex) {
                            log.error("==> IOException");
                            new DomainClientLogManager(key, it, ex);
//                            throw new DomainClientException(key, it, ex.getLocalizedMessage(), ex.getCause());
                        }
                    } catch (IOException ex) {
                        log.error("333333333333");
                        new DomainClientLogManager(key, it, ex);
//                        throw new DomainClientException(key, it, ex.getLocalizedMessage(), ex.getCause());
                    }
                });
            } catch (URISyntaxException ex) {
                new DomainClientLogManager(url, url, ex);
                throw new RuntimeException(ex);
            }
        });
    }
}
