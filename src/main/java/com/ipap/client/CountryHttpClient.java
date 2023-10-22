package com.ipap.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipap.springsoap.gen.Country;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Component
public class CountryHttpClient {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final CloseableHttpClient httpClient;

    public CountryHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Country getCountryGetMethod(String name) throws IOException {
        HttpGet getRequest = new HttpGet("http://localhost:8080/api/countries/" + name);
        CloseableHttpResponse response = httpClient.execute(getRequest);

        HttpEntity entity = response.getEntity();

        if (response.getStatusLine().getStatusCode() != 200) {
            log.error("Cannot connect... " + response.getStatusLine().getStatusCode());
            throw new IOException();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(EntityUtils.toString(entity), new TypeReference<>() { });
    }

    public Country getCountryPostMethod(String name) throws IOException {
        // Create Post request
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);

        HttpPost postRequest = new HttpPost("http://localhost:8080/api/countries");
        NameValuePair requestData = new BasicNameValuePair("name", name);

        EntityTemplate requestEntity = new EntityTemplate(outputStream -> {
            objectMapper.writeValue(outputStream, requestData);
            outputStream.flush();
        });

        requestEntity.setContentType(ContentType.APPLICATION_JSON.toString());
        postRequest.setEntity(requestEntity);

        JsonNode responseData = httpClient.execute(postRequest, response -> {
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new ClientProtocolException(Objects.toString(response.getStatusLine()));
            }
            final HttpEntity responseEntity = response.getEntity();
            if (responseEntity == null) {
                return null;
            }
            try (InputStream inputStream = responseEntity.getContent()) {
                return objectMapper.readTree(inputStream);
            }
        });

        log.info(responseData.toString());
        return objectMapper.readValue(responseData.toString(), Country.class);
    }
}
