package com.example.a3dspacecalculator;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class false_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_false_login);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        final Button btnShowPreview = findViewById(R.id.plotButton);
        btnShowPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call Python script to generate 3D preview
                Python python = Python.getInstance();
                PyObject pyObject = python.getModule("plot_graphs");
                pyObject.callAttr("show_3d_preview", new int[]{5, 5, 5}); // Replace with your object dimensions

                // Display the generated image preview
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageURI(null);
                imageView.setImageURI(Uri.parse("file://" + getFilesDir() + "/object_preview.png"));
            }
        });
    }
}
