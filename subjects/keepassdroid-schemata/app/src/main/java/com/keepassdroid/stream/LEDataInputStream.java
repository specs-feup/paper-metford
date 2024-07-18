/* Copyright 2010-2017 Brian Pellin.

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
import com.keepassdroid.utils.Types;
import java.io.InputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Little endian version of the DataInputStream
 *
 * @author bpellin
 */
public class LEDataInputStream extends java.io.InputStream {
    static final int MUID_STATIC = getMUID();
    public static final long INT_TO_LONG_MASK = 0xffffffffL;

    private java.io.InputStream baseStream;

    public LEDataInputStream(java.io.InputStream in) {
        baseStream = in;
    }


    /**
     * Read a 32-bit value and return it as a long, so that it can
     *  be interpreted as an unsigned integer.
     *
     * @return  * @throws IOException
     */
    public long readUInt() throws java.io.IOException {
        return com.keepassdroid.stream.LEDataInputStream.readUInt(baseStream);
    }


    public int readInt() throws java.io.IOException {
        return com.keepassdroid.stream.LEDataInputStream.readInt(baseStream);
    }


    public long readLong() throws java.io.IOException {
        byte[] buf;
        buf = readBytes(8);
        return com.keepassdroid.stream.LEDataInputStream.readLong(buf, 0);
    }


    @java.lang.Override
    public int available() throws java.io.IOException {
        return baseStream.available();
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        baseStream.close();
    }


    @java.lang.Override
    public void mark(int readlimit) {
        baseStream.mark(readlimit);
    }


    @java.lang.Override
    public boolean markSupported() {
        return baseStream.markSupported();
    }


    @java.lang.Override
    public int read() throws java.io.IOException {
        return baseStream.read();
    }


    @java.lang.Override
    public int read(byte[] b, int offset, int length) throws java.io.IOException {
        return baseStream.read(b, offset, length);
    }


    @java.lang.Override
    public int read(byte[] b) throws java.io.IOException {
        // TODO Auto-generated method stub
        return super.read(b);
    }


    @java.lang.Override
    public synchronized void reset() throws java.io.IOException {
        baseStream.reset();
    }


    @java.lang.Override
    public long skip(long n) throws java.io.IOException {
        return baseStream.skip(n);
    }


