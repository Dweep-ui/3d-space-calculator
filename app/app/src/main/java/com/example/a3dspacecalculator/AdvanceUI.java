package com.example.a3dspacecalculator;


import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;

public class AdvanceUI extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private CallbackManager callbackManager;
    private Button fbButton;
    private static final int RC_SIGN_IN = 9001;
    @SuppressWarnings("deprecation")
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullNameEditText = findViewById(R.id.signup_name);
        emailEditText = findViewById(R.id.signup_email);
        passwordEditText = findViewById(R.id.signup_password);
        progressBar = findViewById(R.id.progress_bar);

        callbackManager = CallbackManager.Factory.create();
        //noinspection deprecation
        FacebookSdk.sdkInitialize(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //noinspection deprecation
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Button googleSignInButton = findViewById(R.id.signup_google_button);
        googleSignInButton.setOnClickListener(view -> signInWithGoogle());

        fbButton = findViewById(R.id.signup_facebook_button);
        fbButton.setOnClickListener(view -> signInWithFacebook());

        Button signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(view -> signUpWithEmailAndPassword());

        TextView loginLink = findViewById(R.id.login_link);
        loginLink.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), login.class)));
    }

    private void signUpWithEmailAndPassword() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        final String fullName = fullNameEditText.getText().toString().trim();

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
                            String userId = user.getUid();

                            // Save user data to Firebase Realtime Database
                            User userData = new User(fullName, email); // Create a User class as needed
                            mDatabase.child("users").child(userId).setValue(userData);

                            // Navigate to the next activity with the side navigation drawer
                            Intent intent = new Intent(AdvanceUI.this, UserProfileActivity.class);
                            startActivity(intent);
                            // Optional: Close the signup activity
                            finish();
                        } else {
                            Toast.makeText(AdvanceUI.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //noinspection deprecation
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(AdvanceUI.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(AdvanceUI.this, "Sign up Successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdvanceUI.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

