package com.prajwaldarekar.dailyexpenses02.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

        EditText etName = dialogView.findViewById(R.id.et_account_name);
        EditText etType = dialogView.findViewById(R.id.et_account_type);
        EditText etBalance = dialogView.findViewById(R.id.et_account_balance);
        Button btnAdd = dialogView.findViewById(R.id.btn_add_account);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel_account); // Optional cancel button

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        // Focus and show keyboard
        etName.requestFocus();
        dialog.setOnShowListener(d -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(etName, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String type = etType.getText().toString().trim();
            String balanceStr = etBalance.getText().toString().trim();

            if (name.isEmpty() || type.isEmpty() || balanceStr.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double balance = Double.parseDouble(balanceStr);

                if (balance < 0) {
                    Toast.makeText(context, "Balance cannot be negative.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Account newAccount = new Account(name, type, balance);
                listener.onAccountAdded(newAccount);
                dialog.dismiss();
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Please enter a valid number for balance.", Toast.LENGTH_SHORT).show();
            }
        });

        if (btnCancel != null) {
            btnCancel.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }
}