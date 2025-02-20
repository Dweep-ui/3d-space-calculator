package com.example.a3dspacecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity3 extends AppCompatActivity{

    private Button[] buttons;
    private TextView statusTextView;
    private DatabaseReference gameRef;
    private ValueEventListener gameListener;
    private String playerId;
    private boolean isPlayer1Turn = true;
    private boolean isGameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Initialize Firebase Realtime Database reference

    }
}

