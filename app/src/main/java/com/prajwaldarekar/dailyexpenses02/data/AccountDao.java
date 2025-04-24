package com.prajwaldarekar.dailyexpenses02.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.prajwaldarekar.dailyexpenses02.models.Account;
import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Query("DELETE FROM accounts")
    void deleteAll();

    @Query("SELECT * FROM accounts ORDER BY id ASC")
    LiveData<List<Account>> getAllAccounts();
}

