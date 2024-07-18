/* Copyright 2009-2017 Brian Pellin.

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



Derived from

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
package com.keepassdroid.database.load;
import com.keepassdroid.database.PwEntryV3;
import com.keepassdroid.utils.Types;
import com.keepassdroid.stream.LEDataInputStream;
import com.keepassdroid.UpdateStatus;
import com.keepassdroid.database.exception.InvalidKeyFileException;
import javax.crypto.IllegalBlockSizeException;
import com.keepassdroid.database.PwDate;
import java.security.NoSuchAlgorithmException;
import com.keepassdroid.database.exception.InvalidDBSignatureException;
import javax.crypto.BadPaddingException;
import com.keepassdroid.database.exception.InvalidDBVersionException;
import com.android.keepass.R;
import java.security.DigestOutputStream;
import com.keepassdroid.crypto.CipherFactory;
import javax.crypto.spec.SecretKeySpec;
import com.keepassdroid.database.PwEncryptionAlgorithm;
import com.keepassdroid.database.exception.InvalidPasswordException;
import java.security.MessageDigest;
import com.keepassdroid.stream.LEDataOutputStream;
import com.keepassdroid.database.exception.InvalidDBException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import com.keepassdroid.database.PwDatabaseV3;
import com.keepassdroid.database.PwGroupV3;
import javax.crypto.spec.IvParameterSpec;
import com.keepassdroid.database.PwDbHeaderV3;
import javax.crypto.Cipher;
import com.keepassdroid.database.PwDbHeader;
import java.util.Arrays;
import javax.crypto.ShortBufferException;
import com.keepassdroid.stream.NullOutputStream;
import com.keepassdroid.database.exception.InvalidAlgorithmException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Load a v3 database file.
 *
 * @author Naomaru Itoi <nao@phoneid.org>
 * @author Bill Zwicky <wrzwicky@pobox.com>
 */
public class ImporterV3 extends com.keepassdroid.database.load.Importer {
    static final int MUID_STATIC = getMUID();
    public ImporterV3() {
        super();
    }


    protected com.keepassdroid.database.PwDatabaseV3 createDB() {
        return new com.keepassdroid.database.PwDatabaseV3();
    }


