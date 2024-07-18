/* ` * Copyright 2009-2017 Brian Pellin.

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
import com.keepassdroid.database.PwEntryV3;
import javax.crypto.CipherOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.security.DigestOutputStream;
import com.keepassdroid.crypto.CipherFactory;
import javax.crypto.spec.SecretKeySpec;
import com.keepassdroid.database.PwEncryptionAlgorithm;
import java.security.MessageDigest;
import java.util.List;
import java.io.BufferedOutputStream;
import com.keepassdroid.stream.LEDataOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.io.IOException;
import java.security.SecureRandom;
import java.io.ByteArrayOutputStream;
import com.keepassdroid.database.PwDatabaseV3;
import com.keepassdroid.database.PwGroupV3;
import javax.crypto.spec.IvParameterSpec;
import com.keepassdroid.database.exception.PwDbOutputException;
import com.keepassdroid.database.PwDbHeaderV3;
import com.keepassdroid.database.PwGroup;
import javax.crypto.Cipher;
import com.keepassdroid.database.PwDbHeader;
import com.keepassdroid.stream.NullOutputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbV3Output extends com.keepassdroid.database.save.PwDbOutput {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.database.PwDatabaseV3 mPM;

    private byte[] headerHashBlock;

    public PwDbV3Output(com.keepassdroid.database.PwDatabaseV3 pm, java.io.OutputStream os) {
        super(os);
        mPM = pm;
    }


    public byte[] getFinalKey(com.keepassdroid.database.PwDbHeader header) throws com.keepassdroid.database.exception.PwDbOutputException {
        try {
            com.keepassdroid.database.PwDbHeaderV3 h3;
            h3 = ((com.keepassdroid.database.PwDbHeaderV3) (header));
            mPM.makeFinalKey(h3.masterSeed, h3.transformSeed, mPM.numKeyEncRounds);
            return mPM.finalKey;
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Key creation failed: " + e.getMessage());
        }
    }


    @java.lang.Override
    public void output() throws com.keepassdroid.database.exception.PwDbOutputException {
        prepForOutput();
        com.keepassdroid.database.PwDbHeader header;
        header = outputHeader(mOS);
        byte[] finalKey;
        finalKey = getFinalKey(header);
        javax.crypto.Cipher cipher;
        try {
            if (mPM.algorithm == com.keepassdroid.database.PwEncryptionAlgorithm.Rjindal) {
                cipher = com.keepassdroid.crypto.CipherFactory.getInstance("AES/CBC/PKCS5Padding");
            } else if (mPM.algorithm == com.keepassdroid.database.PwEncryptionAlgorithm.Twofish) {
                cipher = com.keepassdroid.crypto.CipherFactory.getInstance("Twofish/CBC/PKCS7PADDING");
            } else {
                throw new java.lang.Exception();
            }
        } catch (java.lang.Exception e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Algorithm not supported.");
        }
        try {
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, new javax.crypto.spec.SecretKeySpec(finalKey, "AES"), new javax.crypto.spec.IvParameterSpec(header.encryptionIV));
            javax.crypto.CipherOutputStream cos;
            cos = new javax.crypto.CipherOutputStream(mOS, cipher);
            java.io.BufferedOutputStream bos;
            bos = new java.io.BufferedOutputStream(cos);
            outputPlanGroupAndEntries(bos);
            bos.flush();
            bos.close();
        } catch (java.security.InvalidKeyException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Invalid key");
        } catch (java.security.InvalidAlgorithmParameterException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Invalid algorithm parameter.");
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Failed to output final encrypted part.");
        }
    }


    private void prepForOutput() {
        // Before we output the header, we should sort our list of groups and remove any orphaned nodes that are no longer part of the group hierarchy
        sortGroupsForOutput();
    }


    @java.lang.Override
    protected java.security.SecureRandom setIVs(com.keepassdroid.database.PwDbHeader header) throws com.keepassdroid.database.exception.PwDbOutputException {
        java.security.SecureRandom random;
        random = super.setIVs(header);
        com.keepassdroid.database.PwDbHeaderV3 h3;
        h3 = ((com.keepassdroid.database.PwDbHeaderV3) (header));
        random.nextBytes(h3.transformSeed);
        return random;
    }


    public com.keepassdroid.database.PwDbHeaderV3 outputHeader(java.io.OutputStream os) throws com.keepassdroid.database.exception.PwDbOutputException {
        // Build header
        com.keepassdroid.database.PwDbHeaderV3 header;
        header = new com.keepassdroid.database.PwDbHeaderV3();
        header.signature1 = com.keepassdroid.database.PwDbHeader.PWM_DBSIG_1;
        header.signature2 = com.keepassdroid.database.PwDbHeaderV3.DBSIG_2;
        header.flags = com.keepassdroid.database.PwDbHeaderV3.FLAG_SHA2;
        if (mPM.getEncAlgorithm() == com.keepassdroid.database.PwEncryptionAlgorithm.Rjindal) {
            header.flags |= com.keepassdroid.database.PwDbHeaderV3.FLAG_RIJNDAEL;
        } else if (mPM.getEncAlgorithm() == com.keepassdroid.database.PwEncryptionAlgorithm.Twofish) {
            header.flags |= com.keepassdroid.database.PwDbHeaderV3.FLAG_TWOFISH;
        } else {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Unsupported algorithm.");
        }
        header.version = com.keepassdroid.database.PwDbHeaderV3.DBVER_DW;
        header.numGroups = mPM.getGroups().size();
        header.numEntries = mPM.entries.size();
        header.numKeyEncRounds = mPM.getNumKeyEncRecords();
        setIVs(header);
        // Content checksum
        java.security.MessageDigest md;
        md = null;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("SHA-256 not implemented here.");
        }
        // Header checksum
        java.security.MessageDigest headerDigest;
        try {
            headerDigest = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("SHA-256 not implemented here.");
        }
        com.keepassdroid.stream.NullOutputStream nos;
        nos = new com.keepassdroid.stream.NullOutputStream();
        java.security.DigestOutputStream headerDos;
        headerDos = new java.security.DigestOutputStream(nos, headerDigest);
        // Output header for the purpose of calculating the header checksum
        com.keepassdroid.database.save.PwDbHeaderOutputV3 pho;
        pho = new com.keepassdroid.database.save.PwDbHeaderOutputV3(header, headerDos);
        try {
            pho.outputStart();
            pho.outputEnd();
            headerDos.flush();
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException(e);
        }
        byte[] headerHash;
        headerHash = headerDigest.digest();
        headerHashBlock = getHeaderHashBuffer(headerHash);
        // Output database for the purpose of calculating the content checksum
        nos = new com.keepassdroid.stream.NullOutputStream();
        java.security.DigestOutputStream dos;
        dos = new java.security.DigestOutputStream(nos, md);
        java.io.BufferedOutputStream bos;
        bos = new java.io.BufferedOutputStream(dos);
        try {
            outputPlanGroupAndEntries(bos);
            bos.flush();
            bos.close();
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Failed to generate checksum.");
        }
        header.contentsHash = md.digest();
        // Output header for real output, containing content hash
        pho = new com.keepassdroid.database.save.PwDbHeaderOutputV3(header, os);
        try {
            pho.outputStart();
            dos.on(false);
            pho.outputContentHash();
            dos.on(true);
            pho.outputEnd();
            dos.flush();
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException(e);
        }
        return header;
    }


    public void outputPlanGroupAndEntries(java.io.OutputStream os) throws com.keepassdroid.database.exception.PwDbOutputException {
        com.keepassdroid.stream.LEDataOutputStream los;
        los = new com.keepassdroid.stream.LEDataOutputStream(os);
        if (useHeaderHash() && (headerHashBlock != null)) {
            try {
                los.writeUShort(0x0);
                los.writeInt(headerHashBlock.length);
                los.write(headerHashBlock);
            } catch (java.io.IOException e) {
                throw new com.keepassdroid.database.exception.PwDbOutputException("Failed to output header hash: " + e.getMessage());
            }
        }
        // Groups
        java.util.List<com.keepassdroid.database.PwGroup> groups;
        groups = mPM.getGroups();
        for (int i = 0; i < groups.size(); i++) {
            com.keepassdroid.database.PwGroupV3 pg;
            pg = ((com.keepassdroid.database.PwGroupV3) (groups.get(i)));
            com.keepassdroid.database.save.PwGroupOutputV3 pgo;
            pgo = new com.keepassdroid.database.save.PwGroupOutputV3(pg, os);
            try {
                pgo.output();
            } catch (java.io.IOException e) {
                throw new com.keepassdroid.database.exception.PwDbOutputException("Failed to output a group: " + e.getMessage());
            }
        }
        // Entries
        for (int i = 0; i < mPM.entries.size(); i++) {
            com.keepassdroid.database.PwEntryV3 pe;
            pe = ((com.keepassdroid.database.PwEntryV3) (mPM.entries.get(i)));
            com.keepassdroid.database.save.PwEntryOutputV3 peo;
            peo = new com.keepassdroid.database.save.PwEntryOutputV3(pe, os);
            try {
                peo.output();
            } catch (java.io.IOException e) {
                throw new com.keepassdroid.database.exception.PwDbOutputException("Failed to output an entry.");
            }
        }
    }


    private void sortGroupsForOutput() {
        java.util.List<com.keepassdroid.database.PwGroup> groupList;
        groupList = new java.util.ArrayList<com.keepassdroid.database.PwGroup>();
        // Rebuild list according to coalation sorting order removing any orphaned groups
        java.util.List<com.keepassdroid.database.PwGroup> roots;
        roots = mPM.getGrpRoots();
        for (int i = 0; i < roots.size(); i++) {
            sortGroup(((com.keepassdroid.database.PwGroupV3) (roots.get(i))), groupList);
        }
        mPM.setGroups(groupList);
    }


    private void sortGroup(com.keepassdroid.database.PwGroupV3 group, java.util.List<com.keepassdroid.database.PwGroup> groupList) {
        // Add current group
        groupList.add(group);
        // Recurse over children
        for (int i = 0; i < group.childGroups.size(); i++) {
            sortGroup(((com.keepassdroid.database.PwGroupV3) (group.childGroups.get(i))), groupList);
        }
    }


    private byte[] getHeaderHashBuffer(byte[] headerDigest) {
        java.io.ByteArrayOutputStream baos;
        baos = new java.io.ByteArrayOutputStream();
        try {
            writeExtData(headerDigest, baos);
            return baos.toByteArray();
        } catch (java.io.IOException e) {
            return null;
        }
    }


    private void writeExtData(byte[] headerDigest, java.io.OutputStream os) throws java.io.IOException {
        com.keepassdroid.stream.LEDataOutputStream los;
        los = new com.keepassdroid.stream.LEDataOutputStream(os);
        writeExtDataField(los, 0x1, headerDigest, headerDigest.length);
        byte[] headerRandom;
        headerRandom = new byte[32];
        java.security.SecureRandom rand;
        rand = new java.security.SecureRandom();
        rand.nextBytes(headerRandom);
        writeExtDataField(los, 0x2, headerRandom, headerRandom.length);
        writeExtDataField(los, 0xffff, null, 0);
    }


    private void writeExtDataField(com.keepassdroid.stream.LEDataOutputStream los, int fieldType, byte[] data, int fieldSize) throws java.io.IOException {
        los.writeUShort(fieldType);
        los.writeInt(fieldSize);
        if (data != null) {
            los.write(data);
        }
    }


    protected boolean useHeaderHash() {
        return true;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
