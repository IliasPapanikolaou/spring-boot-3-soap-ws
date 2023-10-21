package com.ipap.repository;

import com.ipap.Country;
import com.ipap.Currency;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountryRepository {

    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country greece = new Country();
        greece.setName("Greece");
        greece.setCapital("Athens");
        greece.setCurrency(Currency.EUR);
        greece.setPopulation(10_341_277);

        countries.put(greece.getName(), greece);

        Country poland = new Country();
        poland.setName("Poland");
        poland.setCapital("Warsaw");
        poland.setCurrency(Currency.PLN);
        poland.setPopulation(41_026_067);

        countries.put(poland.getName(), poland);

        Country uk = new Country();
        uk.setName("Great Britain");
        uk.setCapital("London");
        uk.setCurrency(Currency.EUR);
        uk.setPopulation(67_736_802);

        countries.put(uk.getName(), uk);
    }

    public Country findCountry(String name) {
        return countries.get(name);
    }
}
