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

public class example6 extends AppCompatActivity {
    private LinearLayout objectLinearLayout;
    private ImageView graphImageView;
    private Python py;
    private PyObject pyObject;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example6);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        py = Python.getInstance();
        pyObject = py.getModule("new1");

        objectLinearLayout = findViewById(R.id.objectLinearLayout);
        graphImageView = findViewById(R.id.graphImageView);

        Button generateGraphButton = findViewById(R.id.generateGraphButton);
        Button reAdjustButton = findViewById(R.id.reAdjustButton);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        generateGraphButton.setOnClickListener(v -> generateGraph());
        reAdjustButton.setOnClickListener(v -> reAdjustGraph());
    }

    private void generateGraph() {
        // Reset previous graph and messages
        graphImageView.setVisibility(View.GONE);
        Toast.makeText(example6.this, "Generating graph...", Toast.LENGTH_SHORT).show();

        try {
            int objectQuantity = Integer.parseInt(((EditText) findViewById(R.id.objectQuantityEditText)).getText().toString());
            double areaLength = Double.parseDouble(((EditText) findViewById(R.id.areaLengthEditText)).getText().toString());
            double areaWidth = Double.parseDouble(((EditText) findViewById(R.id.areaWidthEditText)).getText().toString());
            double areaHeight = Double.parseDouble(((EditText) findViewById(R.id.areaHeightEditText)).getText().toString());

            // Collect object dimensions and names dynamically
            StringBuilder objectsDimensions = new StringBuilder();
            StringBuilder objectNames = new StringBuilder();

            for (int i = 0; i < objectQuantity; i++) {
                LinearLayout innerLayout = new LinearLayout(example6.this);
                innerLayout.setOrientation(LinearLayout.VERTICAL);
                innerLayout.setGravity(Gravity.CENTER);

                TextView textView = new TextView(example6.this);
                textView.setText("Object " + (i + 1));
                innerLayout.addView(textView);

                // Input box for object name
                EditText objectNameEditText = new EditText(example6.this);
                objectNameEditText.setTag("objectName" + i);
                objectNameEditText.setHint("Object Name");
                innerLayout.addView(objectNameEditText);

                // Input box for object height
                EditText objectLengthEditText = new EditText(example6.this);
                objectLengthEditText.setTag("objectLength" + i);
                objectLengthEditText.setHint("Length");
                innerLayout.addView(objectLengthEditText);

                // Input box for object width
                EditText objectWidthEditText = new EditText(example6.this);
                objectWidthEditText.setTag("objectWidth" + i);
                objectWidthEditText.setHint("Width");
                innerLayout.addView(objectWidthEditText);

                // Input box for object length
                EditText objectHeightEditText = new EditText(example6.this);
                objectHeightEditText.setTag("objectHeight" + i);
                objectHeightEditText.setHint("Height");
                innerLayout.addView(objectHeightEditText);

                objectLinearLayout.addView(innerLayout);

                // Use findViewWithTag with the correct tags
                EditText objectName = objectLinearLayout.findViewWithTag("objectName" + i);
                EditText objectLength = objectLinearLayout.findViewWithTag("objectLength" + i);
                EditText objectWidth = objectLinearLayout.findViewWithTag("objectWidth" + i);
                EditText objectHeight = objectLinearLayout.findViewWithTag("objectHeight" + i);

                // Check if the dynamically added views are null
                if (objectName != null && objectLength != null && objectWidth != null && objectHeight != null) {
                    double length = 0.0;
                    double width = 0.0;
                    double height = 0.0;

                    String name = objectName.getText().toString();
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
                    objectNames.append(name).append(";");
                }
            }

            // Call Python function to generate graph
            PyObject result = pyObject.callAttr("generate_graph", areaLength, areaWidth, areaHeight, objectsDimensions.toString());

            // Get the base64 encoded image from Python
            String base64Image = result.toString();

            // Display the graph image
            graphImageView.setImageBitmap(ImageUtils.decodeBase64ToBitmap(base64Image));
            graphImageView.setVisibility(View.VISIBLE);
            Toast.makeText(example6.this, "Graph generated successfully", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            // Handle invalid number format in input fields
            Toast.makeText(example6.this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Handle other exceptions
            Toast.makeText(example6.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void reAdjustGraph() {
        // Reset previous graph and messages
        graphImageView.setVisibility(View.GONE);
        //   Toast.makeText(example6.this, "Re-adjusting graph...", Toast.LENGTH_SHORT).show();

        try {
            int objectQuantity = Integer.parseInt(((EditText) findViewById(R.id.objectQuantityEditText)).getText().toString());
            double areaLength = Double.parseDouble(((EditText) findViewById(R.id.areaLengthEditText)).getText().toString());
            double areaWidth = Double.parseDouble(((EditText) findViewById(R.id.areaWidthEditText)).getText().toString());
            double areaHeight = Double.parseDouble(((EditText) findViewById(R.id.areaHeightEditText)).getText().toString());

            // Collect object dimensions and names dynamically
            StringBuilder objectsDimensions = new StringBuilder();
            StringBuilder objectNames = new StringBuilder();
            for (int i = 0; i < objectQuantity; i++) {
                LinearLayout innerLayout = new LinearLayout(example6.this);
                innerLayout.setOrientation(LinearLayout.VERTICAL);
                innerLayout.setGravity(Gravity.CENTER);

                TextView textView = new TextView(example6.this);
                textView.setText("Object " + (i + 1));
                innerLayout.addView(textView);

                // Input box for object name
                EditText objectNameEditText = new EditText(example6.this);
                objectNameEditText.setTag("objectName" + i);
                objectNameEditText.setHint("Object Name");
                innerLayout.addView(objectNameEditText);

                // Input box for object height
                EditText objectLengthEditText = new EditText(example6.this);
                objectLengthEditText.setTag("objectLength" + i);
                objectLengthEditText.setHint("Length");
                innerLayout.addView(objectLengthEditText);

                // Input box for object width
                EditText objectWidthEditText = new EditText(example6.this);
                objectWidthEditText.setTag("objectWidth" + i);
                objectWidthEditText.setHint("Width");
                innerLayout.addView(objectWidthEditText);

                // Input box for object length
                EditText objectHeightEditText = new EditText(example6.this);
                objectHeightEditText.setTag("objectHeight" + i);
                objectHeightEditText.setHint("Height");
                innerLayout.addView(objectHeightEditText);

                objectLinearLayout.addView(innerLayout);
                EditText objectName = objectLinearLayout.findViewWithTag("objectName" + i);
                EditText objectLength = objectLinearLayout.findViewWithTag("objectLength" + i);
                EditText objectWidth = objectLinearLayout.findViewWithTag("objectWidth" + i);
                EditText objectHeight = objectLinearLayout.findViewWithTag("objectHeight" + i);
                if (objectName != null && objectLength != null && objectWidth != null && objectHeight != null) {
                    double length = 0.0;
                    double width = 0.0;
                    double height = 0.0;

                    String name = objectName.getText().toString();
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
                    objectNames.append(name).append(";");
                }
            }

            // Call Python function to re-adjust graph
            PyObject result = pyObject.callAttr("re_adjust_graph", areaLength, areaWidth, areaHeight, objectsDimensions.toString());

            // Get the base64 encoded image from Python
            String base64Image = result.toString();

            // Display the re-adjusted graph image
            graphImageView.setImageBitmap(ImageUtils.decodeBase64ToBitmap(base64Image));
            graphImageView.setVisibility(View.VISIBLE);
            Toast.makeText(example6.this, "Graph re-adjusted successfully", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            // Handle invalid number format in input fields
            Toast.makeText(example6.this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Handle other exceptions
            Toast.makeText(example6.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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


