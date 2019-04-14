package com.example.yuramnadzij.photochemistry;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;

import java.io.ByteArrayOutputStream;

import static android.support.constraint.Constraints.TAG;

public class BoxDetector extends Detector {
    private Detector mDelegate;
    private int mBoxWidth, mBoxHeight;
    private Rect rect;

    public BoxDetector(Detector delegate, int boxWidth, int boxHeight){
        mDelegate = delegate;
        mBoxWidth = boxWidth;
        mBoxHeight = boxHeight;
    }

    public BoxDetector(Detector delegate, Rect frame){
        mDelegate = delegate;
        rect = frame;
    }

    public SparseArray detect(Frame frame){
        int width = frame.getMetadata().getWidth();
        int height = frame.getMetadata().getHeight();
       // Log.d(TAG, "width: " + width + " height: " + height); 1280, 720
        YuvImage yuvImage = new YuvImage(frame.getGrayscaleImageData().array(), ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(rect, 100, byteArrayOutputStream);
        byte[] jpegArray = byteArrayOutputStream.toByteArray();

        Bitmap bitmap = BitmapFactory.decodeByteArray(jpegArray, 0, jpegArray.length);

        Frame croppedFrame = new Frame.Builder()
                                .setBitmap(bitmap)
                                .setRotation(frame.getMetadata().getRotation())
                                .build();

        return mDelegate.detect(croppedFrame);
    }

    public boolean isOperational(){
        return mDelegate.isOperational();
    }

    public boolean setFocus(int id){
        return mDelegate.setFocus(id);
    }
}







































