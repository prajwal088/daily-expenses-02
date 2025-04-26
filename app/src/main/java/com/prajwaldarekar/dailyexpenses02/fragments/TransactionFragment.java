package com.prajwaldarekar.dailyexpenses02.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.adapters.TransactionAdapter;
import com.prajwaldarekar.dailyexpenses02.models.Transaction;
import com.prajwaldarekar.dailyexpenses02.viewmodel.TransactionViewModel;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {

    private RecyclerView rvTransactions;
    private TransactionAdapter transactionAdapter;
    private TextView emptyMessage;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

        // Initialize RecyclerView
        rvTransactions = rootView.findViewById(R.id.rv_transactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize TransactionAdapter with empty list and a click listener
        transactionAdapter = new TransactionAdapter(new ArrayList<>(), transaction -> {
            // Handle transaction click
            // Example: show a toast with the transaction description
            Toast.makeText(getContext(), "Transaction clicked: " + transaction.getDescription(), Toast.LENGTH_SHORT).show();
        });
        rvTransactions.setAdapter(transactionAdapter);

        // Initialize ViewModel
        TransactionViewModel transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Initialize UI components
        Button btnNewTransaction = rootView.findViewById(R.id.btn_new_transaction);
        Button btnAddTransaction = rootView.findViewById(R.id.btn_add_transaction);
        emptyMessage = rootView.findViewById(R.id.empty_message);

        // Observe LiveData for transaction list
        transactionViewModel.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            if (transactions.isEmpty()) {
                // Show empty state if no transactions exist
                rvTransactions.setVisibility(View.GONE);
                emptyMessage.setVisibility(View.VISIBLE);
            } else {
                // Show transactions in RecyclerView
                rvTransactions.setVisibility(View.VISIBLE);
                emptyMessage.setVisibility(View.GONE);
                // Update the adapter with the new list of transactions
                transactionAdapter.setTransactionList(transactions);
            }
        });

        // Button click to navigate to add new transaction screen or show dialog
        btnNewTransaction.setOnClickListener(v -> {
            // Here you could start an activity or show a dialog to add a new transaction
            // Example: Open Add Transaction Dialog
            Toast.makeText(getContext(), "Add New Transaction Clicked", Toast.LENGTH_SHORT).show();
        });

        // Button to add a transaction directly (used for empty state)
        btnAddTransaction.setOnClickListener(v -> {
            // Handle Add Transaction action
            Toast.makeText(getContext(), "Add New Transaction Clicked", Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }
}
