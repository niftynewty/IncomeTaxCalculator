package com.example.incometaxcalculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // 1. Declare variables
    EditText username, password, confirmPassword;
    Button registerBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 2. Initialize
        db = new DatabaseHelper(this);
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        confirmPassword = findViewById(R.id.reg_confirm_password);
        registerBtn = findViewById(R.id.registerBtn);

        // 3. Logic for the Sign Up button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String confPass = confirmPassword.getText().toString();

                if(user.equals("") || pass.equals("") || confPass.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(confPass)) {
                        boolean inserted = db.insertUser(user, pass);
                        if(inserted) {
                            Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            finish(); // This closes the registration screen and goes back to login
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}