/* Copyright 2010-2022 Brian Pellin.

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
import javax.xml.parsers.DocumentBuilderFactory;
import android.webkit.URLUtil;
import com.keepassdroid.database.exception.InvalidKeyFileException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.util.ArrayList;
import com.keepassdroid.crypto.keyDerivation.KdfParameters;
import com.keepassdroid.collections.VariantDictionary;
import com.keepassdroid.crypto.keyDerivation.KdfEngine;
import java.security.NoSuchAlgorithmException;
import org.w3c.dom.Document;
import com.keepassdroid.crypto.keyDerivation.AesKdf;
import com.keepassdroid.crypto.CryptoUtil;
import com.keepassdroid.crypto.engine.CipherEngine;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;
import android.util.Base64;
import com.keepassdroid.crypto.keyDerivation.KdfFactory;
import com.keepassdroid.utils.EmptyUtils;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.io.IOException;
import org.w3c.dom.Text;
import java.util.Date;
import com.keepassdroid.crypto.engine.AesEngine;
import javax.xml.parsers.DocumentBuilder;
import com.keepassdroid.crypto.engine.ChaCha20Engine;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDatabaseV4 extends com.keepassdroid.database.PwDatabase {
    static final int MUID_STATIC = getMUID();
    public static final java.util.Date DEFAULT_NOW = new java.util.Date();

    public static final java.util.UUID UUID_ZERO = new java.util.UUID(0, 0);

    public static final int DEFAULT_ROUNDS = 6000;

    private static final int DEFAULT_HISTORY_MAX_ITEMS = 10;// -1 unlimited


    private static final long DEFAULT_HISTORY_MAX_SIZE = (6 * 1024) * 1024;// -1 unlimited


    private static final java.lang.String RECYCLEBIN_NAME = "RecycleBin";

    public byte[] hmacKey;

    public java.util.UUID dataCipher = com.keepassdroid.crypto.engine.AesEngine.CIPHER_UUID;

    public com.keepassdroid.crypto.engine.CipherEngine dataEngine = new com.keepassdroid.crypto.engine.AesEngine();

    public com.keepassdroid.database.PwCompressionAlgorithm compressionAlgorithm = com.keepassdroid.database.PwCompressionAlgorithm.Gzip;

    // TODO: Refactor me away to get directly from kdfParameters
    public long numKeyEncRounds = 6000;

    public java.util.Date nameChanged = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    public java.util.Date settingsChanged = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    public java.lang.String description = "";

    public java.util.Date descriptionChanged = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    public java.lang.String defaultUserName = "";

    public java.util.Date defaultUserNameChanged = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    public java.util.Date keyLastChanged = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    public long keyChangeRecDays = -1;

    public long keyChangeForceDays = 1;

    public boolean keyChangeForceOnce = false;

    public long maintenanceHistoryDays = 365;

    public java.lang.String color = "";

    public boolean recycleBinEnabled = true;

    public java.util.UUID recycleBinUUID = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public java.util.Date recycleBinChanged = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    public java.util.UUID entryTemplatesGroup = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public java.util.Date entryTemplatesGroupChanged = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    public int historyMaxItems = com.keepassdroid.database.PwDatabaseV4.DEFAULT_HISTORY_MAX_ITEMS;

    public long historyMaxSize = com.keepassdroid.database.PwDatabaseV4.DEFAULT_HISTORY_MAX_SIZE;

    public java.util.UUID lastSelectedGroup = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public java.util.UUID lastTopVisibleGroup = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public com.keepassdroid.database.PwDatabaseV4.MemoryProtectionConfig memoryProtection = new com.keepassdroid.database.PwDatabaseV4.MemoryProtectionConfig();

    public java.util.List<com.keepassdroid.database.PwDeletedObject> deletedObjects = new java.util.ArrayList<com.keepassdroid.database.PwDeletedObject>();

    public java.util.List<com.keepassdroid.database.PwIconCustom> customIcons = new java.util.ArrayList<com.keepassdroid.database.PwIconCustom>();

    public com.keepassdroid.database.PwCustomData customData = new com.keepassdroid.database.PwCustomData();

    public com.keepassdroid.crypto.keyDerivation.KdfParameters kdfParameters = com.keepassdroid.crypto.keyDerivation.KdfFactory.getDefaultParameters();

    public com.keepassdroid.collections.VariantDictionary publicCustomData = new com.keepassdroid.collections.VariantDictionary();

    public com.keepassdroid.database.BinaryPool binPool = new com.keepassdroid.database.BinaryPool();

    public long version = com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32;

    public java.lang.String localizedAppName = "KeePassDroid";

    public class MemoryProtectionConfig {
        public boolean protectTitle = false;

        public boolean protectUserName = false;

        public boolean protectPassword = false;

        public boolean protectUrl = false;

        public boolean protectNotes = false;

        public boolean autoEnableVisualHiding = false;

        public boolean GetProtection(java.lang.String field) {
            if (field.equalsIgnoreCase(com.keepassdroid.database.PwDefsV4.TITLE_FIELD))
                return protectTitle;

            if (field.equalsIgnoreCase(com.keepassdroid.database.PwDefsV4.USERNAME_FIELD))
                return protectUserName;

            if (field.equalsIgnoreCase(com.keepassdroid.database.PwDefsV4.PASSWORD_FIELD))
                return protectPassword;

            if (field.equalsIgnoreCase(com.keepassdroid.database.PwDefsV4.URL_FIELD))
                return protectUrl;

            if (field.equalsIgnoreCase(com.keepassdroid.database.PwDefsV4.NOTES_FIELD))
                return protectNotes;

            return false;
        }

    }

    @java.lang.Override
    public byte[] getMasterKey(java.lang.String key, java.io.InputStream keyInputStream) throws com.keepassdroid.database.exception.InvalidKeyFileException, java.io.IOException {
        assert key != null;
        byte[] fKey;
        if ((key.length() > 0) && (keyInputStream != null)) {
            return getCompositeKey(key, keyInputStream);
        } else if (key.length() > 0) {
            fKey = getPasswordKey(key);
        } else if (keyInputStream != null) {
            fKey = getFileKey(keyInputStream);
        } else {
            throw new java.lang.IllegalArgumentException("Key cannot be empty.");
        }
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("No SHA-256 implementation");
        }
        return md.digest(fKey);
    }


    @java.lang.Override
    public void makeFinalKey(byte[] masterSeed, byte[] masterSeed2, int numRounds) throws java.io.IOException {
        byte[] transformedMasterKey;
        transformedMasterKey = com.keepassdroid.database.PwDatabase.transformMasterKey(masterSeed2, masterKey, numRounds);
        byte[] cmpKey;
        cmpKey = new byte[65];
        java.lang.System.arraycopy(masterSeed, 0, cmpKey, 0, 32);
        java.lang.System.arraycopy(transformedMasterKey, 0, cmpKey, 32, 32);
        finalKey = com.keepassdroid.crypto.CryptoUtil.resizeKey(cmpKey, 0, 64, dataEngine.keyLength());
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("SHA-512");
            cmpKey[64] = 1;
            hmacKey = md.digest(cmpKey);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("No SHA-512 implementation");
        } finally {
            java.util.Arrays.fill(cmpKey, ((byte) (0)));
        }
    }


    public void makeFinalKey(byte[] masterSeed, com.keepassdroid.crypto.keyDerivation.KdfParameters kdfP) throws java.io.IOException {
        makeFinalKey(masterSeed, kdfP, 0);
    }


    public void makeFinalKey(byte[] masterSeed, com.keepassdroid.crypto.keyDerivation.KdfParameters kdfP, long roundsFix) throws java.io.IOException {
        com.keepassdroid.crypto.keyDerivation.KdfEngine kdfEngine;
        kdfEngine = com.keepassdroid.crypto.keyDerivation.KdfFactory.get(kdfP.kdfUUID);
        if (kdfEngine == null) {
            throw new java.io.IOException("Unknown key derivation function");
        }
        // Set to 6000 rounds to open corrupted database
        if ((roundsFix > 0) && kdfP.kdfUUID.equals(com.keepassdroid.crypto.keyDerivation.AesKdf.CIPHER_UUID)) {
            kdfP.setUInt32(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamRounds, roundsFix);
            numKeyEncRounds = roundsFix;
        }
        byte[] transformedMasterKey;
        transformedMasterKey = kdfEngine.transform(masterKey, kdfP);
        if (transformedMasterKey.length != 32) {
            transformedMasterKey = com.keepassdroid.crypto.CryptoUtil.hashSha256(transformedMasterKey);
        }
        byte[] cmpKey;
        cmpKey = new byte[65];
        java.lang.System.arraycopy(masterSeed, 0, cmpKey, 0, 32);
        java.lang.System.arraycopy(transformedMasterKey, 0, cmpKey, 32, 32);
        finalKey = com.keepassdroid.crypto.CryptoUtil.resizeKey(cmpKey, 0, 64, dataEngine.keyLength());
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("SHA-512");
            cmpKey[64] = 1;
            hmacKey = md.digest(cmpKey);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("No SHA-512 implementation");
        } finally {
            java.util.Arrays.fill(cmpKey, ((byte) (0)));
        }
    }


    @java.lang.Override
    protected java.lang.String getPasswordEncoding() {
        return "UTF-8";
    }


    private static final java.lang.String RootElementName = "KeyFile";

    private static final java.lang.String MetaElementName = "Meta";

    private static final java.lang.String VersionElementName = "Version";

    private static final java.lang.String KeyElementName = "Key";

    private static final java.lang.String KeyDataElementName = "Data";

    @java.lang.Override
    protected byte[] loadXmlKeyFile(java.io.InputStream keyInputStream) {
        try {
            javax.xml.parsers.DocumentBuilderFactory dbf;
            dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc;
            doc = db.parse(keyInputStream);
            org.w3c.dom.Element el;
            el = doc.getDocumentElement();
            if ((el == null) || (!el.getNodeName().equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4.RootElementName))) {
                return null;
            }
            org.w3c.dom.NodeList children;
            children = el.getChildNodes();
            if (children.getLength() < 2) {
                return null;
            }
            long version;
            version = getVersion(children);
            for (int i = 0; i < children.getLength(); i++) {
                org.w3c.dom.Node child;
                child = children.item(i);
                if (child.getNodeName().equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4.KeyElementName)) {
                    org.w3c.dom.NodeList keyChildren;
                    keyChildren = child.getChildNodes();
                    for (int j = 0; j < keyChildren.getLength(); j++) {
                        org.w3c.dom.Node keyChild;
                        keyChild = keyChildren.item(j);
                        if (keyChild.getNodeName().equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4.KeyDataElementName)) {
                            org.w3c.dom.NodeList children2;
                            children2 = keyChild.getChildNodes();
                            for (int k = 0; k < children2.getLength(); k++) {
                                org.w3c.dom.Node text;
                                text = children2.item(k);
                                if (text.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {
                                    org.w3c.dom.Text txt;
                                    txt = ((org.w3c.dom.Text) (text));
                                    return decodeKey(txt.getNodeValue(), version);
                                }
                            }
                        }
                    }
                }
            }
        } catch (java.lang.Exception e) {
            return null;
        }
        return null;
    }


    private byte[] decodeKey(java.lang.String value, long version) {
        if (version == 0x1000000000000L) {
            return android.util.Base64.decode(value, android.util.Base64.NO_WRAP);
        } else if (version == 0x2000000000000L) {
            // Strip all whitespace
            value = value.replaceAll("\\s", "");
            return decodeKeyV2(value);
        } else {
            return null;
        }
    }


    private byte[] decodeKeyV2(java.lang.String value) {
        if (value == null) {
            return null;
        }
        int len;
        len = value.length();
        if ((len & 1) != 0) {
            return null;
        }
        byte[] pb;
        switch(MUID_STATIC) {
            // PwDatabaseV4_0_BinaryMutator
            case 170: {
                pb = new byte[len * 2];
                break;
            }
            default: {
            pb = new byte[len / 2];
            break;
        }
    }
    for (int i = 0; i < len; i += 2) {
        char ch;
        ch = value.charAt(i);
        byte bt;
        bt = hexToByte(ch);
        bt <<= 4;
        switch(MUID_STATIC) {
            // PwDatabaseV4_1_BinaryMutator
            case 1170: {
                ch = value.charAt(i - 1);
                break;
            }
            default: {
            ch = value.charAt(i + 1);
            break;
        }
    }
    byte bt2;
    bt2 = hexToByte(ch);
    bt |= bt2;
    pb[i >> 1] = bt;
}
return pb;
}


private byte hexToByte(char ch) {
byte bt;
if ((ch >= '0') && (ch <= '9')) {
    bt = ((byte) (ch - '0'));
} else if ((ch >= 'a') && (ch <= 'f')) {
    switch(MUID_STATIC) {
        // PwDatabaseV4_2_BinaryMutator
        case 2170: {
            bt = ((byte) ((ch - 'a') - 10));
            break;
        }
        default: {
        bt = ((byte) ((ch - 'a') + 10));
        break;
    }
}
} else if ((ch >= 'A') && (ch <= 'F')) {
switch(MUID_STATIC) {
    // PwDatabaseV4_3_BinaryMutator
    case 3170: {
        bt = ((byte) ((ch - 'A') - 10));
        break;
    }
    default: {
    bt = ((byte) ((ch - 'A') + 10));
    break;
}
}
} else {
bt = 0;
}
return bt;
}


private long getVersion(org.w3c.dom.NodeList rootChildren) {
for (int i = 0; i < rootChildren.getLength(); i++) {
org.w3c.dom.Node child;
child = rootChildren.item(i);
java.lang.String nodename;
nodename = child.getNodeName();
if (nodename.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4.MetaElementName)) {
org.w3c.dom.NodeList metaChildren;
metaChildren = child.getChildNodes();
for (int j = 0; j < metaChildren.getLength(); j++) {
    org.w3c.dom.Node metaChild;
    metaChild = metaChildren.item(j);
    if (metaChild.getNodeName().equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4.VersionElementName)) {
        org.w3c.dom.NodeList children2;
        children2 = metaChild.getChildNodes();
        for (int k = 0; k < children2.getLength(); k++) {
            org.w3c.dom.Node text;
            text = children2.item(k);
            if (text.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {
                org.w3c.dom.Text txt;
                txt = ((org.w3c.dom.Text) (text));
                java.lang.String value;
                value = txt.getNodeValue();
                return com.keepassdroid.utils.Types.parseVersion(value);
            }
        }
    }
}
}
}
return 0;
}


@java.lang.Override
public java.util.List<com.keepassdroid.database.PwGroup> getGroups() {
java.util.List<com.keepassdroid.database.PwGroup> list;
list = new java.util.ArrayList<com.keepassdroid.database.PwGroup>();
com.keepassdroid.database.PwGroupV4 root;
root = ((com.keepassdroid.database.PwGroupV4) (rootGroup));
root.buildChildGroupsRecursive(list);
return list;
}


@java.lang.Override
public java.util.List<com.keepassdroid.database.PwGroup> getGrpRoots() {
return rootGroup.childGroups;
}


@java.lang.Override
public java.util.List<com.keepassdroid.database.PwEntry> getEntries() {
java.util.List<com.keepassdroid.database.PwEntry> list;
list = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
com.keepassdroid.database.PwGroupV4 root;
root = ((com.keepassdroid.database.PwGroupV4) (rootGroup));
root.buildChildEntriesRecursive(list);
return list;
}


@java.lang.Override
public long getNumRounds() {
return numKeyEncRounds;
}


@java.lang.Override
public void setNumRounds(long rounds) throws java.lang.NumberFormatException {
numKeyEncRounds = rounds;
}


@java.lang.Override
public boolean appSettingsEnabled() {
return false;
}


@java.lang.Override
public com.keepassdroid.database.PwEncryptionAlgorithm getEncAlgorithm() {
return com.keepassdroid.database.PwEncryptionAlgorithm.Rjindal;
}


@java.lang.Override
public com.keepassdroid.database.PwGroupIdV4 newGroupId() {
com.keepassdroid.database.PwGroupIdV4 id;
id = new com.keepassdroid.database.PwGroupIdV4(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO);
while (true) {
id = new com.keepassdroid.database.PwGroupIdV4(java.util.UUID.randomUUID());
if (!isGroupIdUsed(id))
break;

} 
return id;
}


@java.lang.Override
public com.keepassdroid.database.PwGroup createGroup() {
return new com.keepassdroid.database.PwGroupV4();
}


@java.lang.Override
public boolean isBackup(com.keepassdroid.database.PwGroup group) {
if (!recycleBinEnabled) {
return false;
}
return group.isContainedIn(getRecycleBin());
}


@java.lang.Override
public void populateGlobals(com.keepassdroid.database.PwGroup currentGroup) {
groups.put(rootGroup.getId(), rootGroup);
super.populateGlobals(currentGroup);
}


/**
 * Ensure that the recycle bin group exists, if enabled and create it
 *  if it doesn't exist
 */
