package com.example.a3dspacecalculator;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.jzy3d.chart.Chart;

import java.io.File;

public class example1 extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);

        final EditText numObjectsInput = findViewById(R.id.numObjectsInput);
        final EditText widthInput = findViewById(R.id.widthInput);
        final EditText heightInput = findViewById(R.id.heightInput);
        final EditText depthInput = findViewById(R.id.depthInput);
        final EditText areaWidthInput = findViewById(R.id.areaWidthInput);
        final EditText areaHeightInput = findViewById(R.id.areaHeightInput);
        final EditText areaDepthInput = findViewById(R.id.areaDepthInput);

        Button calculateButton = findViewById(R.id.calculateButton);
       final ImageView previewImageView = findViewById(R.id.previewImageView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get user input values
              //  int numObjects = Integer.parseInt(numObjectsInput.getText().toString());
             //   float width = Float.parseFloat(widthInput.getText().toString());
             //   float height = Float.parseFloat(heightInput.getText().toString());
             //   float depth = Float.parseFloat(depthInput.getText().toString());
                float xValues = Float.parseFloat(areaWidthInput.getText().toString());
                float yValues = Float.parseFloat(areaHeightInput.getText().toString());
                float zValues = Float.parseFloat(areaDepthInput.getText().toString());
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
                // Initialize Python
                if (!Python.isStarted()) {
                    Python.start(new AndroidPlatform(getApplicationContext()));
                }
                Python py = Python.getInstance();
                PyObject module = py.getModule("cubee"); // Replace with your module name
                PyObject result = module.callAttr("draw_3d_graph",xValues, yValues, zValues , "3D Graph Title");
                String base64Image = result.toJava(String.class);
               // String base64Image = result.toString();

                // Display the re-adjusted graph image
                previewImageView.setImageBitmap(ImageUtils.decodeBase64ToBitmap(base64Image));
                previewImageView .setVisibility(View.VISIBLE);
               // byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
                // Convert the Python result to a bitmap and display it in the ImageView
              //  byte[] bytes = result.toJava(byte[].class);
               // Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
              //  previewImageView.setImageBitmap(bitmap);
                //previewImageView.setVisibility(View.VISIBLE);
            }});

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



