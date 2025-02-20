package com.example.a3dspacecalculator;

// MainActivity.java



import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class example4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);

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
            int objectQuantity = Integer.parseInt(objectQuantityEditText.getText().toString());
            double areaLength = Double.parseDouble(areaLengthEditText.getText().toString());
            double areaWidth = Double.parseDouble(areaWidthEditText.getText().toString());
            double areaHeight = Double.parseDouble(areaHeightEditText.getText().toString());

            // Collect object dimensions dynamically
            StringBuilder objectsDimensions = new StringBuilder();
            for (int i = 0; i < objectQuantity; i++) {
                // Use findViewWithTag with the correct tag
                EditText objectLengthEditText = objectLinearLayout.findViewWithTag("objectLength" + i);
                EditText objectWidthEditText = objectLinearLayout.findViewWithTag("objectWidth" + i);
                EditText objectHeightEditText = objectLinearLayout.findViewWithTag("objectHeight" + i);

                // Check if the dynamically added views are null
                if (objectLengthEditText != null && objectWidthEditText != null && objectHeightEditText != null) {
                    double objectLength = Double.parseDouble(objectLengthEditText.getText().toString());
                    double objectWidth = Double.parseDouble(objectWidthEditText.getText().toString());
                    double objectHeight = Double.parseDouble(objectHeightEditText.getText().toString());

                    objectsDimensions.append(objectLength).append(",").append(objectWidth).append(",").append(objectHeight).append(";");
                }
            }

            // Call Python function to generate graph
            PyObject result = pyObject.callAttr("generate_graph", areaLength, areaWidth, areaHeight, objectsDimensions.toString());

            // Get the base64 encoded image from Python
            String base64Image = result.toString();

            // Display the graph image
            graphImageView.setImageBitmap(ImageUtils.decodeBase64ToBitmap(base64Image));
            graphImageView.setVisibility(View.VISIBLE);


        // Dynamically add input fields for each object
        for (int i = 0; i < objectQuantity; i++) {
            EditText objectLengthEditText = new EditText(this);
            objectLengthEditText.setTag("objectLength" + i);
            objectLengthEditText.setHint("Object " + (i + 1) + " Length");
            objectLinearLayout.addView(objectLengthEditText);

            EditText objectWidthEditText = new EditText(this);
            objectWidthEditText.setTag("objectWidth" + i);
            objectWidthEditText.setHint("Object " + (i + 1) + " Width");
            objectLinearLayout.addView(objectWidthEditText);

            EditText objectHeightEditText = new EditText(this);
            objectHeightEditText.setTag("objectHeight" + i);
            objectHeightEditText.setHint("Object " + (i + 1) + " Height");
            objectLinearLayout.addView(objectHeightEditText);
        }
    });
} public boolean onOptionsItemSelected(MenuItem item) {
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

