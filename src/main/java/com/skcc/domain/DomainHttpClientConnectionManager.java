package com.skcc.domain;

import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.SchemePortResolver;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.DefaultHttpClientConnectionOperator;
import org.apache.hc.client5.http.io.ManagedHttpClientConnection;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.core5.http.config.Lookup;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.io.HttpConnectionFactory;

public class DomainHttpClientConnectionManager extends BasicHttpClientConnectionManager {
 
     DomainHttpClientConnectionManager(
             final Lookup<ConnectionSocketFactory> socketFactoryRegistry,
             final HttpConnectionFactory<ManagedHttpClientConnection> connFactory,
             final SchemePortResolver schemePortResolver,
             final DnsResolver dnsResolver) {
       super(new DefaultHttpClientConnectionOperator(
               socketFactoryRegistry, schemePortResolver, dnsResolver), connFactory);
     }

     public DomainHttpClientConnectionManager(Registry<ConnectionSocketFactory> domainConnectionRegistry) {
         this(domainConnectionRegistry, null, null, null);
     }
 
 
 }
 