package com.example.a3dspacecalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


public class example5 extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example5);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        final Python py = Python.getInstance();
        final PyObject pyObject = py.getModule("plot");

        final EditText objectQuantityEditText = findViewById(R.id.objectQuantityEditText);
        final LinearLayout objectLinearLayout = findViewById(R.id.objectLinearLayout);
        final EditText areaLengthEditText = findViewById(R.id.areaLengthEditText);
        final EditText areaWidthEditText = findViewById(R.id.areaWidthEditText);
        final EditText areaHeightEditText = findViewById(R.id.areaHeightEditText);
        final Button generateGraphButton = findViewById(R.id.generateGraphButton);
        final ImageView graphImageView = findViewById(R.id.graphImageView);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        generateGraphButton.setOnClickListener(v -> {
            // Reset previous graph and messages
            graphImageView.setVisibility(View.GONE);
            Toast.makeText(example5.this, "Generating graph...", Toast.LENGTH_SHORT).show();

            try {
                int objectQuantity = Integer.parseInt(objectQuantityEditText.getText().toString());
                double areaLength = Double.parseDouble(areaLengthEditText.getText().toString());
                double areaWidth = Double.parseDouble(areaWidthEditText.getText().toString());
                double areaHeight = Double.parseDouble(areaHeightEditText.getText().toString());

                // Collect object dimensions dynamically
                StringBuilder objectsDimensions = new StringBuilder();

                for (int i = 0; i < objectQuantity; i++) {
                    LinearLayout innerLayout = new LinearLayout(example5.this);
                    innerLayout.setOrientation(LinearLayout.VERTICAL);
                    innerLayout.setGravity(Gravity.CENTER);

                    TextView textView = new TextView(example5.this);
                    textView.setText("Object " + (i + 1));
                    innerLayout.addView(textView);

                    // Input box for object height
                    EditText objectLengthEditText = new EditText(example5.this);
                    objectLengthEditText.setTag("objectLength" + i);
                    objectLengthEditText.setHint("Length");
                    innerLayout.addView(objectLengthEditText);

                    // Input box for object width
                    EditText objectWidthEditText = new EditText(example5.this);
                    objectWidthEditText.setTag("objectWidth" + i);
                    objectWidthEditText.setHint("Width");
                    innerLayout.addView(objectWidthEditText);

                    // Input box for object length
                    EditText objectHeightEditText = new EditText(example5.this);
                    objectHeightEditText.setTag("objectHeight" + i);
                    objectHeightEditText.setHint("Height");
                    innerLayout.addView(objectHeightEditText);

                    objectLinearLayout.addView(innerLayout);

                    // Use findViewWithTag with the correct tags
                    EditText objectLength = objectLinearLayout.findViewWithTag("objectLength" + i);
                    EditText objectWidth = objectLinearLayout.findViewWithTag("objectWidth" + i);
                    EditText objectHeight = objectLinearLayout.findViewWithTag("objectHeight" + i);

                    // Check if the dynamically added views are null
                    if (objectLength != null && objectWidth != null && objectHeight != null) {
                        double length = 0.0;
                        double width = 0.0;
                        double height = 0.0;

                        String lengthStr = objectLength.getText().toString();
                        String widthStr = objectWidth.getText().toString();
                        String heightStr = objectHeight.getText().toString();

                        if (!lengthStr.isEmpty()) {
                            length = Double.parseDouble(lengthStr);
                        }

                        if (!widthStr.isEmpty()) {
                            width = Double.parseDouble(widthStr);
                        }

                        if (!heightStr.isEmpty()) {
                            height = Double.parseDouble(heightStr);
                        }

                        objectsDimensions.append(length).append(",").append(width).append(",").append(height).append(";");
                    }
                }

                // Call Python function to generate graph
                PyObject result = pyObject.callAttr("generate_graph", areaLength, areaWidth, areaHeight, objectsDimensions.toString());

                // Get the base64 encoded image from Python
                String base64Image = result.toString();

                // Display the graph image
                graphImageView.setImageBitmap(ImageUtils.decodeBase64ToBitmap(base64Image));
                graphImageView.setVisibility(View.VISIBLE);
                Toast.makeText(example5.this, "Graph generated successfully", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                // Handle invalid number format in input fields
                Toast.makeText(example5.this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Handle other exceptions
                Toast.makeText(example5.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } );

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