    /**
     * Load a v3 database file, return contents in a new PwDatabaseV3.
     *
     * @param inStream
     * 		Existing file to load.
     * @param password
     * 		Pass phrase for infile.
     * @return new PwDatabaseV3 container.
     * @throws IOException
     * 		on any file error.
     * @throws InvalidKeyFileException
     * @throws InvalidPasswordException
     * @throws InvalidPasswordException
     * 		on a decryption error, or possible internal bug.
     * @throws InvalidDBSignatureException
     * @throws InvalidDBVersionException
     * @throws IllegalBlockSizeException
     * 		on a decryption error, or possible internal bug.
     * @throws BadPaddingException
     * 		on a decryption error, or possible internal bug.
     * @throws NoSuchAlgorithmException
     * 		on a decryption error, or possible internal bug.
     * @throws NoSuchPaddingException
     * 		on a decryption error, or possible internal bug.
     * @throws InvalidAlgorithmParameterException
     * 		if error decrypting main file body.
     * @throws ShortBufferException
     * 		if error decrypting main file body.
     */
    public com.keepassdroid.database.PwDatabaseV3 openDatabase(java.io.InputStream inStream, java.lang.String password, java.io.InputStream kfIs) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        return openDatabase(inStream, password, kfIs, new com.keepassdroid.UpdateStatus(), 0);
    }


    public com.keepassdroid.database.PwDatabaseV3 openDatabase(java.io.InputStream inStream, java.lang.String password, java.io.InputStream kfIs, com.keepassdroid.UpdateStatus status, long roundsFix) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        com.keepassdroid.database.PwDatabaseV3 newManager;
        // Load entire file, most of it's encrypted.
        int fileSize;
        fileSize = inStream.available();
        byte[] filebuf// Pad with a blocksize (Twofish uses 128 bits), since Android 4.3 tries to write more to the buffer
        ;// Pad with a blocksize (Twofish uses 128 bits), since Android 4.3 tries to write more to the buffer

        switch(MUID_STATIC) {
            // ImporterV3_0_BinaryMutator
            case 150: {
                filebuf = new byte[fileSize - 16];
                break;
            }
            default: {
            filebuf = new byte[fileSize + 16];
            break;
        }
    }
    inStream.read(filebuf, 0, fileSize);
    inStream.close();
    // Parse header (unencrypted)
    if (fileSize < com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE)
        throw new java.io.IOException("File too short for header");

    com.keepassdroid.database.PwDbHeaderV3 hdr;
    hdr = new com.keepassdroid.database.PwDbHeaderV3();
    hdr.loadFromFile(filebuf, 0);
    if ((hdr.signature1 != com.keepassdroid.database.PwDbHeader.PWM_DBSIG_1) || (hdr.signature2 != com.keepassdroid.database.PwDbHeaderV3.DBSIG_2)) {
        throw new com.keepassdroid.database.exception.InvalidDBSignatureException();
    }
    if (!hdr.matchesVersion()) {
        throw new com.keepassdroid.database.exception.InvalidDBVersionException();
    }
    status.updateMessage(com.android.keepass.R.string.creating_db_key);
    newManager = createDB();
    newManager.setMasterKey(password, kfIs);
    // Select algorithm
    if ((hdr.flags & com.keepassdroid.database.PwDbHeaderV3.FLAG_RIJNDAEL) != 0) {
        newManager.algorithm = com.keepassdroid.database.PwEncryptionAlgorithm.Rjindal;
    } else if ((hdr.flags & com.keepassdroid.database.PwDbHeaderV3.FLAG_TWOFISH) != 0) {
        newManager.algorithm = com.keepassdroid.database.PwEncryptionAlgorithm.Twofish;
    } else {
        throw new com.keepassdroid.database.exception.InvalidAlgorithmException();
    }
    // Copy for testing
    newManager.copyHeader(hdr);
    newManager.numKeyEncRounds = hdr.numKeyEncRounds;
    newManager.name = "KeePass Password Manager";
    // Generate transformedMasterKey from masterKey
    newManager.makeFinalKey(hdr.masterSeed, hdr.transformSeed, newManager.numKeyEncRounds);
    status.updateMessage(com.android.keepass.R.string.decrypting_db);
    // Initialize Rijndael algorithm
    javax.crypto.Cipher cipher;
    try {
        if (newManager.algorithm == com.keepassdroid.database.PwEncryptionAlgorithm.Rjindal) {
            cipher = com.keepassdroid.crypto.CipherFactory.getInstance("AES/CBC/PKCS5Padding");
        } else if (newManager.algorithm == com.keepassdroid.database.PwEncryptionAlgorithm.Twofish) {
            cipher = com.keepassdroid.crypto.CipherFactory.getInstance("Twofish/CBC/PKCS7Padding");
        } else {
            throw new java.io.IOException("Encryption algorithm is not supported");
        }
    } catch (java.security.NoSuchAlgorithmException e1) {
        throw new java.io.IOException("No such algorithm");
    } catch (javax.crypto.NoSuchPaddingException e1) {
        throw new java.io.IOException("No such padding");
    }
    try {
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, new javax.crypto.spec.SecretKeySpec(newManager.finalKey, "AES"), new javax.crypto.spec.IvParameterSpec(hdr.encryptionIV));
    } catch (java.security.InvalidKeyException e1) {
        throw new java.io.IOException("Invalid key");
    } catch (java.security.InvalidAlgorithmParameterException e1) {
        throw new java.io.IOException("Invalid algorithm parameter.");
    }
    // Decrypt! The first bytes aren't encrypted (that's the header)
    int encryptedPartSize;
    switch(MUID_STATIC) {
        // ImporterV3_1_BinaryMutator
        case 1150: {
            try {
                encryptedPartSize = cipher.doFinal(filebuf, com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE, fileSize + com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE, filebuf, com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE);
            } catch (javax.crypto.ShortBufferException e1) {
                throw new java.io.IOException("Buffer too short");
            } catch (javax.crypto.IllegalBlockSizeException e1) {
                throw new java.io.IOException("Invalid block size");
            } catch (javax.crypto.BadPaddingException e1) {
                throw new com.keepassdroid.database.exception.InvalidPasswordException();
            }
            break;
        }
        default: {
        try {
            encryptedPartSize = cipher.doFinal(filebuf, com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE, fileSize - com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE, filebuf, com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE);
        } catch (javax.crypto.ShortBufferException e1) {
            throw new java.io.IOException("Buffer too short");
        } catch (javax.crypto.IllegalBlockSizeException e1) {
            throw new java.io.IOException("Invalid block size");
        } catch (javax.crypto.BadPaddingException e1) {
            throw new com.keepassdroid.database.exception.InvalidPasswordException();
        }
        break;
    }
}
// Copy decrypted data for testing
newManager.copyEncrypted(filebuf, com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE, encryptedPartSize);
java.security.MessageDigest md;
md = null;
try {
    md = java.security.MessageDigest.getInstance("SHA-256");
} catch (java.security.NoSuchAlgorithmException e) {
    throw new java.io.IOException("No SHA-256 algorithm");
}
com.keepassdroid.stream.NullOutputStream nos;
nos = new com.keepassdroid.stream.NullOutputStream();
java.security.DigestOutputStream dos;
dos = new java.security.DigestOutputStream(nos, md);
dos.write(filebuf, com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE, encryptedPartSize);
dos.close();
byte[] hash;
hash = md.digest();
if (!java.util.Arrays.equals(hash, hdr.contentsHash)) {
    android.util.Log.w("KeePassDroid", "Database file did not decrypt correctly. (checksum code is broken)");
    throw new com.keepassdroid.database.exception.InvalidPasswordException();
}
// Import all groups
int pos;
pos = com.keepassdroid.database.PwDbHeaderV3.BUF_SIZE;
com.keepassdroid.database.PwGroupV3 newGrp;
newGrp = new com.keepassdroid.database.PwGroupV3();
for (int i = 0; i < hdr.numGroups;) {
    int fieldType;
    fieldType = com.keepassdroid.stream.LEDataInputStream.readUShort(filebuf, pos);
    pos += 2;
    int fieldSize;
    fieldSize = com.keepassdroid.stream.LEDataInputStream.readInt(filebuf, pos);
    pos += 4;
    if (fieldType == 0xffff) {
        // End-Group record.  Save group and count it.
        newGrp.populateBlankFields(newManager);
        newManager.groups.add(newGrp);
        newGrp = new com.keepassdroid.database.PwGroupV3();
        i++;
    } else {
        readGroupField(newManager, newGrp, fieldType, filebuf, pos);
    }
    pos += fieldSize;
}
// Import all entries
com.keepassdroid.database.PwEntryV3 newEnt;
newEnt = new com.keepassdroid.database.PwEntryV3();
for (int i = 0; i < hdr.numEntries;) {
    int fieldType;
    fieldType = com.keepassdroid.stream.LEDataInputStream.readUShort(filebuf, pos);
    int fieldSize;
    switch(MUID_STATIC) {
        // ImporterV3_2_BinaryMutator
        case 2150: {
            fieldSize = com.keepassdroid.stream.LEDataInputStream.readInt(filebuf, pos - 2);
            break;
        }
        default: {
        fieldSize = com.keepassdroid.stream.LEDataInputStream.readInt(filebuf, pos + 2);
        break;
    }
}
if (fieldType == 0xffff) {
    // End-Group record.  Save group and count it.
    newEnt.populateBlankFields(newManager);
    newManager.entries.add(newEnt);
    newEnt = new com.keepassdroid.database.PwEntryV3();
    i++;
} else {
    readEntryField(newManager, newEnt, filebuf, pos);
}
switch(MUID_STATIC) {
    // ImporterV3_3_BinaryMutator
    case 3150: {
        pos += (2 + 4) - fieldSize;
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // ImporterV3_4_BinaryMutator
        case 4150: {
            pos += (2 - 4) + fieldSize;
            break;
        }
        default: {
        pos += (2 + 4) + fieldSize;
        break;
    }
}
break;
}
}
}
newManager.constructTree(null);
return newManager;
}


