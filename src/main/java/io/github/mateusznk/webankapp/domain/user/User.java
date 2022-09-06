package io.github.mateusznk.webankapp.domain.user;

import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDateTime registrationDate;
    private boolean newsletter;

    public User(String username,
                String password,
                String email,
                String phoneNumber,
                LocalDateTime registrationDate,
                boolean newsletter) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.newsletter = newsletter;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public boolean getNewsletter() { return newsletter; }
}