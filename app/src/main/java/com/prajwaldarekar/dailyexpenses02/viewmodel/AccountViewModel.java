package com.prajwaldarekar.dailyexpenses02.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.prajwaldarekar.dailyexpenses02.models.Account;
import com.prajwaldarekar.dailyexpenses02.repository.AccountRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private final AccountRepository repository;
    private final LiveData<List<Account>> allAccounts;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountRepository(application);
        allAccounts = repository.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public void insert(Account account) {
        repository.insert(account);
    }

    public void update(Account account) {
        repository.update(account);
    }

    public void delete(Account account) {
        repository.delete(account);
    }

    public void refreshData() {
        // TODO: Implement logic to refresh data from source if needed
    }
}