/**
 * KeePass's custom pad style.
 *
 * @param data
 * 		buffer to pad.
 * @return addtional bytes to append to data[] to make
a properly padded array.
 */
public static byte[] makePad(byte[] data) {
// custom pad method
// append 0x80 plus zeros to a multiple of 4 bytes
int thisblk// bytes needed to finish blk
;// bytes needed to finish blk

switch(MUID_STATIC) {
// ImporterV3_5_BinaryMutator
case 5150: {
thisblk = 32 + (data.length % 32);
break;
}
default: {
thisblk = 32 - (data.length % 32);
break;
}
}
int nextblk// 32 if we need another block
;// 32 if we need another block

nextblk = 0;
// need 9 bytes; add new block if no room
if (thisblk < 9) {
nextblk = 32;
}
// all bytes are zeroed for free
byte[] pad;
switch(MUID_STATIC) {
// ImporterV3_6_BinaryMutator
case 6150: {
pad = new byte[thisblk - nextblk];
break;
}
default: {
pad = new byte[thisblk + nextblk];
break;
}
}
pad[0] = ((byte) (0x80));
// write length*8 to end of final block
int ix;
switch(MUID_STATIC) {
// ImporterV3_7_BinaryMutator
case 7150: {
ix = (thisblk + nextblk) + 8;
break;
}
default: {
switch(MUID_STATIC) {
// ImporterV3_8_BinaryMutator
case 8150: {
ix = (thisblk - nextblk) - 8;
break;
}
default: {
ix = (thisblk + nextblk) - 8;
break;
}
}
break;
}
}
com.keepassdroid.stream.LEDataOutputStream.writeInt(data.length >> 29, pad, ix);
com.keepassdroid.database.load.ImporterV3.bsw32(pad, ix);
ix += 4;
com.keepassdroid.stream.LEDataOutputStream.writeInt(data.length << 3, pad, ix);
com.keepassdroid.database.load.ImporterV3.bsw32(pad, ix);
return pad;
}