private void ensureRecycleBin() {
if (getRecycleBin() == null) {
// Create recycle bin
com.keepassdroid.database.PwGroupV4 recycleBin;
recycleBin = new com.keepassdroid.database.PwGroupV4(true, true, com.keepassdroid.database.PwDatabaseV4.RECYCLEBIN_NAME, iconFactory.getIcon(com.keepassdroid.database.PwIconStandard.TRASH_BIN));
recycleBin.enableAutoType = false;
recycleBin.enableSearching = false;
recycleBin.isExpanded = false;
addGroupTo(recycleBin, rootGroup);
recycleBinUUID = recycleBin.uuid;
}
}


@java.lang.Override
public boolean canRecycle(com.keepassdroid.database.PwGroup group) {
if (!recycleBinEnabled) {
return false;
}
com.keepassdroid.database.PwGroup recycle;
recycle = getRecycleBin();
return (recycle == null) || (!group.isContainedIn(recycle));
}


@java.lang.Override
public boolean canRecycle(com.keepassdroid.database.PwEntry entry) {
if (!recycleBinEnabled) {
return false;
}
com.keepassdroid.database.PwGroup parent;
parent = entry.getParent();
return (parent != null) && canRecycle(parent);
}


@java.lang.Override
public void recycle(com.keepassdroid.database.PwEntry entry) {
ensureRecycleBin();
com.keepassdroid.database.PwGroup parent;
parent = entry.getParent();
removeEntryFrom(entry, parent);
parent.touch(false, true);
com.keepassdroid.database.PwGroup recycleBin;
recycleBin = getRecycleBin();
addEntryTo(entry, recycleBin);
entry.touch(false, true);
entry.touchLocation();
}


