package com.prajwaldarekar.dailyexpenses02.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.adapters.AccountsAdapter;
import com.prajwaldarekar.dailyexpenses02.dialogs.AddAccountDialog;
import com.prajwaldarekar.dailyexpenses02.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountsActivity extends AppCompatActivity {

    private RecyclerView rvAccounts;
    private AccountsAdapter accountsAdapter;
    private List<Account> accountList;
    private Button btnRefresh, btnAddAccount, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        // Bind UI components
        rvAccounts = findViewById(R.id.rv_accounts);
        btnRefresh = findViewById(R.id.btn_refresh);
        btnAddAccount = findViewById(R.id.btn_add_account);
        btnBack = findViewById(R.id.btn_back);

        // Set up RecyclerView
        accountList = new ArrayList<>();
        accountsAdapter = new AccountsAdapter(accountList);
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));
        rvAccounts.setAdapter(accountsAdapter);

        // Load initial data
        loadAccounts();

        // Refresh button
        btnRefresh.setOnClickListener(v -> loadAccounts());

        // Add Account button
        btnAddAccount.setOnClickListener(v -> openAddAccountDialog());

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadAccounts() {
        // TODO: Replace with actual data loading (from DB/API)
        accountList.clear();
        accountList.add(new Account(1, "Cash", "Personal", 1500.00));
        accountList.add(new Account(2, "Bank Account", "Savings", 8700.00));
        accountList.add(new Account(3, "Wallet", "Digital", 120.50));
        accountsAdapter.notifyDataSetChanged();
    }

    private void openAddAccountDialog() {
        AddAccountDialog.show(this, (id, name, type, balance) -> {
            accountList.add(new Account(id, name, type, balance));
            accountsAdapter.notifyDataSetChanged();
        });
    }
}

