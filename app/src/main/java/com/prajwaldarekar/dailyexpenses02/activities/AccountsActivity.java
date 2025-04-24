package com.prajwaldarekar.dailyexpenses02.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.adapters.AccountsAdapter;
import com.prajwaldarekar.dailyexpenses02.dialogs.AddAccountDialog;
import com.prajwaldarekar.dailyexpenses02.models.Account;
import com.prajwaldarekar.dailyexpenses02.viewmodel.AccountViewModel;

public class AccountsActivity extends AppCompatActivity {

    private RecyclerView rvAccounts;
    private AccountsAdapter accountsAdapter;
    private Button btnRefresh, btnAddAccount, btnBack;

    private AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        // Initialize Room ViewModel
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        // Bind UI components
        rvAccounts = findViewById(R.id.rv_accounts);
        btnRefresh = findViewById(R.id.btn_refresh);
        btnAddAccount = findViewById(R.id.btn_add_account);
        btnBack = findViewById(R.id.btn_back);

        // Setup RecyclerView
        accountsAdapter = new AccountsAdapter();
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));
        rvAccounts.setAdapter(accountsAdapter);

        // Observe data
        accountViewModel.getAllAccounts().observe(this, accounts -> {
            accountsAdapter.setAccountList(accounts);
        });

        // Refresh button
        btnRefresh.setOnClickListener(v -> accountViewModel.refreshData());

        // Add Account
        btnAddAccount.setOnClickListener(v -> AddAccountDialog.show(this, (name, type, balance) -> {
            Account newAccount = new Account(name, type, balance);
            accountViewModel.insert(newAccount);
        }));

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }
}