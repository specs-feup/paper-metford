/* Copyright 2017 Brian Pellin.

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
package com.keepassdroid.collections;
import com.keepassdroid.stream.LEDataOutputStream;
import com.keepassdroid.stream.LEDataInputStream;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.Map;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VariantDictionary {
    static final int MUID_STATIC = getMUID();
    private static final int VdVersion = 0x100;

    private static final int VdmCritical = 0xff00;

    private static final int VdmInfo = 0xff;

    private java.util.Map<java.lang.String, com.keepassdroid.collections.VariantDictionary.VdType> dict = new java.util.HashMap<java.lang.String, com.keepassdroid.collections.VariantDictionary.VdType>();

    private class VdType {
        public static final byte None = 0x0;

        public static final byte UInt32 = 0x4;

        public static final byte UInt64 = 0x5;

        public static final byte Bool = 0x8;

        public static final byte Int32 = 0xc;

        public static final byte Int64 = 0xd;

        public static final byte String = 0x18;

        public static final byte ByteArray = 0x42;

        public final byte type;

        public final java.lang.Object value;

        VdType(byte type, java.lang.Object value) {
            this.type = type;
            this.value = value;
        }

    }

    private java.lang.Object getValue(java.lang.String name) {
        com.keepassdroid.collections.VariantDictionary.VdType val;
        val = dict.get(name);
        if (val == null) {
            return null;
        }
        return val.value;
    }


    private void putType(byte type, java.lang.String name, java.lang.Object value) {
        dict.put(name, new com.keepassdroid.collections.VariantDictionary.VdType(type, value));
    }


    public void setUInt32(java.lang.String name, long value) {
        putType(com.keepassdroid.collections.VariantDictionary.VdType.UInt32, name, value);
    }


    public long getUInt32(java.lang.String name) {
        return ((long) (dict.get(name).value));
    }


    public void setUInt64(java.lang.String name, long value) {
        putType(com.keepassdroid.collections.VariantDictionary.VdType.UInt64, name, value);
    }


    public long getUInt64(java.lang.String name) {
        return ((long) (dict.get(name).value));
    }


    public void setBool(java.lang.String name, boolean value) {
        putType(com.keepassdroid.collections.VariantDictionary.VdType.Bool, name, value);
    }


    public boolean getBool(java.lang.String name) {
        return ((boolean) (dict.get(name).value));
    }


    public void setInt32(java.lang.String name, int value) {
        putType(com.keepassdroid.collections.VariantDictionary.VdType.Int32, name, value);
    }


    public int getInt32(java.lang.String name) {
        return ((int) (dict.get(name).value));
    }


    public void setInt64(java.lang.String name, long value) {
        putType(com.keepassdroid.collections.VariantDictionary.VdType.Int64, name, value);
    }


    public long getInt64(java.lang.String name) {
        return ((long) (dict.get(name).value));
    }


    public void setString(java.lang.String name, java.lang.String value) {
        putType(com.keepassdroid.collections.VariantDictionary.VdType.String, name, value);
    }


    public java.lang.String getString(java.lang.String name) {
        return ((java.lang.String) (getValue(name)));
    }


    public void setByteArray(java.lang.String name, byte[] value) {
        putType(com.keepassdroid.collections.VariantDictionary.VdType.ByteArray, name, value);
    }


    public byte[] getByteArray(java.lang.String name) {
        return ((byte[]) (getValue(name)));
    }


    public static com.keepassdroid.collections.VariantDictionary deserialize(com.keepassdroid.stream.LEDataInputStream lis) throws java.io.IOException {
        com.keepassdroid.collections.VariantDictionary d;
        d = new com.keepassdroid.collections.VariantDictionary();
        int version;
        version = lis.readUShort();
        if ((version & com.keepassdroid.collections.VariantDictionary.VdmCritical) > (com.keepassdroid.collections.VariantDictionary.VdVersion & com.keepassdroid.collections.VariantDictionary.VdmCritical)) {
            throw new java.io.IOException("Invalid format");
        }
        while (true) {
            int type;
            type = lis.read();
            if (type < 0) {
                throw new java.io.IOException("Invalid format");
            }
            byte bType;
            bType = ((byte) (type));
            if (bType == com.keepassdroid.collections.VariantDictionary.VdType.None) {
                break;
            }
            int nameLen;
            nameLen = lis.readInt();
            byte[] nameBuf;
            nameBuf = lis.readBytes(nameLen);
            if (nameLen != nameBuf.length) {
                throw new java.io.IOException("Invalid format");
            }
            java.lang.String name;
            name = new java.lang.String(nameBuf, "UTF-8");
            int valueLen;
            valueLen = lis.readInt();
            byte[] valueBuf;
            valueBuf = lis.readBytes(valueLen);
            if (valueLen != valueBuf.length) {
                throw new java.io.IOException("Invalid format");
            }
            switch (bType) {
                case com.keepassdroid.collections.VariantDictionary.VdType.UInt32 :
                    if (valueLen == 4) {
                        d.setUInt32(name, com.keepassdroid.stream.LEDataInputStream.readUInt(valueBuf, 0));
                    }
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.UInt64 :
                    if (valueLen == 8) {
                        d.setUInt64(name, com.keepassdroid.stream.LEDataInputStream.readLong(valueBuf, 0));
                    }
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.Bool :
                    if (valueLen == 1) {
                        d.setBool(name, valueBuf[0] != 0);
                    }
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.Int32 :
                    if (valueLen == 4) {
                        d.setInt32(name, com.keepassdroid.stream.LEDataInputStream.readInt(valueBuf, 0));
                    }
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.Int64 :
                    if (valueLen == 8) {
                        d.setInt64(name, com.keepassdroid.stream.LEDataInputStream.readLong(valueBuf, 0));
                    }
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.String :
                    d.setString(name, new java.lang.String(valueBuf, "UTF-8"));
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.ByteArray :
                    d.setByteArray(name, valueBuf);
                    break;
                default :
                    assert false;
                    break;
            }
        } 
        return d;
    }


    public static void serialize(com.keepassdroid.collections.VariantDictionary d, com.keepassdroid.stream.LEDataOutputStream los) throws java.io.IOException {
        if (los == null) {
            assert false;
            return;
        }
        los.writeUShort(com.keepassdroid.collections.VariantDictionary.VdVersion);
        for (java.util.Map.Entry<java.lang.String, com.keepassdroid.collections.VariantDictionary.VdType> entry : d.dict.entrySet()) {
            java.lang.String name;
            name = entry.getKey();
            byte[] nameBuf;
            nameBuf = null;
            try {
                nameBuf = name.getBytes("UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                assert false;
                throw new java.io.IOException("Couldn't encode parameter name.");
            }
            com.keepassdroid.collections.VariantDictionary.VdType vd;
            vd = entry.getValue();
            los.write(vd.type);
            los.writeInt(nameBuf.length);
            los.write(nameBuf);
            byte[] buf;
            switch (vd.type) {
                case com.keepassdroid.collections.VariantDictionary.VdType.UInt32 :
                    los.writeInt(4);
                    los.writeUInt(((long) (vd.value)));
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.UInt64 :
                    los.writeInt(8);
                    los.writeLong(((long) (vd.value)));
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.Bool :
                    los.writeInt(1);
                    byte bool;
                    bool = (((boolean) (vd.value))) ? ((byte) (1)) : ((byte) (0));
                    los.write(bool);
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.Int32 :
                    los.writeInt(4);
                    los.writeInt(((int) (vd.value)));
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.Int64 :
                    los.writeInt(8);
                    los.writeLong(((long) (vd.value)));
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.String :
                    java.lang.String value;
                    value = ((java.lang.String) (vd.value));
                    buf = value.getBytes("UTF-8");
                    los.writeInt(buf.length);
                    los.write(buf);
                    break;
                case com.keepassdroid.collections.VariantDictionary.VdType.ByteArray :
                    buf = ((byte[]) (vd.value));
                    los.writeInt(buf.length);
                    los.write(buf);
                    break;
                default :
                    assert false;
                    break;
            }
        }
        los.write(com.keepassdroid.collections.VariantDictionary.VdType.None);
    }


    public void copyTo(com.keepassdroid.collections.VariantDictionary d) {
        for (java.util.Map.Entry<java.lang.String, com.keepassdroid.collections.VariantDictionary.VdType> entry : d.dict.entrySet()) {
            java.lang.String key;
            key = entry.getKey();
            com.keepassdroid.collections.VariantDictionary.VdType value;
            value = entry.getValue();
            dict.put(key, value);
        }
    }


    public int size() {
        return dict.size();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
