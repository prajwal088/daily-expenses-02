package com.prajwaldarekar.dailyexpenses02.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.activities.AccountsActivity;
import com.prajwaldarekar.dailyexpenses02.adapters.TransactionAdapter;
import com.prajwaldarekar.dailyexpenses02.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private TextView tvTotalBalance, tvIncomeAmount, tvExpenseAmount;
    private RecyclerView rvTransactions;
    private TransactionAdapter transactionAdapter;
    private List<Transaction> transactionList;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initViews(view);
        setupTransactions();

        // View All Accounts button
        view.findViewById(R.id.btn_view_all_accounts).setOnClickListener(v ->
                startActivity(new Intent(getContext(), AccountsActivity.class))
        );

        // Add Account button - to be implemented
        view.findViewById(R.id.btn_add_account).setOnClickListener(v ->
                showPlaceholderToast("Add Account feature coming soon!")
        );

        // View All Transactions button - to be implemented
        view.findViewById(R.id.btn_view_all_transactions).setOnClickListener(v ->
                showPlaceholderToast("View All Transactions feature coming soon!")
        );

        return view;
    }

    private void initViews(View view) {
        tvTotalBalance = view.findViewById(R.id.total_balance_amount);
        tvIncomeAmount = view.findViewById(R.id.tvIncomeAmount);
        tvExpenseAmount = view.findViewById(R.id.tvExpenseAmount);
        rvTransactions = view.findViewById(R.id.rv_transactions);
    }

    private void setupTransactions() {
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction(1, "Salary", "2025-04-01", 50000.0, "Income", "Income"));
        transactionList.add(new Transaction(2, "Groceries", "2025-04-03", -1500.0, "Food", "Expense"));
        transactionList.add(new Transaction(3, "Electricity Bill", "2025-04-05", -1200.0, "Utilities", "Expense"));

        transactionAdapter = new TransactionAdapter(transactionList, transaction -> {
            // Handle transaction click (if needed)
        });

        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransactions.setAdapter(transactionAdapter);

        updateBalanceSummary();
    }

    private void updateBalanceSummary() {
        double income = 0, expense = 0;
        for (Transaction t : transactionList) {
            if ("Income".equalsIgnoreCase(t.getType())) {
                income += t.getAmount();
            } else if ("Expense".equalsIgnoreCase(t.getType())) {
                expense += Math.abs(t.getAmount());
            }
        }

        double total = income - expense;
        tvTotalBalance.setText(String.format("₹%.2f", total));
        tvIncomeAmount.setText(String.format("₹%.2f", income));
        tvExpenseAmount.setText(String.format("₹%.2f", expense));
    }

    private void showPlaceholderToast(String message) {
        if (getContext() != null) {
            android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}