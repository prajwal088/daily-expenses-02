package com.prajwaldarekar.dailyexpenses02.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder> {

    private List<Account> accountList = new ArrayList<>();
    private final OnAccountClickListener onAccountClickListener;

    // Interface for handling account item clicks
    public interface OnAccountClickListener {
        void onAccountClick(Account account);
    }

    // Constructor to set the listener
    public AccountsAdapter(OnAccountClickListener onAccountClickListener) {
        this.onAccountClickListener = onAccountClickListener;
    }

    // Public method to update account list
    public void setAccountList(List<Account> accounts) {
        if (accounts != null) {
            this.accountList = accounts;
        } else {
            this.accountList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    // Optional: get account at a specific position (useful for swipe/edit)
    public Account getAccountAt(int position) {
        return accountList.get(position);
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.bind(account, onAccountClickListener);
    }

    @Override
    public int getItemCount() {
        return accountList != null ? accountList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        // If you plan to show different view types in future
        return super.getItemViewType(position);
    }

    // ViewHolder class to bind the account data to the item view
    static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView accountName, tvAccountType, accountBalance;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.tv_account_name);
            tvAccountType = itemView.findViewById(R.id.tv_account_type);
            accountBalance = itemView.findViewById(R.id.tv_account_balance);
        }

        // Bind account data to views and set click listener
        public void bind(Account account, OnAccountClickListener listener) {
            accountName.setText(account.getName());
            tvAccountType.setText(account.getType());
            accountBalance.setText(String.format("₹%.2f", account.getBalance()));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAccountClick(account);
                }
            });
        }
    }
}