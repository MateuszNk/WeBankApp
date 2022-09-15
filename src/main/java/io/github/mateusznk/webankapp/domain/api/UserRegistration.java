package io.github.mateusznk.webankapp.domain.api;

public class UserRegistration {
    private final String username;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final boolean newsletter;

    public UserRegistration(String username,
                            String password,
                            String email,
                            String phoneNumber,
                            boolean newsletter) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.newsletter = newsletter;
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

    public boolean getNewsletter() {
        return newsletter;
    }
}
