package io.github.mateusznk.webankapp.domain.country;

public class Country {
    private Integer id;
    private final String country;

    public Country(Integer id, String country) {
        this.id = id;
        this.country = country;
    }

    public Country(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }
}