@java.lang.Override
public void undoRecycle(com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.PwGroup origParent) {
com.keepassdroid.database.PwGroup recycleBin;
recycleBin = getRecycleBin();
removeEntryFrom(entry, recycleBin);
addEntryTo(entry, origParent);
}


@java.lang.Override
public void deleteEntry(com.keepassdroid.database.PwEntry entry) {
super.deleteEntry(entry);
deletedObjects.add(new com.keepassdroid.database.PwDeletedObject(entry.getUUID()));
}


@java.lang.Override
public void undoDeleteEntry(com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.PwGroup origParent) {
super.undoDeleteEntry(entry, origParent);
deletedObjects.remove(new com.keepassdroid.database.PwDeletedObject(entry.getUUID()));
}


@java.lang.Override
public com.keepassdroid.database.PwGroupV4 getRecycleBin() {
if (recycleBinUUID == null) {
return null;
}
com.keepassdroid.database.PwGroupId recycleId;
recycleId = new com.keepassdroid.database.PwGroupIdV4(recycleBinUUID);
return ((com.keepassdroid.database.PwGroupV4) (groups.get(recycleId)));
}


@java.lang.Override
public boolean isGroupSearchable(com.keepassdroid.database.PwGroup group, boolean omitBackup) {
if (!super.isGroupSearchable(group, omitBackup)) {
return false;
}
com.keepassdroid.database.PwGroupV4 g;
g = ((com.keepassdroid.database.PwGroupV4) (group));
return g.isSearchEnabled();
}


