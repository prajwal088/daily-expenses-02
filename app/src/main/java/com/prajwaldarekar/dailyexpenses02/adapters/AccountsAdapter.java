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

    // Set the list of accounts (called by the activity or fragment)
    public void setAccountList(List<Account> accounts) {
        this.accountList = accounts;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
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
        holder.bind(account, onAccountClickListener);  // Bind data to the view holder
    }

    @Override
    public int getItemCount() {
        return accountList.size();
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

            // Set up the click listener
            itemView.setOnClickListener(v -> listener.onAccountClick(account));
        }
    }
}
