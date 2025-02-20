package com.example.a3dspacecalculator;

// MainActivity.java


import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import com.chaquo.python.Python;
//import com.chaquo.python.android.AndroidPlatform;

public class example2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        final EditText areaWidthInput = findViewById(R.id.areaWidthInput);
        final EditText areaHeightInput = findViewById(R.id.areaHeightInput);
        final EditText areaDepthInput = findViewById(R.id.areaDepthInput);
        final LinearLayout container = findViewById(R.id.container);
        final EditText editTextQuantity = findViewById(R.id.editTextQuantity);
        Button btnProcess = findViewById(R.id.btnProcess);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container.removeAllViews(); // Clear existing views

                // Get the quantity of objects
                int quantity = Integer.parseInt(editTextQuantity.getText().toString());

                // Create input boxes for each object's details dynamically
                for (int i = 0; i < quantity; i++) {
                    LinearLayout innerLayout = new LinearLayout(example2.this);
                    innerLayout.setOrientation(LinearLayout.VERTICAL);
                    innerLayout.setGravity(Gravity.CENTER);

                    TextView textView = new TextView(example2.this);
                    textView.setText("Object " + (i + 1));
                    innerLayout.addView(textView);

                    // Input box for object height
                    EditText editTextHeight = new EditText(example2.this);
                    editTextHeight.setHint("Height");
                    innerLayout.addView(editTextHeight);

                    // Input box for object width
                    EditText editTextWidth = new EditText(example2.this);
                    editTextWidth.setHint("Width");
                    innerLayout.addView(editTextWidth);

                    // Input box for object length
                    EditText editTextLength = new EditText(example2.this);
                    editTextLength.setHint("Length");
                    innerLayout.addView(editTextLength);

                    container.addView(innerLayout);

                }
                      }
        });}


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
    }}