@java.lang.Override
public boolean validatePasswordEncoding(java.lang.String key) {
return true;
}


@java.lang.Override
public void initNew(java.lang.String name) {
rootGroup = new com.keepassdroid.database.PwGroupV4(true, true, name, iconFactory.getIcon(com.keepassdroid.database.PwIconStandard.FOLDER));
groups.put(rootGroup.getId(), rootGroup);
}


private java.lang.String dbNameFromPath(java.lang.String dbPath) {
java.lang.String filename;
filename = android.webkit.URLUtil.guessFileName(dbPath, null, null);
if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(filename)) {
return "KeePass Database";
}
int lastExtDot;
lastExtDot = filename.lastIndexOf(".");
if (lastExtDot == (-1)) {
return filename;
}
return filename.substring(0, lastExtDot);
}


private class GroupGetMinVer extends com.keepassdroid.database.GroupHandler<com.keepassdroid.database.PwGroup> {
public int minVer = 0;

@java.lang.Override
public boolean operate(com.keepassdroid.database.PwGroup group) {
if (group == null) {
return true;
}
com.keepassdroid.database.PwGroupV4 g4;
g4 = ((com.keepassdroid.database.PwGroupV4) (group));
if (g4.tags.length() > 0) {
minVer = java.lang.Math.max(minVer, com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1);
}
if (g4.customData.size() > 0) {
minVer = java.lang.Math.max(minVer, com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4);
}
return true;
}

}

