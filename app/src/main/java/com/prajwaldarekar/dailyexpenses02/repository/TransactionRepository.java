package com.prajwaldarekar.dailyexpenses02.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.prajwaldarekar.dailyexpenses02.data.TransactionDao;
import com.prajwaldarekar.dailyexpenses02.data.TransactionDatabase;
import com.prajwaldarekar.dailyexpenses02.models.Transaction;

import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;

    public TransactionRepository(Application application) {
        TransactionDatabase db = TransactionDatabase.getInstance(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public void insert(Transaction transaction) {
        TransactionDatabase.getDatabaseWriteExecutor().execute(() -> transactionDao.insert(transaction));
    }

    public void update(Transaction transaction) {
        TransactionDatabase.getDatabaseWriteExecutor().execute(() -> transactionDao.update(transaction));
    }

    public void delete(Transaction transaction) {
        TransactionDatabase.getDatabaseWriteExecutor().execute(() -> transactionDao.delete(transaction));
    }

    public void deleteAllTransactions() {
        TransactionDatabase.getDatabaseWriteExecutor().execute(() -> transactionDao.deleteAllTransactions());
    }
}