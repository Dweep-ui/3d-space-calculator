package com.example.a3dspacecalculator;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    public CameraPreview(Context context, SurfaceView surfaceView) {
        mSurfaceView = surfaceView;
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    public void startCamera() {
        if (mCamera == null) {
            //noinspection deprecation
            mCamera = Camera.open();
        }
        //noinspection deprecation
        mCamera.setPreviewCallback(this);
        setPreviewDisplay();
    }

    public void stopCamera() {
        if (mCamera != null) {
            //noinspection deprecation
            mCamera.setPreviewCallback(null);
            //noinspection deprecation
            mCamera.stopPreview();
            //noinspection deprecation
            mCamera.release();
            mCamera = null;
        }
    }

    private void setPreviewDisplay() {
        try {
            //noinspection deprecation
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //noinspection deprecation
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setPreviewDisplay();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Adjust camera parameters here if needed
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCamera();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        // Process the camera frame
        int width = camera.getParameters().getPreviewSize().width;
        int height = camera.getParameters().getPreviewSize().height;
        ((MainActivity2) mSurfaceView.getContext()).processFrame(data, width, height);
    }
}

