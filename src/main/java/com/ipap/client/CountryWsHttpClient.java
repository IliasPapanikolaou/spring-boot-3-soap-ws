package com.ipap.client;

import com.ipap.springsoap.gen.GetCountryRequest;
import com.ipap.springsoap.gen.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class CountryWsHttpClient {

    private static final Logger log = LoggerFactory.getLogger(CountryWsHttpClient.class.getName());

    private final WebServiceTemplate webServiceTemplate;

    public CountryWsHttpClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }


    public GetCountryResponse getCountriesWs(String name) {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.ipap.springsoap.gen");

        GetCountryRequest request = new GetCountryRequest();
        request.setName(name);

        webServiceTemplate.setDefaultUri("http://localhost:8080/ws");
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);

        return (GetCountryResponse) webServiceTemplate.marshalSendAndReceive(request);
    }
}
