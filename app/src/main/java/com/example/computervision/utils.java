package com.example.computervision;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class utils {
    Interpreter loadModel(Activity activity) throws IOException {
        AssetFileDescriptor fd = activity.getAssets().openFd("classifier.tflite");
        FileChannel fc = fd.createInputStream().getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, fd.getStartOffset(), fd.getDeclaredLength());
        return new Interpreter(mbb);
    }
}

