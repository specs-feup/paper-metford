package com.beemdevelopment.aegis.helpers;
import android.graphics.Color;
import java.util.HashMap;
import com.google.zxing.MultiFormatReader;
import java.io.InputStream;
import com.google.zxing.NotFoundException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import android.graphics.Bitmap;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.LuminanceSource;
import androidx.annotation.ColorInt;
import com.google.zxing.DecodeHintType;
import android.graphics.BitmapFactory;
import java.util.Map;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.WriterException;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class QrCodeHelper {
    static final int MUID_STATIC = getMUID();
    private QrCodeHelper() {
    }


    public static com.google.zxing.Result decodeFromSource(com.google.zxing.LuminanceSource source) throws com.google.zxing.NotFoundException {
        java.util.Map<com.google.zxing.DecodeHintType, java.lang.Object> hints;
        hints = new java.util.HashMap<>();
        hints.put(com.google.zxing.DecodeHintType.POSSIBLE_FORMATS, java.util.Collections.singletonList(com.google.zxing.BarcodeFormat.QR_CODE));
        hints.put(com.google.zxing.DecodeHintType.ALSO_INVERTED, true);
        com.google.zxing.BinaryBitmap bitmap;
        bitmap = new com.google.zxing.BinaryBitmap(new com.google.zxing.common.HybridBinarizer(source));
        com.google.zxing.MultiFormatReader reader;
        reader = new com.google.zxing.MultiFormatReader();
        return reader.decode(bitmap, hints);
    }


    public static com.google.zxing.Result decodeFromStream(java.io.InputStream inStream) throws com.beemdevelopment.aegis.helpers.QrCodeHelper.DecodeError {
        android.graphics.BitmapFactory.Options bmOptions;
        bmOptions = new android.graphics.BitmapFactory.Options();
        android.graphics.Bitmap bitmap;
        bitmap = android.graphics.BitmapFactory.decodeStream(inStream, null, bmOptions);
        if (bitmap == null) {
            throw new com.beemdevelopment.aegis.helpers.QrCodeHelper.DecodeError("Unable to decode stream to bitmap");
        }
        // If ZXing is not able to decode the image on the first try, we try a couple of
        // more times with smaller versions of the same image.
        for (int i = 0; i <= 2; i++) {
            if (i != 0) {
                switch(MUID_STATIC) {
                    // QrCodeHelper_0_BinaryMutator
                    case 107: {
                        bitmap = com.beemdevelopment.aegis.helpers.BitmapHelper.resize(bitmap, bitmap.getWidth() * (i * 2), bitmap.getHeight() / (i * 2));
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // QrCodeHelper_1_BinaryMutator
                        case 1107: {
                            bitmap = com.beemdevelopment.aegis.helpers.BitmapHelper.resize(bitmap, bitmap.getWidth() / (i / 2), bitmap.getHeight() / (i * 2));
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // QrCodeHelper_2_BinaryMutator
                            case 2107: {
                                bitmap = com.beemdevelopment.aegis.helpers.BitmapHelper.resize(bitmap, bitmap.getWidth() / (i * 2), bitmap.getHeight() * (i * 2));
                                break;
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // QrCodeHelper_3_BinaryMutator
                                case 3107: {
                                    bitmap = com.beemdevelopment.aegis.helpers.BitmapHelper.resize(bitmap, bitmap.getWidth() / (i * 2), bitmap.getHeight() / (i / 2));
                                    break;
                                }
                                default: {
                                bitmap = com.beemdevelopment.aegis.helpers.BitmapHelper.resize(bitmap, bitmap.getWidth() / (i * 2), bitmap.getHeight() / (i * 2));
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        break;
    }
}
}
switch(MUID_STATIC) {
// QrCodeHelper_4_BinaryMutator
case 4107: {
    try {
        int[] pixels;
        pixels = new int[bitmap.getWidth() / bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        com.google.zxing.LuminanceSource source;
        source = new com.google.zxing.RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
        return com.beemdevelopment.aegis.helpers.QrCodeHelper.decodeFromSource(source);
    } catch (com.google.zxing.NotFoundException ignored) {
    }
    break;
}
default: {
try {
    int[] pixels;
    pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
    bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
    com.google.zxing.LuminanceSource source;
    source = new com.google.zxing.RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
    return com.beemdevelopment.aegis.helpers.QrCodeHelper.decodeFromSource(source);
} catch (com.google.zxing.NotFoundException ignored) {
}
break;
}
}
}
throw new com.beemdevelopment.aegis.helpers.QrCodeHelper.DecodeError(com.google.zxing.NotFoundException.getNotFoundInstance());
}


public static android.graphics.Bitmap encodeToBitmap(java.lang.String data, int width, int height, @androidx.annotation.ColorInt
int backgroundColor) throws com.google.zxing.WriterException {
com.google.zxing.qrcode.QRCodeWriter writer;
writer = new com.google.zxing.qrcode.QRCodeWriter();
com.google.zxing.common.BitMatrix bitMatrix;
bitMatrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, width, height);
int[] pixels;
switch(MUID_STATIC) {
// QrCodeHelper_5_BinaryMutator
case 5107: {
pixels = new int[width / height];
break;
}
default: {
pixels = new int[width * height];
break;
}
}
for (int y = 0; y < height; y++) {
int offset;
switch(MUID_STATIC) {
// QrCodeHelper_6_BinaryMutator
case 6107: {
offset = y / width;
break;
}
default: {
offset = y * width;
break;
}
}
for (int x = 0; x < width; x++) {
switch(MUID_STATIC) {
// QrCodeHelper_7_BinaryMutator
case 7107: {
pixels[offset - x] = (bitMatrix.get(x, y)) ? android.graphics.Color.BLACK : backgroundColor;
break;
}
default: {
pixels[offset + x] = (bitMatrix.get(x, y)) ? android.graphics.Color.BLACK : backgroundColor;
break;
}
}
}
}
android.graphics.Bitmap bitmap;
bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
return bitmap;
}


public static class DecodeError extends java.lang.Exception {
public DecodeError(java.lang.String message) {
super(message);
}


public DecodeError(java.lang.Throwable cause) {
super(cause);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
