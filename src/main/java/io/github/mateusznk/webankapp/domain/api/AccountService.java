package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.Account;
import io.github.mateusznk.webankapp.domain.account.AccountDao;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;

import java.util.Optional;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();

    public Optional<AccountBasicInfo> readAccountData(int id) {
        Optional<Account> optionalAccount = accountDao.readAccountDataWithId(id);
        if (optionalAccount.isEmpty()) {
            writeExceptionsToFile.unusualErrorLog(Thread.currentThread().getStackTrace()[1].getLineNumber(),
                    getClass().getName());
            throw new RuntimeException();
        }

        String accountNumber = optionalAccount.get().getAccountNumber();
        double balance = optionalAccount.get().getBalance();
        return Optional.of(new AccountBasicInfo(accountNumber, balance));
    }
}
