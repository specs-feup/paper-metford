/* Copyright 2009-2016 Brian Pellin.

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
import com.keepassdroid.database.exception.KeyFileEmptyException;
import com.keepassdroid.database.exception.InvalidKeyFileException;
import java.util.HashMap;
import com.keepassdroid.crypto.finalkey.FinalKeyFactory;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import com.keepassdroid.utils.Util;
import com.keepassdroid.crypto.finalkey.FinalKey;
import java.security.DigestOutputStream;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import com.keepassdroid.stream.NullOutputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class PwDatabase {
    static final int MUID_STATIC = getMUID();
    public byte masterKey[] = new byte[32];

    public byte[] finalKey;

    public java.lang.String name = "KeePass database";

    public com.keepassdroid.database.PwGroup rootGroup;

    public com.keepassdroid.database.PwIconFactory iconFactory = new com.keepassdroid.database.PwIconFactory();

    public java.util.Map<com.keepassdroid.database.PwGroupId, com.keepassdroid.database.PwGroup> groups = new java.util.HashMap<com.keepassdroid.database.PwGroupId, com.keepassdroid.database.PwGroup>();

    public java.util.Map<java.util.UUID, com.keepassdroid.database.PwEntry> entries = new java.util.HashMap<java.util.UUID, com.keepassdroid.database.PwEntry>();

    private static boolean isKDBExtension(java.lang.String filename) {
        if (filename == null) {
            return false;
        }
        int extIdx;
        extIdx = filename.lastIndexOf(".");
        if (extIdx == (-1))
            return false;

        return filename.substring(extIdx, filename.length()).equalsIgnoreCase(".kdb");
    }


    public static com.keepassdroid.database.PwDatabase getNewDBInstance(java.lang.String filename) {
        if (com.keepassdroid.database.PwDatabase.isKDBExtension(filename)) {
            return new com.keepassdroid.database.PwDatabaseV3();
        } else {
            return new com.keepassdroid.database.PwDatabaseV4();
        }
    }


    public void makeFinalKey(byte[] masterSeed, byte[] masterSeed2, int numRounds) throws java.io.IOException {
        // Write checksum Checksum
        java.security.MessageDigest md;
        md = null;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("SHA-256 not implemented here.");
        }
        com.keepassdroid.stream.NullOutputStream nos;
        nos = new com.keepassdroid.stream.NullOutputStream();
        java.security.DigestOutputStream dos;
        dos = new java.security.DigestOutputStream(nos, md);
        byte[] transformedMasterKey;
        transformedMasterKey = com.keepassdroid.database.PwDatabase.transformMasterKey(masterSeed2, masterKey, numRounds);
        dos.write(masterSeed);
        dos.write(transformedMasterKey);
        finalKey = md.digest();
    }


    /**
     * Encrypt the master key a few times to make brute-force key-search harder
     *
     * @throws IOException
     */
    protected static byte[] transformMasterKey(byte[] pKeySeed, byte[] pKey, int rounds) throws java.io.IOException {
        com.keepassdroid.crypto.finalkey.FinalKey key;
        key = com.keepassdroid.crypto.finalkey.FinalKeyFactory.createFinalKey();
        return key.transformMasterKey(pKeySeed, pKey, rounds);
    }


    public abstract byte[] getMasterKey(java.lang.String key, java.io.InputStream keyInputStream) throws com.keepassdroid.database.exception.InvalidKeyFileException, java.io.IOException;


    public void setMasterKey(java.lang.String key, java.io.InputStream keyInputStream) throws com.keepassdroid.database.exception.InvalidKeyFileException, java.io.IOException {
        assert key != null;
        masterKey = getMasterKey(key, keyInputStream);
    }


    protected byte[] getCompositeKey(java.lang.String key, java.io.InputStream keyInputStream) throws com.keepassdroid.database.exception.InvalidKeyFileException, java.io.IOException {
        assert (key != null) && (keyInputStream != null);
        byte[] fileKey;
        fileKey = getFileKey(keyInputStream);
        byte[] passwordKey;
        passwordKey = getPasswordKey(key);
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("SHA-256 not supported");
        }
        md.update(passwordKey);
        return md.digest(fileKey);
    }


    protected byte[] getFileKey(java.io.InputStream keyInputStream) throws com.keepassdroid.database.exception.InvalidKeyFileException, java.io.IOException {
        assert keyInputStream != null;
        java.io.ByteArrayOutputStream bos;
        bos = new java.io.ByteArrayOutputStream();
        com.keepassdroid.utils.Util.copyStream(keyInputStream, bos);
        byte[] keyData;
        keyData = bos.toByteArray();
        java.io.ByteArrayInputStream bis;
        bis = new java.io.ByteArrayInputStream(keyData);
        byte[] key;
        key = loadXmlKeyFile(bis);
        if (key != null) {
            return key;
        }
        long fileSize;
        fileSize = keyData.length;
        if (fileSize == 0) {
            throw new com.keepassdroid.database.exception.KeyFileEmptyException();
        } else if (fileSize == 32) {
            return keyData;
        } else if (fileSize == 64) {
            byte[] hex;
            hex = new byte[64];
            try {
                return com.keepassdroid.database.PwDatabase.hexStringToByteArray(new java.lang.String(keyData));
            } catch (java.lang.IndexOutOfBoundsException e) {
                // Key is not base 64, treat it as binary data
            }
        }
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("SHA-256 not supported");
        }
        // SHA256Digest md = new SHA256Digest();
        byte[] buffer;
        buffer = new byte[2048];
        int offset;
        offset = 0;
        try {
            md.update(keyData);
        } catch (java.lang.Exception e) {
            java.lang.System.out.println(e.toString());
        }
        return md.digest();
    }


    protected abstract byte[] loadXmlKeyFile(java.io.InputStream keyInputStream);


    public static byte[] hexStringToByteArray(java.lang.String s) {
        int len;
        len = s.length();
        byte[] data;
        switch(MUID_STATIC) {
            // PwDatabase_0_BinaryMutator
            case 156: {
                data = new byte[len * 2];
                break;
            }
            default: {
            data = new byte[len / 2];
            break;
        }
    }
    for (int i = 0; i < len; i += 2) {
        switch(MUID_STATIC) {
            // PwDatabase_1_BinaryMutator
            case 1156: {
                data[i * 2] = ((byte) ((java.lang.Character.digit(s.charAt(i), 16) << 4) + java.lang.Character.digit(s.charAt(i + 1), 16)));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // PwDatabase_2_BinaryMutator
                case 2156: {
                    data[i / 2] = ((byte) ((java.lang.Character.digit(s.charAt(i), 16) << 4) - java.lang.Character.digit(s.charAt(i + 1), 16)));
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // PwDatabase_3_BinaryMutator
                    case 3156: {
                        data[i / 2] = ((byte) ((java.lang.Character.digit(s.charAt(i), 16) << 4) + java.lang.Character.digit(s.charAt(i - 1), 16)));
                        break;
                    }
                    default: {
                    data[i / 2] = ((byte) ((java.lang.Character.digit(s.charAt(i), 16) << 4) + java.lang.Character.digit(s.charAt(i + 1), 16)));
                    break;
                }
            }
            break;
        }
    }
    break;
}
}
}
return data;
}


