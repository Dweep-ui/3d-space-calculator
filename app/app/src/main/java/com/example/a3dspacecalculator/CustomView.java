package com.example.a3dspacecalculator;

// CustomView.java
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class CustomView extends View {
    private PyObject pythonModule;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(getContext()));
        }

        // Import your Python module
        pythonModule = Python.getInstance().getModule("your_module_name");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Call the Python script to generate Axes3D plot
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        pythonModule.callAttr("create_axes3d_widget", width, height);
    }
}

