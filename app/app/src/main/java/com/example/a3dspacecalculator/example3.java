package com.example.a3dspacecalculator;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// example2.java

// ... (your existing imports)
// ... (existing imports)

// ... (existing imports)

// ... (existing imports)

// example2.java

// ... (existing imports)

// ... (existing imports)

import android.os.AsyncTask;

// ... (existing imports)

// MainActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class example3 extends AppCompatActivity {

    private EditText editLength, editWidth, editHeight;
    private ImageView imageGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example3);

        editLength = findViewById(R.id.editLength);
        editWidth = findViewById(R.id.editWidth);
        editHeight = findViewById(R.id.editHeight);
        imageGraph = findViewById(R.id.imageGraph);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button btnGenerateGraph = findViewById(R.id.btnGenerateGraph);
        btnGenerateGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateGraph();
            }
        });

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    private void generateGraph() {
        double length = Double.parseDouble(editLength.getText().toString());
        double width = Double.parseDouble(editWidth.getText().toString());
        double height = Double.parseDouble(editHeight.getText().toString());

        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("plot_graphs"); // Replace with your Python script name

        PyObject result = pythonFile.callAttr("generate_graph", length, width, height);
        String base64Image = result.toString();

        // Convert base64 string to image and display in ImageView
        // (You need to implement this part based on your specific needs)
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        imageGraph.setImageBitmap(bitmap);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Respond to the action bar's Up/Home button
                onBackPressed(); // This will perform the same action as pressing the device's back button
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }

