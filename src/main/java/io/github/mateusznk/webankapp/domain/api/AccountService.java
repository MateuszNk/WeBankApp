package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.Account;
import io.github.mateusznk.webankapp.domain.account.AccountDao;

import java.util.Optional;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();

    public AccountBasicInfo readAccountData(int id) {
        Optional<Account> optionalAccount = accountDao.readAccountDataWithId(id);
        if (optionalAccount.isEmpty()) {
            System.out.println("Nie znaleziono");
        }
        String accountNumber = optionalAccount.get().getAccountNumber();
        double balance = optionalAccount.get().getBalance();
        return new AccountBasicInfo(accountNumber, balance);
    }
}
