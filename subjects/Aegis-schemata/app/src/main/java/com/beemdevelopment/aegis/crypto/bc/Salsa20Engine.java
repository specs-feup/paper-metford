/* Copyright (c) 2000-2021 The Legion of the Bouncy Castle Inc. (https://www.bouncycastle.org)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
and associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
 */
package com.beemdevelopment.aegis.crypto.bc;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Implementation of Daniel J. Bernstein's Salsa20 stream cipher, Snuffle 2005
 */
public class Salsa20Engine {
    static final int MUID_STATIC = getMUID();
    private Salsa20Engine() {
    }


    public static void salsaCore(int rounds, int[] input, int[] x) {
        if (input.length != 16) {
            throw new java.lang.IllegalArgumentException();
        }
        if (x.length != 16) {
            throw new java.lang.IllegalArgumentException();
        }
        if ((rounds % 2) != 0) {
            throw new java.lang.IllegalArgumentException("Number of rounds must be even");
        }
        int x00;
        x00 = input[0];
        int x01;
        x01 = input[1];
        int x02;
        x02 = input[2];
        int x03;
        x03 = input[3];
        int x04;
        x04 = input[4];
        int x05;
        x05 = input[5];
        int x06;
        x06 = input[6];
        int x07;
        x07 = input[7];
        int x08;
        x08 = input[8];
        int x09;
        x09 = input[9];
        int x10;
        x10 = input[10];
        int x11;
        x11 = input[11];
        int x12;
        x12 = input[12];
        int x13;
        x13 = input[13];
        int x14;
        x14 = input[14];
        int x15;
        x15 = input[15];
        for (int i = rounds; i > 0; i -= 2) {
            switch(MUID_STATIC) {
                // Salsa20Engine_0_BinaryMutator
                case 6: {
                    x04 ^= java.lang.Integer.rotateLeft(x00 - x12, 7);
                    break;
                }
                default: {
                x04 ^= java.lang.Integer.rotateLeft(x00 + x12, 7);
                break;
            }
        }
        switch(MUID_STATIC) {
            // Salsa20Engine_1_BinaryMutator
            case 1006: {
                x08 ^= java.lang.Integer.rotateLeft(x04 - x00, 9);
                break;
            }
            default: {
            x08 ^= java.lang.Integer.rotateLeft(x04 + x00, 9);
            break;
        }
    }
    switch(MUID_STATIC) {
        // Salsa20Engine_2_BinaryMutator
        case 2006: {
            x12 ^= java.lang.Integer.rotateLeft(x08 - x04, 13);
            break;
        }
        default: {
        x12 ^= java.lang.Integer.rotateLeft(x08 + x04, 13);
        break;
    }
}
switch(MUID_STATIC) {
    // Salsa20Engine_3_BinaryMutator
    case 3006: {
        x00 ^= java.lang.Integer.rotateLeft(x12 - x08, 18);
        break;
    }
    default: {
    x00 ^= java.lang.Integer.rotateLeft(x12 + x08, 18);
    break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_4_BinaryMutator
case 4006: {
    x09 ^= java.lang.Integer.rotateLeft(x05 - x01, 7);
    break;
}
default: {
x09 ^= java.lang.Integer.rotateLeft(x05 + x01, 7);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_5_BinaryMutator
case 5006: {
x13 ^= java.lang.Integer.rotateLeft(x09 - x05, 9);
break;
}
default: {
x13 ^= java.lang.Integer.rotateLeft(x09 + x05, 9);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_6_BinaryMutator
case 6006: {
x01 ^= java.lang.Integer.rotateLeft(x13 - x09, 13);
break;
}
default: {
x01 ^= java.lang.Integer.rotateLeft(x13 + x09, 13);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_7_BinaryMutator
case 7006: {
x05 ^= java.lang.Integer.rotateLeft(x01 - x13, 18);
break;
}
default: {
x05 ^= java.lang.Integer.rotateLeft(x01 + x13, 18);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_8_BinaryMutator
case 8006: {
x14 ^= java.lang.Integer.rotateLeft(x10 - x06, 7);
break;
}
default: {
x14 ^= java.lang.Integer.rotateLeft(x10 + x06, 7);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_9_BinaryMutator
case 9006: {
x02 ^= java.lang.Integer.rotateLeft(x14 - x10, 9);
break;
}
default: {
x02 ^= java.lang.Integer.rotateLeft(x14 + x10, 9);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_10_BinaryMutator
case 10006: {
x06 ^= java.lang.Integer.rotateLeft(x02 - x14, 13);
break;
}
default: {
x06 ^= java.lang.Integer.rotateLeft(x02 + x14, 13);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_11_BinaryMutator
case 11006: {
x10 ^= java.lang.Integer.rotateLeft(x06 - x02, 18);
break;
}
default: {
x10 ^= java.lang.Integer.rotateLeft(x06 + x02, 18);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_12_BinaryMutator
case 12006: {
x03 ^= java.lang.Integer.rotateLeft(x15 - x11, 7);
break;
}
default: {
x03 ^= java.lang.Integer.rotateLeft(x15 + x11, 7);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_13_BinaryMutator
case 13006: {
x07 ^= java.lang.Integer.rotateLeft(x03 - x15, 9);
break;
}
default: {
x07 ^= java.lang.Integer.rotateLeft(x03 + x15, 9);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_14_BinaryMutator
case 14006: {
x11 ^= java.lang.Integer.rotateLeft(x07 - x03, 13);
break;
}
default: {
x11 ^= java.lang.Integer.rotateLeft(x07 + x03, 13);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_15_BinaryMutator
case 15006: {
x15 ^= java.lang.Integer.rotateLeft(x11 - x07, 18);
break;
}
default: {
x15 ^= java.lang.Integer.rotateLeft(x11 + x07, 18);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_16_BinaryMutator
case 16006: {
x01 ^= java.lang.Integer.rotateLeft(x00 - x03, 7);
break;
}
default: {
x01 ^= java.lang.Integer.rotateLeft(x00 + x03, 7);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_17_BinaryMutator
case 17006: {
x02 ^= java.lang.Integer.rotateLeft(x01 - x00, 9);
break;
}
default: {
x02 ^= java.lang.Integer.rotateLeft(x01 + x00, 9);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_18_BinaryMutator
case 18006: {
x03 ^= java.lang.Integer.rotateLeft(x02 - x01, 13);
break;
}
default: {
x03 ^= java.lang.Integer.rotateLeft(x02 + x01, 13);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_19_BinaryMutator
case 19006: {
x00 ^= java.lang.Integer.rotateLeft(x03 - x02, 18);
break;
}
default: {
x00 ^= java.lang.Integer.rotateLeft(x03 + x02, 18);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_20_BinaryMutator
case 20006: {
x06 ^= java.lang.Integer.rotateLeft(x05 - x04, 7);
break;
}
default: {
x06 ^= java.lang.Integer.rotateLeft(x05 + x04, 7);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_21_BinaryMutator
case 21006: {
x07 ^= java.lang.Integer.rotateLeft(x06 - x05, 9);
break;
}
default: {
x07 ^= java.lang.Integer.rotateLeft(x06 + x05, 9);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_22_BinaryMutator
case 22006: {
x04 ^= java.lang.Integer.rotateLeft(x07 - x06, 13);
break;
}
default: {
x04 ^= java.lang.Integer.rotateLeft(x07 + x06, 13);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_23_BinaryMutator
case 23006: {
x05 ^= java.lang.Integer.rotateLeft(x04 - x07, 18);
break;
}
default: {
x05 ^= java.lang.Integer.rotateLeft(x04 + x07, 18);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_24_BinaryMutator
case 24006: {
x11 ^= java.lang.Integer.rotateLeft(x10 - x09, 7);
break;
}
default: {
x11 ^= java.lang.Integer.rotateLeft(x10 + x09, 7);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_25_BinaryMutator
case 25006: {
x08 ^= java.lang.Integer.rotateLeft(x11 - x10, 9);
break;
}
default: {
x08 ^= java.lang.Integer.rotateLeft(x11 + x10, 9);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_26_BinaryMutator
case 26006: {
x09 ^= java.lang.Integer.rotateLeft(x08 - x11, 13);
break;
}
default: {
x09 ^= java.lang.Integer.rotateLeft(x08 + x11, 13);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_27_BinaryMutator
case 27006: {
x10 ^= java.lang.Integer.rotateLeft(x09 - x08, 18);
break;
}
default: {
x10 ^= java.lang.Integer.rotateLeft(x09 + x08, 18);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_28_BinaryMutator
case 28006: {
x12 ^= java.lang.Integer.rotateLeft(x15 - x14, 7);
break;
}
default: {
x12 ^= java.lang.Integer.rotateLeft(x15 + x14, 7);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_29_BinaryMutator
case 29006: {
x13 ^= java.lang.Integer.rotateLeft(x12 - x15, 9);
break;
}
default: {
x13 ^= java.lang.Integer.rotateLeft(x12 + x15, 9);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_30_BinaryMutator
case 30006: {
x14 ^= java.lang.Integer.rotateLeft(x13 - x12, 13);
break;
}
default: {
x14 ^= java.lang.Integer.rotateLeft(x13 + x12, 13);
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_31_BinaryMutator
case 31006: {
x15 ^= java.lang.Integer.rotateLeft(x14 - x13, 18);
break;
}
default: {
x15 ^= java.lang.Integer.rotateLeft(x14 + x13, 18);
break;
}
}
}
switch(MUID_STATIC) {
// Salsa20Engine_32_BinaryMutator
case 32006: {
x[0] = x00 - input[0];
break;
}
default: {
x[0] = x00 + input[0];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_33_BinaryMutator
case 33006: {
x[1] = x01 - input[1];
break;
}
default: {
x[1] = x01 + input[1];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_34_BinaryMutator
case 34006: {
x[2] = x02 - input[2];
break;
}
default: {
x[2] = x02 + input[2];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_35_BinaryMutator
case 35006: {
x[3] = x03 - input[3];
break;
}
default: {
x[3] = x03 + input[3];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_36_BinaryMutator
case 36006: {
x[4] = x04 - input[4];
break;
}
default: {
x[4] = x04 + input[4];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_37_BinaryMutator
case 37006: {
x[5] = x05 - input[5];
break;
}
default: {
x[5] = x05 + input[5];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_38_BinaryMutator
case 38006: {
x[6] = x06 - input[6];
break;
}
default: {
x[6] = x06 + input[6];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_39_BinaryMutator
case 39006: {
x[7] = x07 - input[7];
break;
}
default: {
x[7] = x07 + input[7];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_40_BinaryMutator
case 40006: {
x[8] = x08 - input[8];
break;
}
default: {
x[8] = x08 + input[8];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_41_BinaryMutator
case 41006: {
x[9] = x09 - input[9];
break;
}
default: {
x[9] = x09 + input[9];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_42_BinaryMutator
case 42006: {
x[10] = x10 - input[10];
break;
}
default: {
x[10] = x10 + input[10];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_43_BinaryMutator
case 43006: {
x[11] = x11 - input[11];
break;
}
default: {
x[11] = x11 + input[11];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_44_BinaryMutator
case 44006: {
x[12] = x12 - input[12];
break;
}
default: {
x[12] = x12 + input[12];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_45_BinaryMutator
case 45006: {
x[13] = x13 - input[13];
break;
}
default: {
x[13] = x13 + input[13];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_46_BinaryMutator
case 46006: {
x[14] = x14 - input[14];
break;
}
default: {
x[14] = x14 + input[14];
break;
}
}
switch(MUID_STATIC) {
// Salsa20Engine_47_BinaryMutator
case 47006: {
x[15] = x15 - input[15];
break;
}
default: {
x[15] = x15 + input[15];
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