public boolean validatePasswordEncoding(java.lang.String key) {
java.lang.String encoding;
encoding = getPasswordEncoding();
byte[] bKey;
try {
bKey = key.getBytes(encoding);
} catch (java.io.UnsupportedEncodingException e) {
return false;
}
java.lang.String reencoded;
try {
reencoded = new java.lang.String(bKey, encoding);
} catch (java.io.UnsupportedEncodingException e) {
return false;
}
return key.equals(reencoded);
}


protected abstract java.lang.String getPasswordEncoding();


public byte[] getPasswordKey(java.lang.String key) throws java.io.IOException {
assert key != null;
if (key.length() == 0)
throw new java.lang.IllegalArgumentException("Key cannot be empty.");

java.security.MessageDigest md;
try {
md = java.security.MessageDigest.getInstance("SHA-256");
} catch (java.security.NoSuchAlgorithmException e) {
throw new java.io.IOException("SHA-256 not supported");
}
byte[] bKey;
try {
bKey = key.getBytes(getPasswordEncoding());
} catch (java.io.UnsupportedEncodingException e) {
assert false;
bKey = key.getBytes();
}
md.update(bKey, 0, bKey.length);
return md.digest();
}


public abstract java.util.List<com.keepassdroid.database.PwGroup> getGrpRoots();


