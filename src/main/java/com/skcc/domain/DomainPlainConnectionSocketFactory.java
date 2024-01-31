package com.skcc.domain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;

public class DomainPlainConnectionSocketFactory extends PlainConnectionSocketFactory {

    private DynamicDomainMapper mapper;

    public DomainPlainConnectionSocketFactory(DynamicDomainMapper mapper) {
        
        this.mapper = mapper;
    }

    @Override
    public Socket connectSocket(
            final TimeValue connectTimeout,
            final Socket socket,
            final HttpHost host,
            final InetSocketAddress remoteAddress,
            final InetSocketAddress localAddress,
            final HttpContext context) throws IOException {

        return super.connectSocket(connectTimeout, socket, host, mapper.map(remoteAddress), localAddress, context);

    }

}
