package com.prajwaldarekar.dailyexpenses02.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.prajwaldarekar.dailyexpenses02.data.AccountDao;
import com.prajwaldarekar.dailyexpenses02.data.AppDatabase;
import com.prajwaldarekar.dailyexpenses02.models.Account;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountRepository {
    private final AccountDao accountDao;
    private final LiveData<List<Account>> allAccounts;
    private final ExecutorService executorService;

    public AccountRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        accountDao = db.accountDao();
        allAccounts = accountDao.getAllAccounts();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public void insert(Account account) {
        executorService.execute(() -> accountDao.insert(account));
    }

    public void update(Account account) {
        executorService.execute(() -> accountDao.update(account));
    }

    public void delete(Account account) {
        executorService.execute(() -> accountDao.delete(account));
    }

    public void deleteAll() {
        executorService.execute(accountDao::deleteAll);
    }
}