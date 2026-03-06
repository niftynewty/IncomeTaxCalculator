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

public class EmiActivity extends AppCompatActivity {

    EditText etLoan, etRate, etTenure;
    Button btnCalculate;
    TextView tvResult;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);

        db = new DatabaseHelper(this);
        etLoan = findViewById(R.id.etLoanAmount);
        etRate = findViewById(R.id.etInterestRate);
        etTenure = findViewById(R.id.etTenure);
        btnCalculate = findViewById(R.id.btnCalculateEMI);
        tvResult = findViewById(R.id.tvEmiResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndSaveEMI();
            }
        });
    }

    private void calculateAndSaveEMI() {
        String pStr = etLoan.getText().toString();
        String rStr = etRate.getText().toString();
        String nStr = etTenure.getText().toString();

        if (pStr.isEmpty() || rStr.isEmpty() || nStr.isEmpty()) {
            tvResult.setText("Please fill all fields");
            return;
        }

        double p = Double.parseDouble(pStr);
        double r = Double.parseDouble(rStr) / 12 / 100;
        int n = Integer.parseInt(nStr);

        // EMI Formula
        double emi = (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
        String resultText = String.format("%.2f", emi);
        tvResult.setText("Monthly EMI: " + resultText);

        // --- SAVING TO HISTORY ---
        SharedPreferences pref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String loggedInUser = pref.getString("username", "Guest");

        boolean isSaved = db.insertHistory(
                loggedInUser,
                "EMI Loan",
                "Amt: " + pStr + ", Rate: " + rStr + "%, Months: " + nStr,
                "EMI: " + resultText
        );

        if(isSaved) {
            Toast.makeText(this, "EMI calculation saved", Toast.LENGTH_SHORT).show();
        }
    }
}