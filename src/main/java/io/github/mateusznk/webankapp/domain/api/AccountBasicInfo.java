package io.github.mateusznk.webankapp.domain.api;

public class AccountBasicInfo {
    private Integer id;
    private String accountNumber;
    private double balance;

    public AccountBasicInfo(Integer id, String accountNumber, double balance) {
        this(accountNumber, balance);
        this.id = id;
    }
    public AccountBasicInfo(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void setId(Integer id) { this.id = id; }
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}
