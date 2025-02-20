package com.example.a3dspacecalculator;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    CardView apple, ball, cat, dog, egg;
    private ImageView profileImageView;
    private TextView usernameTextView, emailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ball = findViewById(R.id.nottt);
        ball.setOnClickListener(this);

        egg = findViewById(R.id.doytb);
        egg.setOnClickListener(this);

        // Initialize your profileImageView, usernameTextView, and emailTextView here
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_user_profile, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_logout) {
                    // Handle the logout action here
                    FirebaseAuth.getInstance().signOut();
                    // Redirect to the login or another activity after logout
                    Intent intent = new Intent(UserProfileActivity.this, login.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doytb:
                Toast.makeText(this, "3D Space Calculator", Toast.LENGTH_LONG).show();
                Intent o = new Intent(UserProfileActivity.this, conversion.class);
                startActivity(o);
                break;
            case R.id.nottt:
                Toast.makeText(this, "3D Space Calculator", Toast.LENGTH_LONG).show();
                Intent k = new Intent(UserProfileActivity.this, example6.class);
                startActivity(k);
                break;
        }

        // Get the current user from Firebase Authentication
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

       //if (user != null) {
            // Set user's name and email
          //  usernameTextView.setText(user.getDisplayName());
            //emailTextView.setText(user.getEmail());

            // Load the user's profile picture if available
            //if (user.getPhotoUrl() != null) {
                //String photoUrl = user.getPhotoUrl().toString();
               // Picasso.get().load(photoUrl).into(profileImageView);
            //} else {
                // Set a default profile picture or handle the case where the user doesn't have one.
              //  profileImageView.setImageResource(R.drawable.gd);
           //}
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            // Handle the logout action here
            FirebaseAuth.getInstance().signOut();
            // Redirect to the login or another activity after logout
            Intent intent = new Intent(UserProfileActivity.this, login.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

