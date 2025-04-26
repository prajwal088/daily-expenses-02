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

    private AccountsAdapter accountsAdapter;
    private AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        // Initialize ViewModel
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        // Bind Views
        RecyclerView rvAccounts = findViewById(R.id.rv_accounts);
        Button btnRefresh = findViewById(R.id.btn_refresh);
        Button btnAddAccount = findViewById(R.id.btn_add_account);
        Button btnBack = findViewById(R.id.btn_back);

        // Setup RecyclerView and Adapter
        accountsAdapter = new AccountsAdapter(account -> {
            // Optional: handle account item click (view/edit)
        });
        rvAccounts.setLayoutManager(new LinearLayoutManager(this));
        rvAccounts.setAdapter(accountsAdapter);

        // Observe LiveData for account list
        accountViewModel.getAllAccounts().observe(this, accounts -> {
            accountsAdapter.setAccountList(accounts);
        });

        // Refresh accounts data
        btnRefresh.setOnClickListener(v -> {
            accountViewModel.refreshData();
        });

        // Show AddAccountDialog and handle result
        btnAddAccount.setOnClickListener(v -> {
            AddAccountDialog.show(this, account -> {
                accountViewModel.insert(account);
            });
        });

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }
}