private class EntryGetMinVer extends com.keepassdroid.database.EntryHandler<com.keepassdroid.database.PwEntry> {
public int minVer = 0;

@java.lang.Override
public boolean operate(com.keepassdroid.database.PwEntry entry) {
if (entry == null) {
return true;
}
com.keepassdroid.database.PwEntryV4 e4;
e4 = ((com.keepassdroid.database.PwEntryV4) (entry));
if (!e4.qualityCheck) {
minVer = java.lang.Math.max(minVer, com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1);
}
if (e4.customData.size() > 0) {
minVer = java.lang.Math.max(minVer, com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4);
}
return true;
}

}

public long getMinKdbxVersion() {
long minVer;
minVer = version;
com.keepassdroid.database.PwDatabaseV4.EntryGetMinVer entryHandler;
entryHandler = new com.keepassdroid.database.PwDatabaseV4.EntryGetMinVer();
com.keepassdroid.database.PwDatabaseV4.GroupGetMinVer groupHandler;
groupHandler = new com.keepassdroid.database.PwDatabaseV4.GroupGetMinVer();
if (rootGroup != null) {
rootGroup.preOrderTraverseTree(groupHandler, entryHandler);
minVer = java.lang.Math.max(minVer, groupHandler.minVer);
minVer = java.lang.Math.max(minVer, entryHandler.minVer);
if (minVer >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1) {
return minVer;
}
}
for (com.keepassdroid.database.PwIconCustom icon : customIcons) {
if ((!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(icon.name)) || (icon.lastMod != null)) {
return com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1;
}
}
for (java.lang.String key : customData.keySet()) {
if (customData.getLastMod(key) != null) {
return com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1;
}
}
if (minVer >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
return minVer;
}
if (com.keepassdroid.crypto.engine.ChaCha20Engine.CIPHER_UUID.equals(dataCipher)) {
return com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
}
if (!com.keepassdroid.crypto.keyDerivation.AesKdf.CIPHER_UUID.equals(kdfParameters.kdfUUID)) {
return com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
}
if (publicCustomData.size() > 0) {
return com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4;
}
return com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_3;
}


@java.lang.Override
public void clearCache() {
binPool.clear();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
