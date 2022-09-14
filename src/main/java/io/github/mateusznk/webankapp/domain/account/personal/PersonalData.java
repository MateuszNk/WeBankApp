package io.github.mateusznk.webankapp.domain.account.personal;

import java.time.LocalDateTime;
import java.util.Date;

public class PersonalData {
    private Integer id;
    private String name;
    private String surname;
    private Date birthDate;
    private String city;
    private String postalCode;
    private String address;
    private String country;

    public PersonalData(Integer id,
                        String name,
                        String surname,
                        Date birthDate,
                        String city,
                        String postalCode,
                        String address,
                        String country) {
        this(name, surname, birthDate, city, postalCode, address, country);
        this.id = id;
    }

    public PersonalData (String name,
                        String surname,
                        Date birthDate,
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDate() {
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
