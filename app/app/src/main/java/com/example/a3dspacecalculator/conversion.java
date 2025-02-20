package com.example.a3dspacecalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class conversion extends AppCompatActivity {

    private EditText valueEditText;
    private Spinner fromUnitSpinner, toUnitSpinner;
    private Button convertButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);



        valueEditText = findViewById(R.id.valueEditText);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Define the units for the spinners
        String[] units = {"Meter", "Centimeter", "Millimeter", "Yard", "Foot", "Inch"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        fromUnitSpinner.setSelection(0);
        toUnitSpinner.setSelection(1);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
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
    private void convertUnits() {
        double inputValue = Double.parseDouble(valueEditText.getText().toString());
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();
        double result = 0;

        // Perform unit conversion
        switch (fromUnit) {
            case "Meter":
                result = convertFromMeter(inputValue, toUnit);
                break;
            case "Centimeter":
                result = convertFromCentimeter(inputValue, toUnit);
                break;
            case "Millimeter":
                result = convertFromMillimeter(inputValue, toUnit);
                break;
            case "Yard":
                result = convertFromYard(inputValue, toUnit);
                break;
            case "Foot":
                result = convertFromFoot(inputValue, toUnit);
                break;
            case "Inch":
                result = convertFromInch(inputValue, toUnit);
                break;
        }

        resultTextView.setText(String.format("%.2f %s = %.2f %s", inputValue, fromUnit, result, toUnit));
    }

    private double convertFromMeter(double value, String toUnit) {
        // Implement the conversion logic for Meter to other units
        double result = 0;
        switch (toUnit) {
            case "Centimeter":
                result = value * 100;
                break;
            case "Millimeter":
                result = value * 1000;
                break;
            case "Yard":
                result = value * 1.09361;
                break;
            case "Foot":
                result = value * 3.28084;
                break;
            case "Inch":
                result = value * 39.3701;
                break;
        }
        return result;
    }

    private double convertFromCentimeter(double value, String toUnit) {
        double result = 0;
        switch (toUnit) {
            case "Meter":
                result = value / 100;
                break;
            case "Millimeter":
                result = value * 10;
                break;
            case "Yard":
                result = value / 91.44;
                break;
            case "Foot":
                result = value / 30.48;
                break;
            case "Inch":
                result = value / 2.54;
                break;
        }
        return result;
    }

    private double convertFromMillimeter(double value, String toUnit) {
        double result = 0;
        switch (toUnit) {
            case "Meter":
                result = value / 1000;
                break;
            case "Centimeter":
                result = value / 10;
                break;
            case "Yard":
                result = value / 914.4;
                break;
            case "Foot":
                result = value / 304.8;
                break;
            case "Inch":
                result = value / 25.4;
                break;
        }
        return result;
    }

    private double convertFromYard(double value, String toUnit) {
        double result = 0;
        switch (toUnit) {
            case "Meter":
                result = value * 0.9144;
                break;
            case "Centimeter":
                result = value * 91.44;
                break;
            case "Millimeter":
                result = value * 914.4;
                break;
            case "Foot":
                result = value * 3;
                break;
            case "Inch":
                result = value * 36;
                break;
        }
        return result;
    }

    private double convertFromFoot(double value, String toUnit) {
        double result = 0;
        switch (toUnit) {
            case "Meter":
                result = value * 0.3048;
                break;
            case "Centimeter":
                result = value * 30.48;
                break;
            case "Millimeter":
                result = value * 304.8;
                break;
            case "Yard":
                result = value / 3;
                break;
            case "Inch":
                result = value * 12;
                break;
        }
        return result;
    }

    private double convertFromInch(double value, String toUnit) {
        double result = 0;
        switch (toUnit) {
            case "Meter":
                result = value * 0.0254;
                break;
            case "Centimeter":
                result = value * 2.54;
                break;
            case "Millimeter":
                result = value * 25.4;
                break;
            case "Yard":
                result = value / 36;
                break;
            case "Foot":
                result = value / 12;
                break;
        }
        return result;
    }


    }




