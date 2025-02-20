package com.example.a3dspacecalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.SurfaceView;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;





public class MainActivity2 extends AppCompatActivity {



private CameraPreview cameraPreview;
    @SuppressLint("MissingInflatedId")
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize Python
        if (!Python.isStarted()) {
        Python.start(new AndroidPlatform(this));
        }

        // Set up camera preview
         SurfaceView surfaceView = findViewById(R.id.surfaceView);
        cameraPreview = new CameraPreview(this, surfaceView);

        // Start the camera preview
        cameraPreview.startCamera();
        }

@Override
protected void onPause() {
        super.onPause();
        cameraPreview.stopCamera();
        }

@Override
protected void onResume() {
        super.onResume();
        cameraPreview.startCamera();
        }

// Process each frame from the camera
public void processFrame(byte[] data, int width, int height) {
        PyObject mainModule = Python.getInstance().getModule("main2");
        PyObject processFrameFunc = mainModule.get("process_frame");
        PyObject result = processFrameFunc.call(data, width, height);
        // Use the processed frame as needed
        }
        }
