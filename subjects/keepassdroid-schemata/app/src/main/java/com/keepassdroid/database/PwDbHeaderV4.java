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
package com.keepassdroid.database;
import com.keepassdroid.utils.Types;
import java.security.InvalidKeyException;
import com.keepassdroid.stream.HmacBlockStream;
import com.keepassdroid.stream.LEDataInputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import com.keepassdroid.crypto.keyDerivation.KdfParameters;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import com.keepassdroid.database.exception.InvalidDBVersionException;
import com.keepassdroid.crypto.keyDerivation.AesKdf;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import com.keepassdroid.database.security.ProtectedBinary;
import java.security.MessageDigest;
import java.util.List;
import com.keepassdroid.stream.CopyInputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbHeaderV4 extends com.keepassdroid.database.PwDbHeader {
    static final int MUID_STATIC = getMUID();
    public static final int DBSIG_PRE2 = 0xb54bfb66;

    public static final int DBSIG_2 = 0xb54bfb67;

    private static final int FILE_VERSION_CRITICAL_MASK = 0xffff0000;

    public static final int FILE_VERSION_32_3 = 0x30001;

    public static final int FILE_VERSION_32_4 = 0x40000;

    public static final int FILE_VERSION_32_4_1 = 0x40001;

    public static final int FILE_VERSION_32 = com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1;

    public class PwDbHeaderV4Fields {
        public static final byte EndOfHeader = 0;

        public static final byte Comment = 1;

        public static final byte CipherID = 2;

        public static final byte CompressionFlags = 3;

        public static final byte MasterSeed = 4;

        public static final byte TransformSeed = 5;

        public static final byte TransformRounds = 6;

        public static final byte EncryptionIV = 7;

        public static final byte InnerRandomstreamKey = 8;

        public static final byte StreamStartBytes = 9;

        public static final byte InnerRandomStreamID = 10;

        public static final byte KdfParameters = 11;

        public static final byte PublicCustomData = 12;
    }

    public class PwDbInnerHeaderV4Fields {
        public static final byte EndOfHeader = 0;

        public static final byte InnerRandomStreamID = 1;

        public static final byte InnerRandomstreamKey = 2;

        public static final byte Binary = 3;
    }

    public class KdbxBinaryFlags {
        public static final byte None = 0;

        public static final byte Protected = 1;
    }

    public class HeaderAndHash {
        public byte[] header;

        public byte[] hash;

        public HeaderAndHash(byte[] header, byte[] hash) {
            this.header = header;
            this.hash = hash;
        }

    }

    private com.keepassdroid.database.PwDatabaseV4 db;

    public byte[] innerRandomStreamKey = new byte[32];

    public byte[] streamStartBytes = new byte[32];

    public com.keepassdroid.database.CrsAlgorithm innerRandomStream;

    public long version;

    public PwDbHeaderV4(com.keepassdroid.database.PwDatabaseV4 d) {
        db = d;
        version = 0;
        masterSeed = new byte[32];
    }


    /**
     * Assumes the input stream is at the beginning of the .kdbx file
     *
     * @param is
     * @throws IOException
     * @throws InvalidDBVersionException
     */
    public com.keepassdroid.database.PwDbHeaderV4.HeaderAndHash loadFromFile(java.io.InputStream is) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBVersionException {
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("No SHA-256 implementation");
        }
        java.io.ByteArrayOutputStream headerBOS;
        headerBOS = new java.io.ByteArrayOutputStream();
        com.keepassdroid.stream.CopyInputStream cis;
        cis = new com.keepassdroid.stream.CopyInputStream(is, headerBOS);
        java.security.DigestInputStream dis;
        dis = new java.security.DigestInputStream(cis, md);
        com.keepassdroid.stream.LEDataInputStream lis;
        lis = new com.keepassdroid.stream.LEDataInputStream(dis);
        int sig1;
        sig1 = lis.readInt();
        int sig2;
        sig2 = lis.readInt();
        if (!com.keepassdroid.database.PwDbHeaderV4.matchesHeader(sig1, sig2)) {
            throw new com.keepassdroid.database.exception.InvalidDBVersionException();
        }
        version = lis.readUInt();
        if (!validVersion(version)) {
            throw new com.keepassdroid.database.exception.InvalidDBVersionException();
        }
        boolean done;
        done = false;
        while (!done) {
            done = readHeaderField(lis);
        } 
        byte[] hash;
        hash = md.digest();
        return new com.keepassdroid.database.PwDbHeaderV4.HeaderAndHash(headerBOS.toByteArray(), hash);
    }


    private boolean readHeaderField(com.keepassdroid.stream.LEDataInputStream dis) throws java.io.IOException {
        byte fieldID;
        fieldID = ((byte) (dis.read()));
        int fieldSize;
        if (version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            fieldSize = dis.readUShort();
        } else {
            fieldSize = dis.readInt();
        }
        byte[] fieldData;
        fieldData = null;
        if (fieldSize > 0) {
            fieldData = new byte[fieldSize];
            int readSize;
            readSize = dis.read(fieldData);
            if (readSize != fieldSize) {
                throw new java.io.IOException("Header ended early.");
            }
        }
        switch (fieldID) {
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.EndOfHeader :
                return true;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.CipherID :
                setCipher(fieldData);
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.CompressionFlags :
                setCompressionFlags(fieldData);
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.MasterSeed :
                masterSeed = fieldData;
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.TransformSeed :
                assert version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
                com.keepassdroid.crypto.keyDerivation.AesKdf kdfS;
                kdfS = new com.keepassdroid.crypto.keyDerivation.AesKdf();
                if (!db.kdfParameters.kdfUUID.equals(kdfS.uuid)) {
                    db.kdfParameters = kdfS.getDefaultParameters();
                }
                db.kdfParameters.setByteArray(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamSeed, fieldData);
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.TransformRounds :
                assert version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
                com.keepassdroid.crypto.keyDerivation.AesKdf kdfR;
                kdfR = new com.keepassdroid.crypto.keyDerivation.AesKdf();
                if (!db.kdfParameters.kdfUUID.equals(kdfR.uuid)) {
                    db.kdfParameters = kdfR.getDefaultParameters();
                }
                long rounds;
                rounds = com.keepassdroid.stream.LEDataInputStream.readLong(fieldData, 0);
                db.kdfParameters.setUInt64(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamRounds, rounds);
                db.numKeyEncRounds = rounds;
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.EncryptionIV :
                encryptionIV = fieldData;
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.InnerRandomstreamKey :
                assert version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
                innerRandomStreamKey = fieldData;
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.StreamStartBytes :
                streamStartBytes = fieldData;
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.InnerRandomStreamID :
                assert version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
                setRandomStreamID(fieldData);
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.KdfParameters :
                db.kdfParameters = com.keepassdroid.crypto.keyDerivation.KdfParameters.deserialize(fieldData);
                break;
            case com.keepassdroid.database.PwDbHeaderV4.PwDbHeaderV4Fields.PublicCustomData :
                db.publicCustomData = com.keepassdroid.crypto.keyDerivation.KdfParameters.deserialize(fieldData);
                break;
            default :
                throw new java.io.IOException("Invalid header type: " + fieldID);
        }
        return false;
    }


    private void setCipher(byte[] pbId) throws java.io.IOException {
        if ((pbId == null) || (pbId.length != 16)) {
            throw new java.io.IOException("Invalid cipher ID.");
        }
        db.dataCipher = com.keepassdroid.utils.Types.bytestoUUID(pbId);
    }


    private void setCompressionFlags(byte[] pbFlags) throws java.io.IOException {
        if ((pbFlags == null) || (pbFlags.length != 4)) {
            throw new java.io.IOException("Invalid compression flags.");
        }
        int flag;
        flag = com.keepassdroid.stream.LEDataInputStream.readInt(pbFlags, 0);
        if ((flag < 0) || (flag >= com.keepassdroid.database.PwCompressionAlgorithm.count)) {
            throw new java.io.IOException("Unrecognized compression flag.");
        }
        db.compressionAlgorithm = com.keepassdroid.database.PwCompressionAlgorithm.fromId(flag);
    }


    private void setTransformRounds(byte[] rounds) throws java.io.IOException {
        if ((rounds == null) || (rounds.length != 8)) {
            throw new java.io.IOException("Invalid rounds.");
        }
        long rnd;
        rnd = com.keepassdroid.stream.LEDataInputStream.readLong(rounds, 0);
        if ((rnd < 0) || (rnd > java.lang.Integer.MAX_VALUE)) {
            // TODO: Actually support really large numbers
            throw new java.io.IOException(("Rounds higher than " + java.lang.Integer.MAX_VALUE) + " are not currently supported.");
        }
        db.numKeyEncRounds = rnd;
    }


    public void setRandomStreamID(byte[] streamID) throws java.io.IOException {
        if ((streamID == null) || (streamID.length != 4)) {
            throw new java.io.IOException("Invalid stream id.");
        }
        int id;
        id = com.keepassdroid.stream.LEDataInputStream.readInt(streamID, 0);
        if ((id < 0) || (id >= com.keepassdroid.database.CrsAlgorithm.count)) {
            throw new java.io.IOException("Invalid stream id.");
        }
        innerRandomStream = com.keepassdroid.database.CrsAlgorithm.fromId(id);
    }


    /**
     * Determines if this is a supported version.
     *
     *  A long is needed here to represent the unsigned int since we perform
     *  arithmetic on it.
     *
     * @param version
     * @return  */
    private boolean validVersion(long version) {
        return !((version & com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_CRITICAL_MASK) > (com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32 & com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_CRITICAL_MASK));
    }


    public static boolean matchesHeader(int sig1, int sig2) {
        return (sig1 == com.keepassdroid.database.PwDbHeader.PWM_DBSIG_1) && ((sig2 == com.keepassdroid.database.PwDbHeaderV4.DBSIG_PRE2) || (sig2 == com.keepassdroid.database.PwDbHeaderV4.DBSIG_2));
    }


    public static byte[] computeHeaderHmac(byte[] header, byte[] key) throws java.io.IOException {
        byte[] headerHmac;
        byte[] blockKey;
        blockKey = com.keepassdroid.stream.HmacBlockStream.GetHmacKey64(key, com.keepassdroid.utils.Types.ULONG_MAX_VALUE);
        javax.crypto.Mac hmac;
        try {
            hmac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec signingKey;
            signingKey = new javax.crypto.spec.SecretKeySpec(blockKey, "HmacSHA256");
            hmac.init(signingKey);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("No HmacAlogirthm");
        } catch (java.security.InvalidKeyException e) {
            throw new java.io.IOException("Invalid Hmac Key");
        }
        return hmac.doFinal(header);
    }


    public byte[] getTransformSeed() {
        assert version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
        return db.kdfParameters.getByteArray(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamSeed);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