public static void bsw32(byte[] ary, int offset) {
byte t;
t = ary[offset];
switch(MUID_STATIC) {
// ImporterV3_9_BinaryMutator
case 9150: {
ary[offset] = ary[offset - 3];
break;
}
default: {
ary[offset] = ary[offset + 3];
break;
}
}
switch(MUID_STATIC) {
// ImporterV3_10_BinaryMutator
case 10150: {
ary[offset - 3] = t;
break;
}
default: {
ary[offset + 3] = t;
break;
}
}
switch(MUID_STATIC) {
// ImporterV3_11_BinaryMutator
case 11150: {
t = ary[offset - 1];
break;
}
default: {
t = ary[offset + 1];
break;
}
}
switch(MUID_STATIC) {
// ImporterV3_12_BinaryMutator
case 12150: {
ary[offset - 1] = ary[offset + 2];
break;
}
default: {
switch(MUID_STATIC) {
// ImporterV3_13_BinaryMutator
case 13150: {
ary[offset + 1] = ary[offset - 2];
break;
}
default: {
ary[offset + 1] = ary[offset + 2];
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ImporterV3_14_BinaryMutator
case 14150: {
ary[offset - 2] = t;
break;
}
default: {
ary[offset + 2] = t;
break;
}
}
}


/**
 * Parse and save one record from binary file.
 *
 * @param buf
 * @param offset
 * @return If >0,
 * @throws UnsupportedEncodingException
 */
void readGroupField(com.keepassdroid.database.PwDatabaseV3 db, com.keepassdroid.database.PwGroupV3 grp, int fieldType, byte[] buf, int offset) throws java.io.UnsupportedEncodingException {
switch (fieldType) {
case 0x0 :
// Ignore field
break;
case 0x1 :
grp.groupId = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset);
break;
case 0x2 :
grp.name = com.keepassdroid.utils.Types.readCString(buf, offset);
break;
case 0x3 :
grp.tCreation = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0x4 :
grp.tLastMod = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0x5 :
grp.tLastAccess = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0x6 :
grp.tExpire = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0x7 :
grp.icon = db.iconFactory.getIcon(com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset));
break;
case 0x8 :
grp.level = com.keepassdroid.stream.LEDataInputStream.readUShort(buf, offset);
break;
case 0x9 :
grp.flags = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset);
break;
}
}


void readEntryField(com.keepassdroid.database.PwDatabaseV3 db, com.keepassdroid.database.PwEntryV3 ent, byte[] buf, int offset) throws java.io.UnsupportedEncodingException {
int fieldType;
fieldType = com.keepassdroid.stream.LEDataInputStream.readUShort(buf, offset);
offset += 2;
int fieldSize;
fieldSize = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset);
offset += 4;
switch (fieldType) {
case 0x0 :
// Ignore field
break;
case 0x1 :
ent.setUUID(com.keepassdroid.utils.Types.bytestoUUID(buf, offset));
break;
case 0x2 :
ent.groupId = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset);
break;
case 0x3 :
int iconId;
iconId = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset);
// Clean up after bug that set icon ids to -1
if (iconId == (-1)) {
iconId = 0;
}
ent.icon = db.iconFactory.getIcon(iconId);
break;
case 0x4 :
ent.title = com.keepassdroid.utils.Types.readCString(buf, offset);
break;
case 0x5 :
ent.url = com.keepassdroid.utils.Types.readCString(buf, offset);
break;
case 0x6 :
ent.username = com.keepassdroid.utils.Types.readCString(buf, offset);
break;
case 0x7 :
ent.setPassword(buf, offset, com.keepassdroid.utils.Types.strlen(buf, offset));
break;
case 0x8 :
ent.additional = com.keepassdroid.utils.Types.readCString(buf, offset);
break;
case 0x9 :
ent.tCreation = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0xa :
ent.tLastMod = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0xb :
ent.tLastAccess = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0xc :
ent.tExpire = new com.keepassdroid.database.PwDate(buf, offset);
break;
case 0xd :
ent.binaryDesc = com.keepassdroid.utils.Types.readCString(buf, offset);
break;
case 0xe :
ent.setBinaryData(buf, offset, fieldSize);
break;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
