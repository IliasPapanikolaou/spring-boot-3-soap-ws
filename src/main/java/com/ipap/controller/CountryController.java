package com.ipap.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipap.client.CountryHttpClient;
import com.ipap.client.CountryWsHttpClient;
import com.ipap.repository.CountryRepository;
import com.ipap.springsoap.gen.Country;
import com.ipap.springsoap.gen.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class CountryController {

    private static final Logger log = LoggerFactory.getLogger(CountryHttpClient.class.getName());

    private final CountryHttpClient countryHttpClient;
    private final CountryRepository countryRepository;
    private final CountryWsHttpClient countryWsHttpClient;

    public CountryController(CountryHttpClient countryHttpClient, CountryRepository
            countryRepository, CountryWsHttpClient countryWsHttpClient) {
        this.countryHttpClient = countryHttpClient;
        this.countryRepository = countryRepository;
        this.countryWsHttpClient = countryWsHttpClient;
    }

    // Called internally by HttpClient
    @GetMapping("/countries/{name}")
    public Country getCountryByName(@PathVariable String name) throws IOException {
        return countryRepository.findCountry(name);
    }

    // Called internally by HttpClient
    @PostMapping("/countries")
    public Country postCountryByName(@RequestBody String json) throws IOException {
        // Get value from JSON using Jackson
        final JsonNode node = new ObjectMapper().readTree(json);
        if (node.has("contentType")) {
            log.debug("contentType: " + node.get("contentType"));
        }
        if (node.has("name")) {
            log.debug("name: " + node.get("name"));
        }
        return countryRepository.findCountry(node.get("value").textValue());
    }

    // Call via Postman to invoke GET flow
    @GetMapping("/countries/test/{name}")
    public Country invokeGetCountryByNameApi(@PathVariable String name) throws IOException {
        return countryHttpClient.getCountryGetMethod(name);
    }

    // Call via Postman to invoke POST flow
    @PostMapping("/countries/test")
    public Country invokePostCountryByNameApi(@RequestBody String json) throws IOException {
        // Get value from JSON using Jackson
        final JsonNode node = new ObjectMapper().readTree(json);
        if (node.has("contentType")) {
            log.debug("contentType: " + node.get("contentType"));
        }
        if (node.has("name")) {
            log.debug("name: " + node.get("name"));
        }
        String name = node.get("name").textValue();
        return countryHttpClient.getCountryPostMethod(name);
    }

    @GetMapping("/countries/ws-test-invoke/{name}")
    public GetCountryResponse invokeWsCountryByName(@PathVariable String name) {
        return countryWsHttpClient.getCountriesWs(name);
    }
}
