package com.prajwaldarekar.dailyexpenses02.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.activities.AccountsActivity;
import com.prajwaldarekar.dailyexpenses02.adapters.AccountsAdapter;
import com.prajwaldarekar.dailyexpenses02.adapters.TransactionAdapter;
import com.prajwaldarekar.dailyexpenses02.dialogs.AddAccountDialog;
import com.prajwaldarekar.dailyexpenses02.models.Account;
import com.prajwaldarekar.dailyexpenses02.models.Transaction;
import com.prajwaldarekar.dailyexpenses02.viewmodel.AccountViewModel;
import com.prajwaldarekar.dailyexpenses02.viewmodel.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private TextView tvTotalBalance, tvIncomeAmount, tvExpenseAmount;
    private RecyclerView rvTransactions, rvAccounts;

    private TransactionAdapter transactionAdapter;
    private final AccountsAdapter accountsAdapter = new AccountsAdapter(account ->
            Toast.makeText(getContext(), "Clicked: " + account.getName(), Toast.LENGTH_SHORT).show());

    private List<Transaction> transactionList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();

    private AccountViewModel accountViewModel;
    private TransactionViewModel transactionViewModel;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initViews(view);
        initViewModels();
        initRecyclerViews();
        observeViewModels();
        initButtonListeners(view);

        return view;
    }

    private void initViews(View view) {
        tvTotalBalance = view.findViewById(R.id.total_balance_amount);
        tvIncomeAmount = view.findViewById(R.id.tvIncomeAmount);
        tvExpenseAmount = view.findViewById(R.id.tvExpenseAmount);
        rvTransactions = view.findViewById(R.id.rv_transactions);
        rvAccounts = view.findViewById(R.id.rv_accounts);
    }

    private void initViewModels() {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
    }

    private void initRecyclerViews() {
        // Transactions RecyclerView
        transactionAdapter = new TransactionAdapter(transactionList, transaction -> {
            // Handle item click if needed
        });
        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransactions.setAdapter(transactionAdapter);

        // Accounts RecyclerView
        rvAccounts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvAccounts.setAdapter(accountsAdapter);
    }

    private void observeViewModels() {
        transactionViewModel.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            transactionList = transactions != null ? transactions : new ArrayList<>();
            transactionAdapter.setTransactionList(transactionList);
            updateSummary();
        });

        accountViewModel.getAllAccounts().observe(getViewLifecycleOwner(), accounts -> {
            accountList = accounts != null ? accounts : new ArrayList<>();
            accountsAdapter.setAccountList(accountList);
            updateSummary();
        });
    }

    private void updateSummary() {
        double totalBalance = 0, income = 0, expense = 0;

        for (Account account : accountList) {
            totalBalance += account.getBalance();
        }

        for (Transaction t : transactionList) {
            if ("Income".equalsIgnoreCase(t.getType())) {
                income += t.getAmount();
            } else if ("Expense".equalsIgnoreCase(t.getType())) {
                expense += Math.abs(t.getAmount());
            }
        }

        tvTotalBalance.setText(formatCurrency(totalBalance));
        tvIncomeAmount.setText(formatCurrency(income));
        tvExpenseAmount.setText(formatCurrency(expense));
    }

    private void initButtonListeners(View view) {
        view.findViewById(R.id.btn_view_all_accounts).setOnClickListener(v ->
                startActivity(new Intent(getContext(), AccountsActivity.class))
        );

        view.findViewById(R.id.btn_add_account).setOnClickListener(v ->
                AddAccountDialog.show(getContext(), account -> accountViewModel.insert(account))
        );

        view.findViewById(R.id.btn_view_all_transactions).setOnClickListener(v ->
                Toast.makeText(getContext(), "View All Transactions feature coming soon!", Toast.LENGTH_SHORT).show()
        );
    }

    private String formatCurrency(double amount) {
        return String.format("₹%.2f", amount);
    }
}