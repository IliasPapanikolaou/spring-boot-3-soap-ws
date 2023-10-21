package com.ipap.controller;

import com.ipap.client.CountryHttpClient;
import com.ipap.repository.CountryRepository;
import com.ipap.springsoap.gen.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class CountryController {

    private final CountryHttpClient countryHttpClient;
    private final CountryRepository countryRepository;

    public CountryController(CountryHttpClient countryHttpClient, CountryRepository countryRepository) {
        this.countryHttpClient = countryHttpClient;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/countries/{name}")
    public Country getCountryByName(@PathVariable String name) throws IOException {
        return countryRepository.findCountry(name);
    }

    @GetMapping("/countries/invoke/{name}")
    public Country invokeGetCountryByNameApi(@PathVariable String name) throws IOException {
        return countryHttpClient.getCountry(name);
    }
}
