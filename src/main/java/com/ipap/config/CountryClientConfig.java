package com.ipap.config;

import com.ipap.client.CountryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class CountryClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.ipap.springsoap.gen");
        return marshaller;
    }

    @Bean
    public CountryClient countryClient(Jaxb2Marshaller marshaller) {
        CountryClient countryClient = new CountryClient();
        countryClient.setDefaultUri("http://localhost:8080/ws");
        countryClient.setMarshaller(marshaller);
        countryClient.setUnmarshaller(marshaller);
        return countryClient;
    }
}
