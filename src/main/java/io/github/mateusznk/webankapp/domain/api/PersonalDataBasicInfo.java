package io.github.mateusznk.webankapp.domain.api;

import java.time.LocalDateTime;
import java.util.Date;

public class PersonalDataBasicInfo {
    private String name;
    private String surname;
    private LocalDateTime birthDate;
    private String city;
    private String postalCode;
    private String address;
    private String country;

    public PersonalDataBasicInfo (String name,
                         String surname,
                         LocalDateTime birthDate,
                         String city,
                         String postalCode,
                         String address,
                         String country) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }
}
