package io.github.mateusznk.webankapp.domain.api;

public class CountryName {
    private Integer id;
    private final String country;

    public CountryName(Integer id, String country) {
        this.id = id;
        this.country = country;
    }

    public CountryName(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public Integer getId() {
        return id;
    }
}
