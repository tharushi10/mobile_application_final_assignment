package com.example.mobile_application_final_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupscreen extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnSignUp;
    TextView tvToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupscreen);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnLogin);
        tvToLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
            userscreen newUser = new userscreen(username, password, email);

            dbRef.child(username).setValue(newUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, signinscreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        tvToLogin.setOnClickListener(v -> startActivity(new Intent(this, signinscreen.class)));
    }
}
