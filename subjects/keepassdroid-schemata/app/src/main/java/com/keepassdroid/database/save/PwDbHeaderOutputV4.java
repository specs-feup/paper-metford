/* Copyright 2012-2017 Brian Pellin.

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
package com.keepassdroid.database.save;
import com.keepassdroid.stream.LEDataOutputStream;
import com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields;
import com.keepassdroid.utils.Types;
import java.security.InvalidKeyException;
import com.keepassdroid.stream.HmacBlockStream;
import java.io.OutputStream;
import java.io.IOException;
import com.keepassdroid.crypto.keyDerivation.KdfParameters;
import com.keepassdroid.collections.VariantDictionary;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import com.keepassdroid.stream.MacOutputStream;
import com.keepassdroid.database.PwDatabaseV4;
import javax.crypto.Mac;
import java.security.DigestOutputStream;
import com.keepassdroid.database.exception.PwDbOutputException;
import javax.crypto.spec.SecretKeySpec;
import com.keepassdroid.database.PwDbHeaderV4;
import java.security.MessageDigest;
import com.keepassdroid.database.PwDbHeader;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbHeaderOutputV4 extends com.keepassdroid.database.save.PwDbHeaderOutput {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.database.PwDbHeaderV4 header;

    private com.keepassdroid.stream.LEDataOutputStream los;

    private com.keepassdroid.stream.MacOutputStream mos;

    private java.security.DigestOutputStream dos;

    private com.keepassdroid.database.PwDatabaseV4 db;

    public byte[] headerHmac;

    private static byte[] EndHeaderValue = new byte[]{ '\r', '\n', '\r', '\n' };

    public PwDbHeaderOutputV4(com.keepassdroid.database.PwDatabaseV4 d, com.keepassdroid.database.PwDbHeaderV4 h, java.io.OutputStream os) throws com.keepassdroid.database.exception.PwDbOutputException {
        db = d;
        header = h;
        java.security.MessageDigest md;
        md = null;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("SHA-256 not implemented here.");
        }
        try {
            d.makeFinalKey(header.masterSeed, d.kdfParameters);
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException(e);
        }
        javax.crypto.Mac hmac;
        try {
            hmac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec signingKey;
            signingKey = new javax.crypto.spec.SecretKeySpec(com.keepassdroid.stream.HmacBlockStream.GetHmacKey64(db.hmacKey, com.keepassdroid.utils.Types.ULONG_MAX_VALUE), "HmacSHA256");
            hmac.init(signingKey);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException(e);
        } catch (java.security.InvalidKeyException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException(e);
        }
        dos = new java.security.DigestOutputStream(os, md);
        mos = new com.keepassdroid.stream.MacOutputStream(dos, hmac);
        los = new com.keepassdroid.stream.LEDataOutputStream(mos);
    }


    public void output() throws java.io.IOException {
        los.writeUInt(com.keepassdroid.database.PwDbHeader.PWM_DBSIG_1);
        los.writeUInt(com.keepassdroid.database.PwDbHeaderV4.DBSIG_2);
        los.writeUInt(header.version);
        writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.CipherID, com.keepassdroid.utils.Types.UUIDtoBytes(db.dataCipher));
        writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.CompressionFlags, com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(db.compressionAlgorithm.id));
        writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.MasterSeed, header.masterSeed);
        if (header.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.TransformSeed, header.getTransformSeed());
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.TransformRounds, com.keepassdroid.stream.LEDataOutputStream.writeLongBuf(db.numKeyEncRounds));
        } else {
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.KdfParameters, com.keepassdroid.crypto.keyDerivation.KdfParameters.serialize(db.kdfParameters));
        }
        if (header.encryptionIV.length > 0) {
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.EncryptionIV, header.encryptionIV);
        }
        if (header.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.InnerRandomstreamKey, header.innerRandomStreamKey);
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.StreamStartBytes, header.streamStartBytes);
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.InnerRandomStreamID, com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(header.innerRandomStream.id));
        }
        if (db.publicCustomData.size() > 0) {
            java.io.ByteArrayOutputStream bos;
            bos = new java.io.ByteArrayOutputStream();
            com.keepassdroid.stream.LEDataOutputStream los;
            los = new com.keepassdroid.stream.LEDataOutputStream(bos);
            com.keepassdroid.collections.VariantDictionary.serialize(db.publicCustomData, los);
            writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.PublicCustomData, bos.toByteArray());
        }
        writeHeaderField(com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.EndOfHeader, com.keepassdroid.database.save.PwDbHeaderOutputV4.EndHeaderValue);
        los.flush();
        hashOfHeader = dos.getMessageDigest().digest();
        headerHmac = mos.getMac();
    }


    private void writeHeaderField(byte fieldId, byte[] pbData) throws java.io.IOException {
        // Write the field id
        los.write(fieldId);
        if (pbData != null) {
            writeHeaderFieldSize(pbData.length);
            los.write(pbData);
        } else {
            writeHeaderFieldSize(0);
        }
    }


    private void writeHeaderFieldSize(int size) throws java.io.IOException {
        if (header.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            los.writeUShort(size);
        } else {
            los.writeInt(size);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
