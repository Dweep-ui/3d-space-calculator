package com.example.a3dspacecalculator;

import android.content.Context;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class signup extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText;
    private ProgressBar progressBar;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullNameEditText = findViewById(R.id.signup_name);
        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_password);
        progressBar = findViewById(R.id.progress_bar);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize Facebook CallbackManager
        callbackManager = CallbackManager.Factory.create();
        TextView signUpTextView = findViewById(R.id.login_link);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the signup activity when the "Sign up here" TextView is clicked
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
        Button signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    signUpWithEmailAndPassword();
                } else {
                    Toast.makeText(signup.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button facebookButton = findViewById(R.id.signup_facebook_button);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    loginWithFacebook();
                } else {
                    Toast.makeText(signup.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Other initialization code...
    }

    private void signUpWithEmailAndPassword() {
        final String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                // Save user data to Firebase Realtime Database
                                mDatabase.child("users").child(userId).child("fullName").setValue(fullName);
                                mDatabase.child("users").child(userId).child("email").setValue(user.getEmail());
                                // Navigate to the next activity
                                Intent intent = new Intent(signup.this, UserProfileActivity.class);
                                startActivity(intent);
                                finish(); // Optional: Close the sign-up activity
                            }
                        } else {
                            Toast.makeText(signup.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(signup.this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(signup.this, "Facebook login canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(signup.this, "Facebook login error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String email = object.getString("email");
                    String fullName = object.getString("name");

                    // You can do something with this data (e.g., save to Firebase)
                    Toast.makeText(signup.this, "Facebook login successful", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(signup.this, "Failed to get Facebook user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    // Check if network is available
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    // Override onActivityResult method to handle Facebook login result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

