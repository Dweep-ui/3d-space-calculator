package com.example.a3dspacecalculator;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText numObjectsInput = findViewById(R.id.numObjectsInput);
        final EditText widthInput = findViewById(R.id.widthInput);
        final EditText heightInput = findViewById(R.id.heightInput);
        final EditText depthInput = findViewById(R.id.depthInput);
        final EditText areaWidthInput = findViewById(R.id.areaWidthInput);
        final EditText areaHeightInput = findViewById(R.id.areaHeightInput);
        final EditText areaDepthInput = findViewById(R.id.areaDepthInput);

        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                int numObjects = Integer.parseInt(numObjectsInput.getText().toString());
                float width = Float.parseFloat(widthInput.getText().toString());
                float height = Float.parseFloat(heightInput.getText().toString());
                float depth = Float.parseFloat(depthInput.getText().toString());
                float areaWidth = Float.parseFloat(areaWidthInput.getText().toString());
                float areaHeight = Float.parseFloat(areaHeightInput.getText().toString());
                float areaDepth = Float.parseFloat(areaDepthInput.getText().toString());

                // Call Python functions using subprocess
                try {
                    String pythonScriptPath = "C:/Users/asus/Documents/app/src/main/python/cubee.py";
                    Process process = Runtime.getRuntime().exec("python3 " + pythonScriptPath);

                    // Write data to Python script's standard input if needed
                    OutputStream outputStream = process.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    writer.write("Your input data here\n");
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    // Read Python script's standard output
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Handle Python output if needed
                    }
                    reader.close();

                    // Wait for the Python script to finish
                    process.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

