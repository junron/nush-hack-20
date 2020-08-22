package com.example.computervision;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Utils {
    public static Interpreter loadModel(Activity activity) throws IOException {
        AssetFileDescriptor fd = activity.getAssets().openFd("model.tflite");
        FileChannel fc = fd.createInputStream().getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, fd.getStartOffset(), fd.getDeclaredLength());
        return new Interpreter(mbb);
    }
}

