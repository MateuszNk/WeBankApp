package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.Account;
import io.github.mateusznk.webankapp.domain.account.AccountDao;

import java.util.Optional;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();

    public Optional<AccountBasicInfo> readAccountData(int id) {
        Optional<Account> optionalAccount = accountDao.readAccountDataWithId(id);
        if (optionalAccount.isEmpty()) {
            throw new UnknownError();
        }

        String accountNumber = optionalAccount.get().getAccountNumber();
        double balance = optionalAccount.get().getBalance();
        return Optional.of(new AccountBasicInfo(accountNumber, balance));
    }
}
