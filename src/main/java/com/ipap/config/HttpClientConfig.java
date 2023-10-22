package com.ipap.config;

import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class HttpClientConfig {

    // @Bean
    // public CloseableHttpClient getHttpClient() {
    //    return HttpClients.createDefault();
    // }

    @Bean
    public CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                // .setSSLSocketFactory(new SSLConnectionSocketFactory(
                //        SSLContexts.createSystemDefault(),
                //        new String[] { "TLSv1.2" },
                //        null,
                //        SSLConnectionSocketFactory.getDefaultHostnameVerifier()))
                .setConnectionTimeToLive(1, TimeUnit.MINUTES)
                .setDefaultSocketConfig(SocketConfig.custom()
                        .setSoTimeout(60000)
                        .build())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(60000)
                        .setSocketTimeout(60000)
                        .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                        .build())
                .build();
    }

    /**
     * While CloseableHttpClient should have the default configuration applicable to all messages exchanges,
     * one can use HttpContext to customize individual request execution parameters.
     */
    @Bean
    public HttpClientContext getHttpClientContext() {
        CookieStore cookieStore = new BasicCookieStore();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        HttpClientContext clientContext = HttpClientContext.create();
        clientContext.setCookieStore(cookieStore);
        clientContext.setCredentialsProvider(credentialsProvider);
        clientContext.setRequestConfig(RequestConfig.custom()
                .setConnectTimeout(60000)
                .setSocketTimeout(60000)
                .setCookieSpec(CookieSpecs.STANDARD)
                .build());

        return clientContext;
    }
}
