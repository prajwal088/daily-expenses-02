package com.prajwaldarekar.dailyexpenses02.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.prajwaldarekar.dailyexpenses02.models.Transaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transaction.class}, version = 1, exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase {

    private static volatile TransactionDatabase INSTANCE;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);  // This creates the thread pool for background operations

    public abstract TransactionDao transactionDao();

    // Singleton pattern to ensure only one instance of the database
    public static TransactionDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TransactionDatabase.class, "transaction_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }
}