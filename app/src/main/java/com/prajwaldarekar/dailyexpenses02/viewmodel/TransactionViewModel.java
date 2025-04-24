package com.prajwaldarekar.dailyexpenses02.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.prajwaldarekar.dailyexpenses02.models.Transaction;
import com.prajwaldarekar.dailyexpenses02.repository.TransactionRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;
    private LiveData<List<Transaction>> allTransactions;

    // Executor service for background tasks (equivalent of coroutines in Java)
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public TransactionViewModel(Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        allTransactions = transactionRepository.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public void insert(Transaction transaction) {
        executorService.execute(() -> {
            transactionRepository.insert(transaction);
        });
    }

    public void update(Transaction transaction) {
        executorService.execute(() -> {
            transactionRepository.update(transaction);
        });
    }

    public void delete(Transaction transaction) {
        executorService.execute(() -> {
            transactionRepository.delete(transaction);
        });
    }

    public void deleteAllTransactions() {
        executorService.execute(() -> {
            transactionRepository.deleteAllTransactions();
        });
    }
}
