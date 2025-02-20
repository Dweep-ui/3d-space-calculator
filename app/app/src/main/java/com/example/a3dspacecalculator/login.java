package com.example.a3dspacecalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class login extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
// Add this code in your LoginActivity's onCreate method
        TextView signUpTextView = findViewById(R.id.signup_text);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the signup activity when the "Sign up here" TextView is clicked
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");

                    return;
                }

                if (password.length() < 10) {
                    passwordEditText.setError("Password must be 10 character");
                    int passwordLength=password.length();
                    StringBuilder displaypassword=new StringBuilder();
                    for(int i=0;i<passwordLength;i++){
                        displaypassword.append("*");
                    }
                    return;
                }
                progressBar.setVisibility(View.VISIBLE); // Show the ProgressBar

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // Hide the ProgressBar
                                Intent i =new Intent(login.this ,UserProfileActivity.class);
                                startActivity(i);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    // Login successful, do something
                                    showToast("Login successfull");
                                    finish();
                                } else {
                                    // Login failed, handle the error
                                    showToast("Login failed: " + task.getException().getMessage());
                                }
                            }
                        });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
