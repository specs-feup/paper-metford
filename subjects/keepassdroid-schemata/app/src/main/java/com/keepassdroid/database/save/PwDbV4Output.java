/* Copyright 2013-2022 Brian Pellin.

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
import com.keepassdroid.database.PwEntryV4;
import javax.crypto.CipherOutputStream;
import java.io.OutputStream;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDocNode;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemMeta;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChangeForce;
import com.keepassdroid.database.security.ProtectedString;
import org.joda.time.DateTime;
import com.keepassdroid.crypto.keyDerivation.KdfEngine;
import java.security.NoSuchAlgorithmException;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemHistoryMaxItems;
import com.keepassdroid.stream.HashedBlockOutputStream;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObjects;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbColor;
import com.keepassdroid.database.PwIconCustom;
import com.keepassdroid.crypto.CipherFactory;
import com.keepassdroid.database.PwCustomData;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem;
import com.keepassdroid.database.PwDefsV4;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemBinaries;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeDefaultSeq;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItemData;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemProtNotes;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemHistory;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemExpiryTime;
import com.keepassdroid.crypto.keyDerivation.KdfFactory;
import com.keepassdroid.stream.HmacBlockOutputStream;
import java.security.SecureRandom;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemUsageCount;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemExpires;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemHistoryMaxSize;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemKey;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeEnabled;
import com.keepassdroid.database.PwEntryV4.AutoType;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItemID;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemValue;
import com.keepassdroid.database.exception.PwDbOutputException;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeObfuscation;
import com.keepassdroid.database.CrsAlgorithm;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemQualityCheck;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemTags;
import java.util.Map;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbNameChanged;
import static com.keepassdroid.database.PwDatabaseV4XML.AttrRef;
import com.keepassdroid.database.PwDatabaseV4.MemoryProtectionConfig;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemEntryTemplatesGroup;
import com.keepassdroid.database.EntryHandler;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemEntry;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemProtTitle;
import com.keepassdroid.database.PwDatabaseV4XML;
import com.keepassdroid.utils.DateUtil;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemAutoType;
import java.util.Map.Entry;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemKeystrokeSequence;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemUuid;
import org.bouncycastle.crypto.StreamCipher;
import android.util.Base64;
import com.keepassdroid.stream.LEDataOutputStream;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbDefaultUser;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemRoot;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemLastTopVisibleGroup;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinEnabled;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemString;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemHeaderHash;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconID;
import java.io.IOException;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItem;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemIsExpanded;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemFgColor;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemLastTopVisibleEntry;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbMntncHistoryDays;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemGroupDefaultAutoTypeSeq;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemGroup;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemLastSelectedGroup;
import com.keepassdroid.database.PwCompressionAlgorithm;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemWindow;
import javax.crypto.Cipher;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChangeRec;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemEntryTemplatesGroupChanged;
import com.keepassdroid.utils.Types;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemIcon;
import static com.keepassdroid.database.PwDatabaseV4XML.AttrId;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinUuid;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemName;
import com.keepassdroid.database.PwEntry;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemLastModTime;
import java.util.zip.GZIPOutputStream;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObject;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemNotes;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemProtURL;
import static com.keepassdroid.database.PwDatabaseV4XML.ValFalse;
import com.keepassdroid.database.security.ProtectedBinary;
import com.keepassdroid.utils.MemUtil;
import java.util.List;
import java.util.UUID;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemProtUserName;
import com.keepassdroid.crypto.PwStreamCipherFactory;
import static com.keepassdroid.database.PwDatabaseV4XML.AttrCompressed;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbName;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemPreviousParentGroup;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDeletionTime;
import com.keepassdroid.utils.EmptyUtils;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbDesc;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemGenerator;
import com.keepassdroid.database.GroupHandler;
import com.keepassdroid.database.PwDatabaseV4;
import java.util.Stack;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemBinary;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemLastAccessTime;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemProtPassword;
import com.keepassdroid.database.PwGroup;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIcons;
import com.keepassdroid.database.PwDbHeader;
import org.xmlpull.v1.XmlSerializer;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemMemoryProt;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemLocationChanged;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbDefaultUserChanged;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemEnableAutoType;
import static com.keepassdroid.database.PwDatabaseV4XML.ValTrue;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeItem;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemTimes;
import com.keepassdroid.crypto.engine.CipherEngine;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemEnableSearching;
import static com.keepassdroid.database.PwDatabaseV4XML.AttrProtected;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChanged;
import com.keepassdroid.database.PwDeletedObject;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemCreationTime;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemDbDescChanged;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinChanged;
import java.util.Date;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemBgColor;
import com.keepassdroid.database.PwGroupV4;
import com.keepassdroid.database.ITimeLogger;
import static com.keepassdroid.database.PwDatabaseV4XML.ElemOverrideUrl;
import android.util.Xml;
import com.keepassdroid.database.PwDbHeaderV4;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbV4Output extends com.keepassdroid.database.save.PwDbOutput {
    static final int MUID_STATIC = getMUID();
    com.keepassdroid.database.PwDatabaseV4 mPM;

    private org.bouncycastle.crypto.StreamCipher randomStream;

    private org.xmlpull.v1.XmlSerializer xml;

    private com.keepassdroid.database.PwDbHeaderV4 header;

    private byte[] hashOfHeader;

    private byte[] headerHmac;

    private com.keepassdroid.crypto.engine.CipherEngine engine = null;

    protected PwDbV4Output(com.keepassdroid.database.PwDatabaseV4 pm, java.io.OutputStream os) {
        super(os);
        mPM = pm;
    }


    @java.lang.Override
    public void output() throws com.keepassdroid.database.exception.PwDbOutputException {
        try {
            try {
                engine = com.keepassdroid.crypto.CipherFactory.getInstance(mPM.dataCipher);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new com.keepassdroid.database.exception.PwDbOutputException("No such cipher", e);
            }
            header = ((com.keepassdroid.database.PwDbHeaderV4) (outputHeader(mOS)));
            java.io.OutputStream osPlain;
            if (header.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
                javax.crypto.CipherOutputStream cos;
                cos = attachStreamEncryptor(header, mOS);
                cos.write(header.streamStartBytes);
                com.keepassdroid.stream.HashedBlockOutputStream hashed;
                hashed = new com.keepassdroid.stream.HashedBlockOutputStream(cos);
                osPlain = hashed;
            } else {
                mOS.write(hashOfHeader);
                mOS.write(headerHmac);
                com.keepassdroid.stream.HmacBlockOutputStream hbos;
                hbos = new com.keepassdroid.stream.HmacBlockOutputStream(mOS, mPM.hmacKey);
                osPlain = attachStreamEncryptor(header, hbos);
            }
            java.io.OutputStream osXml;
            try {
                if (mPM.compressionAlgorithm == com.keepassdroid.database.PwCompressionAlgorithm.Gzip) {
                    osXml = new java.util.zip.GZIPOutputStream(osPlain);
                } else {
                    osXml = osPlain;
                }
                if (header.version >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
                    com.keepassdroid.database.save.PwDbInnerHeaderOutputV4 ihOut;
                    ihOut = new com.keepassdroid.database.save.PwDbInnerHeaderOutputV4(((com.keepassdroid.database.PwDatabaseV4) (mPM)), header, osXml);
                    ihOut.output();
                }
                outputDatabase(osXml);
                osXml.close();
            } catch (java.lang.IllegalArgumentException e) {
                throw new com.keepassdroid.database.exception.PwDbOutputException(e);
            } catch (java.lang.IllegalStateException e) {
                throw new com.keepassdroid.database.exception.PwDbOutputException(e);
            }
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException(e);
        }
    }


    private class GroupWriter extends com.keepassdroid.database.GroupHandler<com.keepassdroid.database.PwGroup> {
        private java.util.Stack<com.keepassdroid.database.PwGroupV4> groupStack;

        public GroupWriter(java.util.Stack<com.keepassdroid.database.PwGroupV4> gs) {
            groupStack = gs;
        }


        @java.lang.Override
        public boolean operate(com.keepassdroid.database.PwGroup g) {
            com.keepassdroid.database.PwGroupV4 group;
            group = ((com.keepassdroid.database.PwGroupV4) (g));
            assert group != null;
            while (true) {
                try {
                    if (group.parent == groupStack.peek()) {
                        groupStack.push(group);
                        startGroup(group);
                        break;
                    } else {
                        groupStack.pop();
                        if (groupStack.size() <= 0)
                            return false;

                        endGroup();
                    }
                } catch (java.io.IOException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } 
            return true;
        }

    }

    private class EntryWriter extends com.keepassdroid.database.EntryHandler<com.keepassdroid.database.PwEntry> {
        @java.lang.Override
        public boolean operate(com.keepassdroid.database.PwEntry e) {
            com.keepassdroid.database.PwEntryV4 entry;
            entry = ((com.keepassdroid.database.PwEntryV4) (e));
            assert entry != null;
            try {
                writeEntry(entry, false);
            } catch (java.io.IOException ex) {
                throw new java.lang.RuntimeException(ex);
            }
            return true;
        }

    }

    private void outputDatabase(java.io.OutputStream os) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        xml = android.util.Xml.newSerializer();
        xml.setOutput(os, "UTF-8");
        xml.startDocument("UTF-8", true);
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemDocNode);
        writeMeta();
        com.keepassdroid.database.PwGroupV4 root;
        root = ((com.keepassdroid.database.PwGroupV4) (mPM.rootGroup));
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemRoot);
        startGroup(root);
        java.util.Stack<com.keepassdroid.database.PwGroupV4> groupStack;
        groupStack = new java.util.Stack<com.keepassdroid.database.PwGroupV4>();
        groupStack.push(root);
        if (!root.preOrderTraverseTree(new com.keepassdroid.database.save.PwDbV4Output.GroupWriter(groupStack), new com.keepassdroid.database.save.PwDbV4Output.EntryWriter()))
            throw new java.lang.RuntimeException("Writing groups failed");

        while (groupStack.size() > 1) {
            xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemGroup);
            groupStack.pop();
        } 
        endGroup();
        writeList(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObjects, mPM.deletedObjects);
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemRoot);
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemDocNode);
        xml.endDocument();
    }


    private void writeMeta() throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemMeta);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemGenerator, mPM.localizedAppName);
        if (hashOfHeader != null) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemHeaderHash, android.util.Base64.encodeToString(hashOfHeader, android.util.Base64.NO_WRAP));
        }
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbName, mPM.name, true);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbNameChanged, mPM.nameChanged);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDesc, mPM.description, true);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDescChanged, mPM.descriptionChanged);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDefaultUser, mPM.defaultUserName, true);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDefaultUserChanged, mPM.defaultUserNameChanged);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbMntncHistoryDays, mPM.maintenanceHistoryDays);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbColor, mPM.color);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChanged, mPM.keyLastChanged);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChangeRec, mPM.keyChangeRecDays);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChangeForce, mPM.keyChangeForceDays);
        writeList(com.keepassdroid.database.PwDatabaseV4XML.ElemMemoryProt, mPM.memoryProtection);
        writeCustomIconList();
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinEnabled, mPM.recycleBinEnabled);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinUuid, mPM.recycleBinUUID);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinChanged, mPM.recycleBinChanged);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemEntryTemplatesGroup, mPM.entryTemplatesGroup);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemEntryTemplatesGroupChanged, mPM.entryTemplatesGroupChanged);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemHistoryMaxItems, mPM.historyMaxItems);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemHistoryMaxSize, mPM.historyMaxSize);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLastSelectedGroup, mPM.lastSelectedGroup);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLastTopVisibleGroup, mPM.lastTopVisibleGroup);
        if (header.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            writeBinPool();
        }
        writeList(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData, mPM.customData);
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemMeta);
    }


    private javax.crypto.CipherOutputStream attachStreamEncryptor(com.keepassdroid.database.PwDbHeaderV4 header, java.io.OutputStream os) throws com.keepassdroid.database.exception.PwDbOutputException {
        javax.crypto.Cipher cipher;
        try {
            // mPM.makeFinalKey(header.masterSeed, mPM.kdfParameters);
            cipher = engine.getCipher(javax.crypto.Cipher.ENCRYPT_MODE, mPM.finalKey, header.encryptionIV);
        } catch (java.lang.Exception e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Invalid algorithm.", e);
        }
        javax.crypto.CipherOutputStream cos;
        cos = new javax.crypto.CipherOutputStream(os, cipher);
        return cos;
    }


    @java.lang.Override
    protected java.security.SecureRandom setIVs(com.keepassdroid.database.PwDbHeader header) throws com.keepassdroid.database.exception.PwDbOutputException {
        java.security.SecureRandom random;
        random = super.setIVs(header);
        com.keepassdroid.database.PwDbHeaderV4 h;
        h = ((com.keepassdroid.database.PwDbHeaderV4) (header));
        random.nextBytes(h.masterSeed);
        int ivLength;
        ivLength = engine.ivLength();
        if (ivLength != h.encryptionIV.length) {
            h.encryptionIV = new byte[ivLength];
        }
        random.nextBytes(h.encryptionIV);
        java.util.UUID kdfUUID;
        kdfUUID = mPM.kdfParameters.kdfUUID;
        com.keepassdroid.crypto.keyDerivation.KdfEngine kdf;
        kdf = com.keepassdroid.crypto.keyDerivation.KdfFactory.get(kdfUUID);
        kdf.randomize(mPM.kdfParameters);
        if (h.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            h.innerRandomStream = com.keepassdroid.database.CrsAlgorithm.Salsa20;
            h.innerRandomStreamKey = new byte[32];
        } else {
            h.innerRandomStream = com.keepassdroid.database.CrsAlgorithm.ChaCha20;
            h.innerRandomStreamKey = new byte[64];
        }
        random.nextBytes(h.innerRandomStreamKey);
        randomStream = com.keepassdroid.crypto.PwStreamCipherFactory.getInstance(h.innerRandomStream, h.innerRandomStreamKey);
        if (randomStream == null) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Invalid random cipher");
        }
        if (h.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            random.nextBytes(h.streamStartBytes);
        }
        return random;
    }


    @java.lang.Override
    public com.keepassdroid.database.PwDbHeader outputHeader(java.io.OutputStream os) throws com.keepassdroid.database.exception.PwDbOutputException {
        com.keepassdroid.database.PwDbHeaderV4 header;
        header = new com.keepassdroid.database.PwDbHeaderV4(mPM);
        header.version = mPM.getMinKdbxVersion();
        setIVs(header);
        com.keepassdroid.database.save.PwDbHeaderOutputV4 pho;
        pho = new com.keepassdroid.database.save.PwDbHeaderOutputV4(mPM, header, os);
        try {
            pho.output();
        } catch (java.io.IOException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Failed to output the header.", e);
        }
        hashOfHeader = pho.getHashOfHeader();
        headerHmac = pho.headerHmac;
        return header;
    }


    private void startGroup(com.keepassdroid.database.PwGroupV4 group) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemGroup);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemUuid, group.uuid);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemName, group.name);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemNotes, group.notes);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemIcon, group.icon.iconId);
        if (!group.customIcon.equals(com.keepassdroid.database.PwIconCustom.ZERO)) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconID, group.customIcon.uuid);
        }
        writeList(com.keepassdroid.database.PwDatabaseV4XML.ElemTimes, group);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemIsExpanded, group.isExpanded);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemGroupDefaultAutoTypeSeq, group.defaultAutoTypeSequence);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemEnableAutoType, group.enableAutoType);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemEnableSearching, group.enableSearching);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLastTopVisibleEntry, group.lastTopVisibleEntry);
        if (header.version >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1) {
            if (!group.prevParentGroup.equals(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO)) {
                writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemPreviousParentGroup, group.prevParentGroup);
            }
            if (!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(group.tags)) {
                writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemTags, group.tags);
            }
        }
    }


    private void endGroup() throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemGroup);
    }


    private void writeEntry(com.keepassdroid.database.PwEntryV4 entry, boolean isHistory) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert entry != null;
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemEntry);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemUuid, entry.uuid);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemIcon, entry.icon.iconId);
        if (!entry.customIcon.equals(com.keepassdroid.database.PwIconCustom.ZERO)) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconID, entry.customIcon.uuid);
        }
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemFgColor, entry.foregroundColor);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemBgColor, entry.backgroupColor);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemOverrideUrl, entry.overrideURL);
        if ((header.version >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1) && (!entry.qualityCheck)) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemQualityCheck, false);
        }
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemTags, entry.tags);
        if ((header.version >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1) && (!entry.prevParentGroup.equals(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO))) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemPreviousParentGroup, entry.prevParentGroup);
        }
        writeList(com.keepassdroid.database.PwDatabaseV4XML.ElemTimes, entry);
        writeList(entry.strings, true);
        writeList(entry.binaries);
        writeList(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoType, entry.autoType);
        if (!isHistory) {
            writeList(com.keepassdroid.database.PwDatabaseV4XML.ElemHistory, entry.history, true);
        } else {
            assert entry.history.size() == 0;
        }
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemEntry);
    }


    private void writeObject(java.lang.String key, com.keepassdroid.database.security.ProtectedBinary value, boolean allowRef) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (key != null) && (value != null);
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemBinary);
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemKey);
        xml.text(safeXmlString(key));
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemKey);
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemValue);
        java.lang.String strRef;
        strRef = null;
        if (allowRef) {
            int ref;
            ref = mPM.binPool.poolFind(value);
            strRef = java.lang.Integer.toString(ref);
        }
        if (strRef != null) {
            xml.attribute(null, com.keepassdroid.database.PwDatabaseV4XML.AttrRef, strRef);
        } else {
            subWriteValue(value);
        }
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemValue);
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemBinary);
    }


    private void subWriteValue(com.keepassdroid.database.security.ProtectedBinary value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        int valLength;
        valLength = ((int) (value.length()));
        if (valLength > 0) {
            byte[] buffer;
            buffer = new byte[valLength];
            value.getData().read(buffer, 0, valLength);
            if (value.isProtected()) {
                xml.attribute(null, com.keepassdroid.database.PwDatabaseV4XML.AttrProtected, com.keepassdroid.database.PwDatabaseV4XML.ValTrue);
                byte[] encoded;
                encoded = new byte[valLength];
                randomStream.processBytes(buffer, 0, valLength, encoded, 0);
                xml.text(android.util.Base64.encodeToString(encoded, android.util.Base64.NO_WRAP));
            } else if (mPM.compressionAlgorithm == com.keepassdroid.database.PwCompressionAlgorithm.Gzip) {
                xml.attribute(null, com.keepassdroid.database.PwDatabaseV4XML.AttrCompressed, com.keepassdroid.database.PwDatabaseV4XML.ValTrue);
                byte[] compressData;
                compressData = com.keepassdroid.utils.MemUtil.compress(buffer);
                xml.text(android.util.Base64.encodeToString(compressData, android.util.Base64.NO_WRAP));
            } else {
                xml.text(android.util.Base64.encodeToString(buffer, android.util.Base64.NO_WRAP));
            }
        }
    }


    private void writeObject(java.lang.String name, java.lang.String value, boolean filterXmlChars) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (value != null);
        xml.startTag(null, name);
        if (filterXmlChars) {
            value = safeXmlString(value);
        }
        xml.text(value);
        xml.endTag(null, name);
    }


    private void writeObject(java.lang.String name, java.lang.String value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        writeObject(name, value, false);
    }


    private void writeObject(java.lang.String name, java.util.Date value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        if (header.version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            writeObject(name, com.keepassdroid.database.PwDatabaseV4XML.dateFormatter.get().format(value));
        } else {
            org.joda.time.DateTime dt;
            dt = new org.joda.time.DateTime(value);
            long seconds;
            seconds = com.keepassdroid.utils.DateUtil.convertDateToKDBX4Time(dt);
            byte[] buf;
            buf = com.keepassdroid.stream.LEDataOutputStream.writeLongBuf(seconds);
            java.lang.String b64;
            b64 = android.util.Base64.encodeToString(buf, android.util.Base64.NO_WRAP);
            writeObject(name, b64);
        }
    }


    private void writeObject(java.lang.String name, long value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        writeObject(name, java.lang.String.valueOf(value));
    }


    private void writeObject(java.lang.String name, java.lang.Boolean value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        java.lang.String text;
        if (value == null) {
            text = "null";
        } else if (value) {
            text = com.keepassdroid.database.PwDatabaseV4XML.ValTrue;
        } else {
            text = com.keepassdroid.database.PwDatabaseV4XML.ValFalse;
        }
        writeObject(name, text);
    }


    private void writeObject(java.lang.String name, java.util.UUID uuid) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        byte[] data;
        data = com.keepassdroid.utils.Types.UUIDtoBytes(uuid);
        writeObject(name, android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP));
    }


    private void writeObject(java.lang.String name, java.lang.String keyName, java.lang.String keyValue, java.lang.String valueName, java.lang.String valueValue, java.util.Date lastMod) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        xml.startTag(null, name);
        xml.startTag(null, keyName);
        xml.text(safeXmlString(keyValue));
        xml.endTag(null, keyName);
        xml.startTag(null, valueName);
        xml.text(safeXmlString(valueValue));
        xml.endTag(null, valueName);
        if (lastMod != null) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLastModTime, lastMod);
        }
        xml.endTag(null, name);
    }


    private void writeList(java.lang.String name, com.keepassdroid.database.PwEntryV4.AutoType autoType) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (autoType != null);
        xml.startTag(null, name);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeEnabled, autoType.enabled);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeObfuscation, autoType.obfuscationOptions);
        if (autoType.defaultSequence.length() > 0) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeDefaultSeq, autoType.defaultSequence, true);
        }
        for (java.util.Map.Entry<java.lang.String, java.lang.String> pair : autoType.entrySet()) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeItem, com.keepassdroid.database.PwDatabaseV4XML.ElemWindow, pair.getKey(), com.keepassdroid.database.PwDatabaseV4XML.ElemKeystrokeSequence, pair.getValue(), null);
        }
        xml.endTag(null, name);
    }


    private void writeList(java.util.Map<java.lang.String, com.keepassdroid.database.security.ProtectedString> strings, boolean isEntryString) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert strings != null;
        for (java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString> pair : strings.entrySet()) {
            writeObject(pair.getKey(), pair.getValue(), isEntryString);
        }
    }


    private void writeObject(java.lang.String key, com.keepassdroid.database.security.ProtectedString value, boolean isEntryString) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (key != null) && (value != null);
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemString);
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemKey);
        xml.text(safeXmlString(key));
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemKey);
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemValue);
        boolean protect;
        protect = value.isProtected();
        if (isEntryString) {
            if (key.equals(com.keepassdroid.database.PwDefsV4.TITLE_FIELD)) {
                protect = mPM.memoryProtection.protectTitle;
            } else if (key.equals(com.keepassdroid.database.PwDefsV4.USERNAME_FIELD)) {
                protect = mPM.memoryProtection.protectUserName;
            } else if (key.equals(com.keepassdroid.database.PwDefsV4.PASSWORD_FIELD)) {
                protect = mPM.memoryProtection.protectPassword;
            } else if (key.equals(com.keepassdroid.database.PwDefsV4.URL_FIELD)) {
                protect = mPM.memoryProtection.protectUrl;
            } else if (key.equals(com.keepassdroid.database.PwDefsV4.NOTES_FIELD)) {
                protect = mPM.memoryProtection.protectNotes;
            }
        }
        if (protect) {
            xml.attribute(null, com.keepassdroid.database.PwDatabaseV4XML.AttrProtected, com.keepassdroid.database.PwDatabaseV4XML.ValTrue);
            byte[] data;
            data = value.toString().getBytes("UTF-8");
            int valLength;
            valLength = data.length;
            if (valLength > 0) {
                byte[] encoded;
                encoded = new byte[valLength];
                randomStream.processBytes(data, 0, valLength, encoded, 0);
                xml.text(android.util.Base64.encodeToString(encoded, android.util.Base64.NO_WRAP));
            }
        } else {
            xml.text(safeXmlString(value.toString()));
        }
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemValue);
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemString);
    }


    private void writeObject(java.lang.String name, com.keepassdroid.database.PwDeletedObject value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (value != null);
        xml.startTag(null, name);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemUuid, value.uuid);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletionTime, value.getDeletionTime());
        xml.endTag(null, name);
    }


    private void writeList(java.util.Map<java.lang.String, com.keepassdroid.database.security.ProtectedBinary> binaries) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert binaries != null;
        for (java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedBinary> pair : binaries.entrySet()) {
            writeObject(pair.getKey(), pair.getValue(), true);
        }
    }


    private void writeList(java.lang.String name, java.util.List<com.keepassdroid.database.PwDeletedObject> value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (value != null);
        xml.startTag(null, name);
        for (com.keepassdroid.database.PwDeletedObject pdo : value) {
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObject, pdo);
        }
        xml.endTag(null, name);
    }


    private void writeList(java.lang.String name, com.keepassdroid.database.PwDatabaseV4.MemoryProtectionConfig value) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (value != null);
        xml.startTag(null, name);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemProtTitle, value.protectTitle);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemProtUserName, value.protectUserName);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemProtPassword, value.protectPassword);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemProtURL, value.protectUrl);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemProtNotes, value.protectNotes);
        xml.endTag(null, name);
    }


    private void writeList(java.lang.String name, com.keepassdroid.database.PwCustomData customData) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (customData != null);
        xml.startTag(null, name);
        for (java.util.Map.Entry<java.lang.String, java.lang.String> pair : customData.entrySet()) {
            java.lang.String key;
            key = pair.getKey();
            java.util.Date lastMod;
            lastMod = null;
            if (header.version >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1) {
                lastMod = customData.getLastMod(key);
            }
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem, com.keepassdroid.database.PwDatabaseV4XML.ElemKey, key, com.keepassdroid.database.PwDatabaseV4XML.ElemValue, pair.getValue(), lastMod);
        }
        xml.endTag(null, name);
    }


    private void writeList(java.lang.String name, com.keepassdroid.database.ITimeLogger it) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (it != null);
        xml.startTag(null, name);
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLastModTime, it.getLastModificationTime());
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemCreationTime, it.getCreationTime());
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLastAccessTime, it.getLastAccessTime());
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemExpiryTime, it.getExpiryTime());
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemExpires, it.expires());
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemUsageCount, it.getUsageCount());
        writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLocationChanged, it.getLocationChanged());
        xml.endTag(null, name);
    }


    private void writeList(java.lang.String name, java.util.List<com.keepassdroid.database.PwEntryV4> value, boolean isHistory) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        assert (name != null) && (value != null);
        xml.startTag(null, name);
        for (com.keepassdroid.database.PwEntryV4 entry : value) {
            writeEntry(entry, isHistory);
        }
        xml.endTag(null, name);
    }


    private void writeCustomIconList() throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        java.util.List<com.keepassdroid.database.PwIconCustom> customIcons;
        customIcons = mPM.customIcons;
        if (customIcons.size() == 0)
            return;

        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIcons);
        for (com.keepassdroid.database.PwIconCustom icon : customIcons) {
            xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItem);
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItemID, icon.uuid);
            writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItemData, android.util.Base64.encodeToString(icon.imageData, android.util.Base64.NO_WRAP));
            if (header.version >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4_1) {
                if (!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(icon.name)) {
                    writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemName, icon.name, true);
                }
                if (icon.lastMod != null) {
                    writeObject(com.keepassdroid.database.PwDatabaseV4XML.ElemLastModTime, icon.lastMod);
                }
            }
            xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItem);
        }
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIcons);
    }


    private void writeBinPool() throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemBinaries);
        for (java.util.Map.Entry<java.lang.Integer, com.keepassdroid.database.security.ProtectedBinary> pair : mPM.binPool.entrySet()) {
            xml.startTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemBinary);
            xml.attribute(null, com.keepassdroid.database.PwDatabaseV4XML.AttrId, java.lang.Integer.toString(pair.getKey()));
            subWriteValue(pair.getValue());
            xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemBinary);
        }
        xml.endTag(null, com.keepassdroid.database.PwDatabaseV4XML.ElemBinaries);
    }


    private java.lang.String safeXmlString(java.lang.String text) {
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(text)) {
            return text;
        }
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder();
        char ch;
        for (int i = 0; i < text.length(); i++) {
            ch = text.charAt(i);
            if ((((((ch >= 0x20) && (ch <= 0xd7ff)) || (ch == 0x9)) || (ch == 0xa)) || (ch == 0xd)) || ((ch >= 0xe000) && (ch <= 0xfffd))) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
