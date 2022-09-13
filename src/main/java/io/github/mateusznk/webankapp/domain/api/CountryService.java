package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.country.Country;
import io.github.mateusznk.webankapp.domain.country.CountryDao;

import java.util.List;
import java.util.stream.Collectors;

public class CountryService {
    private CountryDao countryDao = new CountryDao();

    public List<CountryName> findAllCategoryNames() {
        return countryDao.findAllCountries()
                .stream().map(CountryNameMapper::map)
                .collect(Collectors.toList());
    }

    private static class CountryNameMapper{
        static CountryName map(Country c) {
            return new CountryName(
                    c.getId(),
                    c.getCountry()
            );
        }
    }
}
