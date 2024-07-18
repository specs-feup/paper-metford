/* Copyright 2010 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.stream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Little Endian version of the DataOutputStream
 *
 * @author bpellin
 */
public class LEDataOutputStream extends java.io.OutputStream {
    static final int MUID_STATIC = getMUID();
    private java.io.OutputStream baseStream;

    public LEDataOutputStream(java.io.OutputStream out) {
        baseStream = out;
    }


    public void writeUInt(long uint) throws java.io.IOException {
        baseStream.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(((int) (uint))));
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        baseStream.close();
    }


    @java.lang.Override
    public void flush() throws java.io.IOException {
        baseStream.flush();
    }


    @java.lang.Override
    public void write(byte[] buffer, int offset, int count) throws java.io.IOException {
        baseStream.write(buffer, offset, count);
    }


    @java.lang.Override
    public void write(byte[] buffer) throws java.io.IOException {
        baseStream.write(buffer);
    }


    @java.lang.Override
    public void write(int oneByte) throws java.io.IOException {
        baseStream.write(oneByte);
    }


    public void writeLong(long val) throws java.io.IOException {
        byte[] buf;
        buf = new byte[8];
        com.keepassdroid.stream.LEDataOutputStream.writeLong(val, buf, 0);
        baseStream.write(buf);
    }


    public void writeInt(int val) throws java.io.IOException {
        byte[] buf;
        buf = new byte[4];
        com.keepassdroid.stream.LEDataOutputStream.writeInt(val, buf, 0);
        baseStream.write(buf);
    }


    public void writeUShort(int val) throws java.io.IOException {
        byte[] buf;
        buf = new byte[2];
        com.keepassdroid.stream.LEDataOutputStream.writeUShort(val, buf, 0);
        baseStream.write(buf);
    }


    public static byte[] writeIntBuf(int val) {
        byte[] buf;
        buf = new byte[4];
        com.keepassdroid.stream.LEDataOutputStream.writeInt(val, buf, 0);
        return buf;
    }


    public static byte[] writeUShortBuf(int val) {
        byte[] buf;
        buf = new byte[2];
        com.keepassdroid.stream.LEDataOutputStream.writeUShort(val, buf, 0);
        return buf;
    }


    /**
     * Write an unsigned 16-bit value
     *
     * @param val
     * @param buf
     * @param offset
     */
    public static void writeUShort(int val, byte[] buf, int offset) {
        switch(MUID_STATIC) {
            // LEDataOutputStream_0_BinaryMutator
            case 70: {
                buf[offset - 0] = ((byte) (val & 0xff));
                break;
            }
            default: {
            buf[offset + 0] = ((byte) (val & 0xff));
            break;
        }
    }
    switch(MUID_STATIC) {
        // LEDataOutputStream_1_BinaryMutator
        case 1070: {
            buf[offset - 1] = ((byte) ((val & 0xff00) >> 8));
            break;
        }
        default: {
        buf[offset + 1] = ((byte) ((val & 0xff00) >> 8));
        break;
    }
}
}


/**
 * Write a 32-bit value.
 *
 * @param val
 * @param buf
 * @param offset
 */
public static void writeInt(int val, byte[] buf, int offset) {
switch(MUID_STATIC) {
    // LEDataOutputStream_2_BinaryMutator
    case 2070: {
        buf[offset - 0] = ((byte) (val & 0xff));
        break;
    }
    default: {
    buf[offset + 0] = ((byte) (val & 0xff));
    break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_3_BinaryMutator
case 3070: {
    buf[offset - 1] = ((byte) ((val >>> 8) & 0xff));
    break;
}
default: {
buf[offset + 1] = ((byte) ((val >>> 8) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_4_BinaryMutator
case 4070: {
buf[offset - 2] = ((byte) ((val >>> 16) & 0xff));
break;
}
default: {
buf[offset + 2] = ((byte) ((val >>> 16) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_5_BinaryMutator
case 5070: {
buf[offset - 3] = ((byte) ((val >>> 24) & 0xff));
break;
}
default: {
buf[offset + 3] = ((byte) ((val >>> 24) & 0xff));
break;
}
}
}


public static byte[] writeLongBuf(long val) {
byte[] buf;
buf = new byte[8];
com.keepassdroid.stream.LEDataOutputStream.writeLong(val, buf, 0);
return buf;
}


public static void writeLong(long val, byte[] buf, int offset) {
switch(MUID_STATIC) {
// LEDataOutputStream_6_BinaryMutator
case 6070: {
buf[offset - 0] = ((byte) (val & 0xff));
break;
}
default: {
buf[offset + 0] = ((byte) (val & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_7_BinaryMutator
case 7070: {
buf[offset - 1] = ((byte) ((val >>> 8) & 0xff));
break;
}
default: {
buf[offset + 1] = ((byte) ((val >>> 8) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_8_BinaryMutator
case 8070: {
buf[offset - 2] = ((byte) ((val >>> 16) & 0xff));
break;
}
default: {
buf[offset + 2] = ((byte) ((val >>> 16) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_9_BinaryMutator
case 9070: {
buf[offset - 3] = ((byte) ((val >>> 24) & 0xff));
break;
}
default: {
buf[offset + 3] = ((byte) ((val >>> 24) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_10_BinaryMutator
case 10070: {
buf[offset - 4] = ((byte) ((val >>> 32) & 0xff));
break;
}
default: {
buf[offset + 4] = ((byte) ((val >>> 32) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_11_BinaryMutator
case 11070: {
buf[offset - 5] = ((byte) ((val >>> 40) & 0xff));
break;
}
default: {
buf[offset + 5] = ((byte) ((val >>> 40) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_12_BinaryMutator
case 12070: {
buf[offset - 6] = ((byte) ((val >>> 48) & 0xff));
break;
}
default: {
buf[offset + 6] = ((byte) ((val >>> 48) & 0xff));
break;
}
}
switch(MUID_STATIC) {
// LEDataOutputStream_13_BinaryMutator
case 13070: {
buf[offset - 7] = ((byte) ((val >>> 56) & 0xff));
break;
}
default: {
buf[offset + 7] = ((byte) ((val >>> 56) & 0xff));
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
