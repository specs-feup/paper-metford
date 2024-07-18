package com.beemdevelopment.aegis.helpers;
import com.google.zxing.PlanarYUVLuminanceSource;
import android.util.Log;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import com.google.zxing.NotFoundException;
import androidx.camera.core.ImageProxy;
import com.google.zxing.Result;
import java.nio.ByteBuffer;
import static android.graphics.ImageFormat.YUV_420_888;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class QrCodeAnalyzer implements androidx.camera.core.ImageAnalysis.Analyzer {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = com.beemdevelopment.aegis.helpers.QrCodeAnalyzer.class.getSimpleName();

    public static final android.util.Size RESOLUTION = new android.util.Size(1200, 1600);

    private final com.beemdevelopment.aegis.helpers.QrCodeAnalyzer.Listener _listener;

    public QrCodeAnalyzer(com.beemdevelopment.aegis.helpers.QrCodeAnalyzer.Listener listener) {
        _listener = listener;
    }


    @java.lang.Override
    public void analyze(@androidx.annotation.NonNull
    androidx.camera.core.ImageProxy image) {
        int format;
        format = image.getFormat();
        if (format != android.graphics.ImageFormat.YUV_420_888) {
            android.util.Log.e(com.beemdevelopment.aegis.helpers.QrCodeAnalyzer.TAG, java.lang.String.format("Unexpected YUV image format: %d", format));
            image.close();
            return;
        }
        androidx.camera.core.ImageProxy.PlaneProxy plane;
        plane = image.getPlanes()[0];
        java.nio.ByteBuffer buf;
        buf = plane.getBuffer();
        byte[] data;
        data = new byte[buf.remaining()];
        buf.get(data);
        buf.rewind();
        com.google.zxing.PlanarYUVLuminanceSource source;
        source = new com.google.zxing.PlanarYUVLuminanceSource(data, plane.getRowStride(), image.getHeight(), 0, 0, image.getWidth(), image.getHeight(), false);
        try {
            com.google.zxing.Result result;
            result = com.beemdevelopment.aegis.helpers.QrCodeHelper.decodeFromSource(source);
            if (_listener != null) {
                _listener.onQrCodeDetected(result);
            }
        } catch (com.google.zxing.NotFoundException ignored) {
        } finally {
            image.close();
        }
    }


    public interface Listener {
        void onQrCodeDetected(com.google.zxing.Result result);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
