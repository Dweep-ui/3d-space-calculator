package com.example.a3dspacecalculator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImageUtils {

    public static Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        InputStream inputStream = new ByteArrayInputStream(decodedBytes);
        return BitmapFactory.decodeStream(inputStream);
    }
}
