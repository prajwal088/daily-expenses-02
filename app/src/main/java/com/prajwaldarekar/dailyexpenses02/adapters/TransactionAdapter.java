package com.prajwaldarekar.dailyexpenses02.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList = new ArrayList<>();
    private final OnTransactionClickListener clickListener;

    // Constructor accepts both the list of transactions and the click listener
    public TransactionAdapter(List<Transaction> transactionList, OnTransactionClickListener clickListener) {
        this.transactionList = transactionList != null ? transactionList : new ArrayList<>();
        this.clickListener = clickListener;
    }

    // Update the list of transactions
    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList != null ? transactionList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        final Transaction transaction = transactionList.get(position);

        // Set transaction details in views
        holder.amountTextView.setText(String.format("$%.2f", transaction.getAmount()));
        holder.descriptionTextView.setText(transaction.getDescription());
        holder.dateTextView.setText(transaction.getDate());
        holder.typeTextView.setText(transaction.getType());

        // Handle item click with the provided listener
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onTransactionClick(transaction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    // ViewHolder class for transaction items
    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        final TextView amountTextView;
        final TextView descriptionTextView;
        final TextView dateTextView;
        final TextView typeTextView;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.tv_amount);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            dateTextView = itemView.findViewById(R.id.tv_date);
            typeTextView = itemView.findViewById(R.id.tv_type);
        }
    }

    // Interface for handling transaction click events
    public interface OnTransactionClickListener {
        void onTransactionClick(Transaction transaction);
    }
}