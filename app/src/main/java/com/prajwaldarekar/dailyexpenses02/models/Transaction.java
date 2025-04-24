package com.prajwaldarekar.dailyexpenses02.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "amount")
    private double amount;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    // Constructor used when inserting data (id is auto-generated)
    public Transaction(double amount, @NonNull String description, @NonNull String date, @NonNull String type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    // Constructor used when reading data from the database (with id)
    @Ignore
    public Transaction(int id, double amount, @NonNull String description, @NonNull String date, @NonNull String type) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    @NonNull
    public String getDescription() { return description; }
    public void setDescription(@NonNull String description) { this.description = description; }

    @NonNull
    public String getDate() { return date; }
    public void setDate(@NonNull String date) { this.date = date; }

    @NonNull
    public String getType() { return type; }
    public void setType(@NonNull String type) { this.type = type; }
}
