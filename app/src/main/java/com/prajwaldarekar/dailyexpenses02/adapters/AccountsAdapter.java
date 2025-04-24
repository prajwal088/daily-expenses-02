package com.prajwaldarekar.dailyexpenses02.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.models.Account;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder> {
    private final List<Account> accountList;

    public interface OnAccountClickListener {
        void onAccountClick(Account account);
    }

    public AccountsAdapter(List<Account> accountList) {
        this.accountList = accountList;
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
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView accountName, tvAccountType, accountBalance;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.tv_account_name);
            tvAccountType = itemView.findViewById(R.id.tv_account_type);
            accountBalance = itemView.findViewById(R.id.tv_account_balance);
        }

        public void bind(Account account, OnAccountClickListener listener) {
            accountName.setText(account.getName());
            tvAccountType.setText(account.getType());
            accountBalance.setText(String.format("$%.2f", account.getBalance()));
            itemView.setOnClickListener(v -> listener.onAccountClick(account));
        }
    }
}
