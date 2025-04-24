package com.prajwaldarekar.dailyexpenses02.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.prajwaldarekar.dailyexpenses02.models.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    // Insert a new transaction into the database
    @Insert
    void insert(Transaction transaction);

    // Update an existing transaction in the database
    @Update
    void update(Transaction transaction);

    // Delete a specific transaction from the database
    @Delete
    void delete(Transaction transaction);

    // Delete all transactions from the database
    @Query("DELETE FROM transactions")
    void deleteAllTransactions();

    // Get all transactions ordered by date (or any specific order)
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    LiveData<List<Transaction>> getAllTransactions();
}