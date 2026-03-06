package com.example.incometaxcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TaxCalculatorActivity extends AppCompatActivity {

    EditText etAnnualIncome;
    Button btnCalculateTax;
    TextView tvTaxResult;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_calculator);

        db = new DatabaseHelper(this);
        etAnnualIncome = findViewById(R.id.etAnnualIncome);
        btnCalculateTax = findViewById(R.id.btnCalculateTax);
        tvTaxResult = findViewById(R.id.tvTaxResult);

        btnCalculateTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndSave();
            }
        });
    }

    private void calculateAndSave() {
        String input = etAnnualIncome.getText().toString();

        if(input.isEmpty()) {
            tvTaxResult.setText("Please enter income");
            return;
        }

        double income = Double.parseDouble(input);
        double tax = 0;

        // Your Tax Logic
        if(income <= 100000) {
            tax = income * 0.10;
        } else {
            tax = (100000 * 0.10) + ((income - 100000) * 0.20);
        }

        String resultText = String.format("%.2f", tax);
        tvTaxResult.setText("Total Tax: " + resultText);

        // --- SAVING TO HISTORY ---
        // 1. Get the username of the person logged in
        SharedPreferences pref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String loggedInUser = pref.getString("username", "Guest");

        // 2. Insert into Database
        boolean isSaved = db.insertHistory(
                loggedInUser,
                "Income Tax",
                "Income: " + input,
                "Tax: " + resultText
        );

        if(isSaved) {
            Toast.makeText(this, "Calculation saved to history", Toast.LENGTH_SHORT).show();
        }
    }
}