package com.example.incometaxcalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    TextView tvHistoryDisplay;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tvHistoryDisplay = findViewById(R.id.tvHistoryDisplay);
        db = new DatabaseHelper(this);

        // 1. Get current logged-in user
        SharedPreferences pref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String currentUser = pref.getString("username", "Guest");

        // 2. Fetch history from DB
        Cursor cursor = db.getUserHistory(currentUser);

        if (cursor.getCount() == 0) {
            tvHistoryDisplay.setText("No records found for " + currentUser);
        } else {
            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                // Column indices: 2=Type, 3=Input, 4=Result
                String type = cursor.getString(2);
                String input = cursor.getString(3);
                String result = cursor.getString(4);

                builder.append("Type: ").append(type).append("\n");
                builder.append("Data: ").append(input).append("\n");
                builder.append("Result: ").append(result).append("\n");
                builder.append("----------------------------\n");
            }
            tvHistoryDisplay.setText(builder.toString());
        }
        cursor.close();
    }
}