    public static byte[] readBytes(java.io.InputStream is, int length) throws java.io.IOException {
        byte[] buf;
        buf = new byte[length];
        int count;
        count = 0;
        while (count < length) {
            int read;
            switch(MUID_STATIC) {
                // LEDataInputStream_0_BinaryMutator
                case 73: {
                    read = is.read(buf, count, length + count);
                    break;
                }
                default: {
                read = is.read(buf, count, length - count);
                break;
            }
        }
        // Reached end
        if (read == (-1)) {
            // Stop early
            byte[] early;
            early = new byte[count];
            java.lang.System.arraycopy(buf, 0, early, 0, count);
            return early;
        }
        count += read;
    } 
    return buf;
}


public byte[] readBytes(int length) throws java.io.IOException {
    return com.keepassdroid.stream.LEDataInputStream.readBytes(baseStream, length);
}


public static int readUShort(java.io.InputStream is) throws java.io.IOException {
    byte[] buf;
    buf = com.keepassdroid.stream.LEDataInputStream.readBytes(is, 2);
    buf = com.keepassdroid.stream.LEDataInputStream.padOut(buf, 2);
    return com.keepassdroid.stream.LEDataInputStream.readUShort(buf, 0);
}


public int readUShort() throws java.io.IOException {
    return com.keepassdroid.stream.LEDataInputStream.readUShort(baseStream);
}


/**
 * Read an unsigned 16-bit value.
 *
 * @param buf
 * @param offset
 * @return  */
public static int readUShort(byte[] buf, int offset) {
    switch(MUID_STATIC) {
        // LEDataInputStream_1_BinaryMutator
        case 1073: {
            return (buf[offset + 0] & 0xff) - ((buf[offset + 1] & 0xff) << 8);
        }
        default: {
        switch(MUID_STATIC) {
            // LEDataInputStream_2_BinaryMutator
            case 2073: {
                return (buf[offset - 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8);
            }
            default: {
            switch(MUID_STATIC) {
                // LEDataInputStream_3_BinaryMutator
                case 3073: {
                    return (buf[offset + 0] & 0xff) + ((buf[offset - 1] & 0xff) << 8);
                }
                default: {
                return (buf[offset + 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8);
                }
        }
        }
}
}
}
}


public static long readLong(byte[] buf, int offset) {
switch(MUID_STATIC) {
// LEDataInputStream_4_BinaryMutator
case 4073: {
return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) - ((((long) (buf[offset + 7])) & 0xff) << 56);
}
default: {
switch(MUID_STATIC) {
// LEDataInputStream_5_BinaryMutator
case 5073: {
    return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) - ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
}
default: {
switch(MUID_STATIC) {
    // LEDataInputStream_6_BinaryMutator
    case 6073: {
        return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) - ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
    }
    default: {
    switch(MUID_STATIC) {
        // LEDataInputStream_7_BinaryMutator
        case 7073: {
            return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) - ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
        }
        default: {
        switch(MUID_STATIC) {
            // LEDataInputStream_8_BinaryMutator
            case 8073: {
                return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) - ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
            }
            default: {
            switch(MUID_STATIC) {
                // LEDataInputStream_9_BinaryMutator
                case 9073: {
                    return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) - ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                }
                default: {
                switch(MUID_STATIC) {
                    // LEDataInputStream_10_BinaryMutator
                    case 10073: {
                        return (((((((((long) (buf[offset + 0])) & 0xff) - ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // LEDataInputStream_11_BinaryMutator
                        case 11073: {
                            return (((((((((long) (buf[offset - 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // LEDataInputStream_12_BinaryMutator
                            case 12073: {
                                return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset - 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // LEDataInputStream_13_BinaryMutator
                                case 13073: {
                                    return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset - 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                                }
                                default: {
                                switch(MUID_STATIC) {
                                    // LEDataInputStream_14_BinaryMutator
                                    case 14073: {
                                        return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset - 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                                    }
                                    default: {
                                    switch(MUID_STATIC) {
                                        // LEDataInputStream_15_BinaryMutator
                                        case 15073: {
                                            return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset - 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                                        }
                                        default: {
                                        switch(MUID_STATIC) {
                                            // LEDataInputStream_16_BinaryMutator
                                            case 16073: {
                                                return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset - 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                                            }
                                            default: {
                                            switch(MUID_STATIC) {
                                                // LEDataInputStream_17_BinaryMutator
                                                case 17073: {
                                                    return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset - 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                                                }
                                                default: {
                                                switch(MUID_STATIC) {
                                                    // LEDataInputStream_18_BinaryMutator
                                                    case 18073: {
                                                        return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset - 7])) & 0xff) << 56);
                                                    }
                                                    default: {
                                                    return (((((((((long) (buf[offset + 0])) & 0xff) + ((((long) (buf[offset + 1])) & 0xff) << 8)) + ((((long) (buf[offset + 2])) & 0xff) << 16)) + ((((long) (buf[offset + 3])) & 0xff) << 24)) + ((((long) (buf[offset + 4])) & 0xff) << 32)) + ((((long) (buf[offset + 5])) & 0xff) << 40)) + ((((long) (buf[offset + 6])) & 0xff) << 48)) + ((((long) (buf[offset + 7])) & 0xff) << 56);
                                                    }
                                            }
                                            }
                                    }
                                    }
                            }
                            }
                    }
                    }
            }
            }
    }
    }
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}


public static long readUInt(byte[] buf, int offset) {
return com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset) & com.keepassdroid.stream.LEDataInputStream.INT_TO_LONG_MASK;
}


public static int readInt(java.io.InputStream is) throws java.io.IOException {
byte[] buf;
buf = com.keepassdroid.stream.LEDataInputStream.readBytes(is, 4);
buf = com.keepassdroid.stream.LEDataInputStream.padOut(buf, 4);
return com.keepassdroid.stream.LEDataInputStream.readInt(buf, 0);
}


public static byte[] padOut(byte[] input, int length) {
if ((input == null) || (input.length < length)) {
byte[] output;
output = new byte[4];
if (input == null) {
return output;
}
for (int i = 0; i < input.length; i++) {
output[i] = input[i];
}
return output;
}
return input;
}


public static long readUInt(java.io.InputStream is) throws java.io.IOException {
return com.keepassdroid.stream.LEDataInputStream.readInt(is) & com.keepassdroid.stream.LEDataInputStream.INT_TO_LONG_MASK;
}


/**
 * Read a 32-bit value.
 *
 * @param buf
 * @param offset
 * @return  */
public static int readInt(byte[] buf, int offset) {
switch(MUID_STATIC) {
// LEDataInputStream_19_BinaryMutator
case 19073: {
return (((buf[offset + 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8)) + ((buf[offset + 2] & 0xff) << 16)) - ((buf[offset + 3] & 0xff) << 24);
}
default: {
switch(MUID_STATIC) {
// LEDataInputStream_20_BinaryMutator
case 20073: {
return (((buf[offset + 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8)) - ((buf[offset + 2] & 0xff) << 16)) + ((buf[offset + 3] & 0xff) << 24);
}
default: {
switch(MUID_STATIC) {
// LEDataInputStream_21_BinaryMutator
case 21073: {
return (((buf[offset + 0] & 0xff) - ((buf[offset + 1] & 0xff) << 8)) + ((buf[offset + 2] & 0xff) << 16)) + ((buf[offset + 3] & 0xff) << 24);
}
default: {
switch(MUID_STATIC) {
// LEDataInputStream_22_BinaryMutator
case 22073: {
return (((buf[offset - 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8)) + ((buf[offset + 2] & 0xff) << 16)) + ((buf[offset + 3] & 0xff) << 24);
}
default: {
switch(MUID_STATIC) {
// LEDataInputStream_23_BinaryMutator
case 23073: {
return (((buf[offset + 0] & 0xff) + ((buf[offset - 1] & 0xff) << 8)) + ((buf[offset + 2] & 0xff) << 16)) + ((buf[offset + 3] & 0xff) << 24);
}
default: {
switch(MUID_STATIC) {
// LEDataInputStream_24_BinaryMutator
case 24073: {
return (((buf[offset + 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8)) + ((buf[offset - 2] & 0xff) << 16)) + ((buf[offset + 3] & 0xff) << 24);
}
default: {
switch(MUID_STATIC) {
// LEDataInputStream_25_BinaryMutator
case 25073: {
return (((buf[offset + 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8)) + ((buf[offset + 2] & 0xff) << 16)) + ((buf[offset - 3] & 0xff) << 24);
}
default: {
return (((buf[offset + 0] & 0xff) + ((buf[offset + 1] & 0xff) << 8)) + ((buf[offset + 2] & 0xff) << 16)) + ((buf[offset + 3] & 0xff) << 24);
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}


public java.util.UUID readUUID() throws java.io.IOException {
byte[] buf;
buf = readBytes(16);
return com.keepassdroid.utils.Types.bytestoUUID(buf);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
