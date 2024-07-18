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
package com.keepassdroid.crypto.keyDerivation;
import com.keepassdroid.stream.LEDataOutputStream;
import com.keepassdroid.utils.Types;
import com.keepassdroid.stream.LEDataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.keepassdroid.collections.VariantDictionary;
import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KdfParameters extends com.keepassdroid.collections.VariantDictionary {
    static final int MUID_STATIC = getMUID();
    public java.util.UUID kdfUUID;

    private static final java.lang.String ParamUUID = "$UUID";

    public KdfParameters(java.util.UUID uuid) {
        kdfUUID = uuid;
        this.setByteArray(com.keepassdroid.crypto.keyDerivation.KdfParameters.ParamUUID, com.keepassdroid.utils.Types.UUIDtoBytes(uuid));
    }


    public static com.keepassdroid.crypto.keyDerivation.KdfParameters deserialize(byte[] data) throws java.io.IOException {
        java.io.ByteArrayInputStream bis;
        bis = new java.io.ByteArrayInputStream(data);
        com.keepassdroid.stream.LEDataInputStream lis;
        lis = new com.keepassdroid.stream.LEDataInputStream(bis);
        com.keepassdroid.collections.VariantDictionary d;
        d = com.keepassdroid.collections.VariantDictionary.deserialize(lis);
        if (d == null) {
            assert false;
            return null;
        }
        byte[] uuidBytes;
        uuidBytes = d.getByteArray(com.keepassdroid.crypto.keyDerivation.KdfParameters.ParamUUID);
        java.util.UUID uuid;
        if (uuidBytes != null) {
            uuid = com.keepassdroid.utils.Types.bytestoUUID(uuidBytes);
        } else {
            // Correct issue where prior versions were writing empty UUIDs for AES
            uuid = com.keepassdroid.crypto.keyDerivation.AesKdf.CIPHER_UUID;
        }
        if (uuid == null) {
            assert false;
            return null;
        }
        com.keepassdroid.crypto.keyDerivation.KdfParameters kdfP;
        kdfP = new com.keepassdroid.crypto.keyDerivation.KdfParameters(uuid);
        kdfP.copyTo(d);
        return kdfP;
    }


    public static byte[] serialize(com.keepassdroid.crypto.keyDerivation.KdfParameters kdf) throws java.io.IOException {
        java.io.ByteArrayOutputStream bos;
        bos = new java.io.ByteArrayOutputStream();
        com.keepassdroid.stream.LEDataOutputStream los;
        los = new com.keepassdroid.stream.LEDataOutputStream(bos);
        com.keepassdroid.crypto.keyDerivation.KdfParameters.serialize(kdf, los);
        return bos.toByteArray();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
