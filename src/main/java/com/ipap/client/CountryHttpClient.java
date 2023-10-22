package com.ipap.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipap.springsoap.gen.Country;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CountryHttpClient {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final CloseableHttpClient httpClient;

    public CountryHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Country getCountry(String name) throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/api/countries/" + name);
        CloseableHttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();

        if (response.getStatusLine().getStatusCode() != 200) {
            log.error("Cannot connect... " + response.getStatusLine().getStatusCode());
            throw new IOException();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(EntityUtils.toString(entity), new TypeReference<>() { });
    }


}
