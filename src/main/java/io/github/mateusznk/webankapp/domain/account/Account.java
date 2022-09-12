package io.github.mateusznk.webankapp.domain.account;

public class Account {
    private Integer id;
    private String accountNumber;
    private double balance;

    public Account(Integer id, String accountNumber, double balance) {
        this(accountNumber, balance);
        this.id = id;
    }

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}