public abstract java.util.List<com.keepassdroid.database.PwGroup> getGroups();


public abstract java.util.List<com.keepassdroid.database.PwEntry> getEntries();


public abstract long getNumRounds();


public abstract void setNumRounds(long rounds) throws java.lang.NumberFormatException;


public abstract boolean appSettingsEnabled();


public abstract com.keepassdroid.database.PwEncryptionAlgorithm getEncAlgorithm();


public void addGroupTo(com.keepassdroid.database.PwGroup newGroup, com.keepassdroid.database.PwGroup parent) {
// Add group to parent group
if (parent == null) {
parent = rootGroup;
}
parent.childGroups.add(newGroup);
newGroup.setParent(parent);
groups.put(newGroup.getId(), newGroup);
parent.touch(true, true);
}


public void removeGroupFrom(com.keepassdroid.database.PwGroup remove, com.keepassdroid.database.PwGroup parent) {
// Remove group from parent group
parent.childGroups.remove(remove);
groups.remove(remove.getId());
}


public void addEntryTo(com.keepassdroid.database.PwEntry newEntry, com.keepassdroid.database.PwGroup parent) {
// Add entry to parent
if (parent != null) {
parent.childEntries.add(newEntry);
}
newEntry.setParent(parent);
entries.put(newEntry.getUUID(), newEntry);
}


public void removeEntryFrom(com.keepassdroid.database.PwEntry remove, com.keepassdroid.database.PwGroup parent) {
// Remove entry for parent
if (parent != null) {
parent.childEntries.remove(remove);
}
entries.remove(remove.getUUID());
}


public abstract com.keepassdroid.database.PwGroupId newGroupId();


/**
 * Determine if an id number is already in use
 *
 * @param id
 * 		ID number to check for
 * @return True if the ID is used, false otherwise
 */
protected boolean isGroupIdUsed(com.keepassdroid.database.PwGroupId id) {
java.util.List<com.keepassdroid.database.PwGroup> groups;
groups = getGroups();
for (int i = 0; i < groups.size(); i++) {
com.keepassdroid.database.PwGroup group;
group = groups.get(i);
if (group.getId().equals(id)) {
return true;
}
}
return false;
}


public abstract com.keepassdroid.database.PwGroup createGroup();


public abstract boolean isBackup(com.keepassdroid.database.PwGroup group);


public void populateGlobals(com.keepassdroid.database.PwGroup currentGroup) {
java.util.List<com.keepassdroid.database.PwGroup> childGroups;
childGroups = currentGroup.childGroups;
java.util.List<com.keepassdroid.database.PwEntry> childEntries;
childEntries = currentGroup.childEntries;
for (int i = 0; i < childEntries.size(); i++) {
com.keepassdroid.database.PwEntry cur;
cur = childEntries.get(i);
entries.put(cur.getUUID(), cur);
}
for (int i = 0; i < childGroups.size(); i++) {
com.keepassdroid.database.PwGroup cur;
cur = childGroups.get(i);
groups.put(cur.getId(), cur);
populateGlobals(cur);
}
}


public boolean canRecycle(com.keepassdroid.database.PwGroup group) {
return false;
}


public boolean canRecycle(com.keepassdroid.database.PwEntry entry) {
return false;
}


public void recycle(com.keepassdroid.database.PwEntry entry) {
// Assume calls to this are protected by calling inRecyleBin
throw new java.lang.RuntimeException("Call not valid for .kdb databases.");
}


public void undoRecycle(com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.PwGroup origParent) {
throw new java.lang.RuntimeException("Call not valid for .kdb databases.");
}


public void deleteEntry(com.keepassdroid.database.PwEntry entry) {
com.keepassdroid.database.PwGroup parent;
parent = entry.getParent();
removeEntryFrom(entry, parent);
parent.touch(false, true);
}


public void undoDeleteEntry(com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.PwGroup origParent) {
addEntryTo(entry, origParent);
}


public com.keepassdroid.database.PwGroup getRecycleBin() {
return null;
}


public boolean isGroupSearchable(com.keepassdroid.database.PwGroup group, boolean omitBackup) {
return group != null;
}


/**
 * Initialize a newly created database
 */
public abstract void initNew(java.lang.String name);


public abstract void clearCache();


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
