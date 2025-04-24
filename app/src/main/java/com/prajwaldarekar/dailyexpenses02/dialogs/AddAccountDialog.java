package com.prajwaldarekar.dailyexpenses02.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.models.Account;

public class AddAccountDialog {

    public interface OnAccountAddedListener {
        void onAccountAdded(Account account);
    }

    public static void show(Context context, OnAccountAddedListener listener) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_account, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        EditText etName = dialogView.findViewById(R.id.et_account_name);
        EditText etType = dialogView.findViewById(R.id.et_account_type);
        EditText etBalance = dialogView.findViewById(R.id.et_account_balance);
        Button btnAdd = dialogView.findViewById(R.id.btn_add_account);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String type = etType.getText().toString().trim();
            String balanceStr = etBalance.getText().toString().trim();

            if (name.isEmpty() || type.isEmpty() || balanceStr.isEmpty()) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double balance = Double.parseDouble(balanceStr);
                Account newAccount = new Account(name, type, balance);
                listener.onAccountAdded(newAccount);
                dialog.dismiss();
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Invalid balance format", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
