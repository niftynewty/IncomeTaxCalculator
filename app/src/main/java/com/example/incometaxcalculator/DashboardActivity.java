package com.example.incometaxcalculator; // Make sure this matches your project!

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnTax, btnEMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnTax = findViewById(R.id.btnTax);
        btnEMI = findViewById(R.id.btnEMI);

        // Logic to go to the Tax Calculator
        btnTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This assumes you have TaxCalculatorActivity created
                Intent intent = new Intent(DashboardActivity.this, TaxCalculatorActivity.class);
                startActivity(intent);
            }
        });

        // Logic for EMI (We can build this screen next)
        btnEMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, EmiActivity.class);
                startActivity(intent);
            }
        });
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // This clears the dashboard from the "back stack"
            }
        });
        Button btnHistory = findViewById(R.id.btnViewHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}