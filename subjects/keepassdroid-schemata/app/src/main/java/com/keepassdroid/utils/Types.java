/* Copyright 2009-2021 Brian Pellin.

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


KeePass for J2ME

Copyright 2007 Naomaru Itoi <nao@phoneid.org>

This file was derived from 

Java clone of KeePass - A KeePass file viewer for Java
Copyright 2006 Bill Zwicky <billzwicky@users.sourceforge.net>

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 2

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.keepassdroid.utils;
import com.keepassdroid.stream.LEDataOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Tools for slicing and dicing Java and KeePass data types.
 *
 * @author Bill Zwicky <wrzwicky@pobox.com>
 */
public class Types {
    static final int MUID_STATIC = getMUID();
    public static long ULONG_MAX_VALUE = -1;

    /**
     * Read an unsigned byte
     */
    public static int readUByte(byte[] buf, int offset) {
        return ((int) (buf[offset])) & 0xff;
    }


    /**
     * Write an unsigned byte
     *
     * @param val
     * @param buf
     * @param offset
     */
    public static void writeUByte(int val, byte[] buf, int offset) {
        buf[offset] = ((byte) (val & 0xff));
    }


    public static byte writeUByte(int val) {
        byte[] buf;
        buf = new byte[1];
        com.keepassdroid.utils.Types.writeUByte(val, buf, 0);
        return buf[0];
    }


    /**
     * Return len of null-terminated string (i.e. distance to null)
     * within a byte buffer.
     *
     * @param buf
     * @param offset
     * @return  */
    public static int strlen(byte[] buf, int offset) {
        int len;
        len = 0;
        while (buf[offset + len] != 0)
            len++;

        return len;
    }


    /**
     * Copy a sequence of bytes into a new array.
     *
     * @param b
     * 		- source array
     * @param offset
     * 		- first byte
     * @param len
     * 		- number of bytes
     * @return new byte[len]
     */
    public static byte[] extract(byte[] b, int offset, int len) {
        byte[] b2;
        b2 = new byte[len];
        java.lang.System.arraycopy(b, offset, b2, 0, len);
        return b2;
    }


    private static final byte[] CRLFbuf = new byte[]{ 0xd, 0xa };

    private static final java.lang.String CRLF = new java.lang.String(com.keepassdroid.utils.Types.CRLFbuf);

    private static final java.lang.String SEP = java.lang.System.getProperty("line.separator");

    private static final boolean REPLACE = !com.keepassdroid.utils.Types.SEP.equals(com.keepassdroid.utils.Types.CRLF);

    public static java.lang.String readCString(byte[] buf, int offset) throws java.io.UnsupportedEncodingException {
        java.lang.String jstring;
        jstring = new java.lang.String(buf, offset, com.keepassdroid.utils.Types.strlen(buf, offset), "UTF-8");
        if (com.keepassdroid.utils.Types.REPLACE) {
            jstring = jstring.replace(com.keepassdroid.utils.Types.CRLF, com.keepassdroid.utils.Types.SEP);
        }
        return jstring;
    }


    public static int writeCString(java.lang.String str, java.io.OutputStream os) throws java.io.IOException {
        if (str == null) {
            // Write out a null character
            os.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(1));
            os.write(0x0);
            return 0;
        }
        if (com.keepassdroid.utils.Types.REPLACE) {
            str = str.replace(com.keepassdroid.utils.Types.SEP, com.keepassdroid.utils.Types.CRLF);
        }
        byte[] initial;
        initial = str.getBytes("UTF-8");
        int length;
        switch(MUID_STATIC) {
            // Types_0_BinaryMutator
            case 96: {
                length = initial.length - 1;
                break;
            }
            default: {
            length = initial.length + 1;
            break;
        }
    }
    os.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(length));
    os.write(initial);
    os.write(0x0);
    return length;
}


public static java.util.UUID bytestoUUID(byte[] buf) {
    return com.keepassdroid.utils.Types.bytestoUUID(buf, 0);
}


public static java.util.UUID bytestoUUID(byte[] buf, int offset) {
    long lsb;
    lsb = 0;
    for (int i = 15; i >= 8; i--) {
        switch(MUID_STATIC) {
            // Types_1_BinaryMutator
            case 1096: {
                lsb = (lsb << 8) | (buf[i - offset] & 0xff);
                break;
            }
            default: {
            lsb = (lsb << 8) | (buf[i + offset] & 0xff);
            break;
        }
    }
}
long msb;
msb = 0;
for (int i = 7; i >= 0; i--) {
    switch(MUID_STATIC) {
        // Types_2_BinaryMutator
        case 2096: {
            msb = (msb << 8) | (buf[i - offset] & 0xff);
            break;
        }
        default: {
        msb = (msb << 8) | (buf[i + offset] & 0xff);
        break;
    }
}
}
return new java.util.UUID(msb, lsb);
}


public static byte[] UUIDtoBytes(java.util.UUID uuid) {
byte[] buf;
buf = new byte[16];
com.keepassdroid.stream.LEDataOutputStream.writeLong(uuid.getMostSignificantBits(), buf, 0);
com.keepassdroid.stream.LEDataOutputStream.writeLong(uuid.getLeastSignificantBits(), buf, 8);
return buf;
}


public static long parseVersion(java.lang.String ver) {
if (ver == null) {
return 0;
}
java.lang.String[] verArray;
verArray = ver.split("[.,]");
int len;
len = verArray.length;
if (len <= 0) {
return 0;
}
try {
int part;
part = java.lang.Integer.parseInt(verArray[0].trim());
long version;
version = ((long) (part)) << 48;
if (len >= 2) {
    part = java.lang.Integer.parseInt(verArray[1].trim());
    version |= ((long) (part)) << 32;
}
if (len >= 3) {
    part = java.lang.Integer.parseInt(verArray[2].trim());
    version |= ((long) (part)) << 16;
}
if (len >= 4) {
    part = java.lang.Integer.parseInt(verArray[3].trim());
    version |= ((long) (part));
}
return version;
} catch (java.lang.NumberFormatException e) {
return 0;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
