/* Copyright 2009-2022 Brian Pellin.

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
package com.keepassdroid.database.load;
import com.keepassdroid.utils.Types;
import com.keepassdroid.database.PwEntryV4;
import com.keepassdroid.UpdateStatus;
import java.io.OutputStream;
import com.keepassdroid.database.security.ProtectedString;
import java.security.NoSuchAlgorithmException;
import com.keepassdroid.stream.BetterCipherInputStream;
import com.keepassdroid.utils.Util;
import org.xmlpull.v1.XmlPullParser;
import com.keepassdroid.database.PwIconCustom;
import com.keepassdroid.crypto.CipherFactory;
import com.keepassdroid.database.security.ProtectedBinary;
import com.keepassdroid.utils.MemUtil;
import com.keepassdroid.database.exception.InvalidPasswordException;
import org.xmlpull.v1.XmlPullParserException;
import java.util.UUID;
import com.keepassdroid.crypto.PwStreamCipherFactory;
import com.keepassdroid.database.exception.InvalidDBException;
import java.security.InvalidAlgorithmParameterException;
import com.keepassdroid.utils.EmptyUtils;
import java.io.UnsupportedEncodingException;
import com.keepassdroid.database.PwDatabaseV4;
import java.util.Stack;
import java.util.TimeZone;
import com.keepassdroid.stream.HmacBlockInputStream;
import static com.keepassdroid.database.PwDatabaseV4XML.*;
import java.io.File;
import java.util.Arrays;
import com.keepassdroid.database.PwDatabaseV4XML;
import com.keepassdroid.stream.LEDataInputStream;
import com.keepassdroid.utils.DateUtil;
import java.util.zip.GZIPInputStream;
import com.keepassdroid.crypto.engine.CipherEngine;
import org.bouncycastle.crypto.StreamCipher;
import android.util.Base64;
import com.keepassdroid.stream.HashedBlockInputStream;
import com.keepassdroid.database.PwDeletedObject;
import com.keepassdroid.database.exception.ArcFourException;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.io.InputStream;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.util.Date;
import com.keepassdroid.database.PwGroupV4;
import com.keepassdroid.database.ITimeLogger;
import com.keepassdroid.database.PwCompressionAlgorithm;
import com.keepassdroid.database.PwDbHeaderV4;
import java.text.ParseException;
import javax.crypto.Cipher;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImporterV4 extends com.keepassdroid.database.load.Importer {
    static final int MUID_STATIC = getMUID();
    private org.bouncycastle.crypto.StreamCipher randomStream;

    private com.keepassdroid.database.PwDatabaseV4 db;

    private byte[] hashOfHeader = null;

    private byte[] pbHeader = null;

    private long version;

    private int binNum = 0;

    java.util.Calendar utcCal;

    private java.io.File streamDir;

    public ImporterV4(java.io.File streamDir) {
        this.utcCal = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));
        this.streamDir = streamDir;
    }


    protected com.keepassdroid.database.PwDatabaseV4 createDB() {
        return new com.keepassdroid.database.PwDatabaseV4();
    }


    @java.lang.Override
    public com.keepassdroid.database.PwDatabaseV4 openDatabase(java.io.InputStream inStream, java.lang.String password, java.io.InputStream keyInputStream) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        return openDatabase(inStream, password, keyInputStream, new com.keepassdroid.UpdateStatus(), 0);
    }


    @java.lang.Override
    public com.keepassdroid.database.PwDatabaseV4 openDatabase(java.io.InputStream inStream, java.lang.String password, java.io.InputStream keyInputStream, com.keepassdroid.UpdateStatus status, long roundsFix) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        db = createDB();
        com.keepassdroid.database.PwDbHeaderV4 header;
        header = new com.keepassdroid.database.PwDbHeaderV4(db);
        db.binPool.clear();
        com.keepassdroid.database.PwDbHeaderV4.HeaderAndHash hh;
        hh = header.loadFromFile(inStream);
        version = header.version;
        db.version = version;
        hashOfHeader = hh.hash;
        pbHeader = hh.header;
        db.setMasterKey(password, keyInputStream);
        db.makeFinalKey(header.masterSeed, db.kdfParameters, roundsFix);
        com.keepassdroid.crypto.engine.CipherEngine engine;
        javax.crypto.Cipher cipher;
        try {
            engine = com.keepassdroid.crypto.CipherFactory.getInstance(db.dataCipher);
            db.dataEngine = engine;
            cipher = engine.getCipher(javax.crypto.Cipher.DECRYPT_MODE, db.finalKey, header.encryptionIV);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("Invalid algorithm.");
        } catch (javax.crypto.NoSuchPaddingException e) {
            throw new java.io.IOException("Invalid algorithm.");
        } catch (java.security.InvalidKeyException e) {
            throw new java.io.IOException("Invalid algorithm.");
        } catch (java.security.InvalidAlgorithmParameterException e) {
            throw new java.io.IOException("Invalid algorithm.");
        }
        java.io.InputStream isPlain;
        if (version < com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
            java.io.InputStream decrypted;
            decrypted = AttachCipherStream(inStream, cipher);
            com.keepassdroid.stream.LEDataInputStream dataDecrypted;
            dataDecrypted = new com.keepassdroid.stream.LEDataInputStream(decrypted);
            byte[] storedStartBytes;
            storedStartBytes = null;
            try {
                storedStartBytes = dataDecrypted.readBytes(32);
                if ((storedStartBytes == null) || (storedStartBytes.length != 32)) {
                    throw new com.keepassdroid.database.exception.InvalidPasswordException();
                }
            } catch (java.io.IOException e) {
                throw new com.keepassdroid.database.exception.InvalidPasswordException();
            }
            if (!java.util.Arrays.equals(storedStartBytes, header.streamStartBytes)) {
                throw new com.keepassdroid.database.exception.InvalidPasswordException();
            }
            isPlain = new com.keepassdroid.stream.HashedBlockInputStream(dataDecrypted);
        } else {
            // KDBX 4
            com.keepassdroid.stream.LEDataInputStream isData;
            isData = new com.keepassdroid.stream.LEDataInputStream(inStream);
            byte[] storedHash;
            storedHash = isData.readBytes(32);
            if (!java.util.Arrays.equals(storedHash, hashOfHeader)) {
                throw new com.keepassdroid.database.exception.InvalidDBException();
            }
            byte[] hmacKey;
            hmacKey = db.hmacKey;
            byte[] headerHmac;
            headerHmac = com.keepassdroid.database.PwDbHeaderV4.computeHeaderHmac(pbHeader, hmacKey);
            byte[] storedHmac;
            storedHmac = isData.readBytes(32);
            if ((storedHmac == null) || (storedHmac.length != 32)) {
                throw new com.keepassdroid.database.exception.InvalidDBException();
            }
            // Mac doesn't match
            if (!java.util.Arrays.equals(headerHmac, storedHmac)) {
                throw new com.keepassdroid.database.exception.InvalidDBException();
            }
            com.keepassdroid.stream.HmacBlockInputStream hmIs;
            hmIs = new com.keepassdroid.stream.HmacBlockInputStream(isData, true, hmacKey);
            isPlain = AttachCipherStream(hmIs, cipher);
        }
        java.io.InputStream isXml;
        if (db.compressionAlgorithm == com.keepassdroid.database.PwCompressionAlgorithm.Gzip) {
            isXml = new java.util.zip.GZIPInputStream(isPlain);
        } else {
            isXml = isPlain;
        }
        if (version >= header.FILE_VERSION_32_4) {
            LoadInnerHeader(isXml, header);
        }
        if (header.innerRandomStreamKey == null) {
            assert false;
            throw new java.io.IOException("Invalid stream key.");
        }
        randomStream = com.keepassdroid.crypto.PwStreamCipherFactory.getInstance(header.innerRandomStream, header.innerRandomStreamKey);
        if (randomStream == null) {
            throw new com.keepassdroid.database.exception.ArcFourException();
        }
        ReadXmlStreamed(isXml);
        return db;
    }


    private java.io.InputStream AttachCipherStream(java.io.InputStream is, javax.crypto.Cipher cipher) {
        switch(MUID_STATIC) {
            // ImporterV4_0_BinaryMutator
            case 152: {
                return new com.keepassdroid.stream.BetterCipherInputStream(is, cipher, 50 / 1024);
            }
            default: {
            return new com.keepassdroid.stream.BetterCipherInputStream(is, cipher, 50 * 1024);
            }
    }
}


private void LoadInnerHeader(java.io.InputStream is, com.keepassdroid.database.PwDbHeaderV4 header) throws java.io.IOException {
    com.keepassdroid.stream.LEDataInputStream lis;
    lis = new com.keepassdroid.stream.LEDataInputStream(is);
    while (true) {
        if (!ReadInnerHeader(lis, header))
            break;

    } 
}


private boolean ReadInnerHeader(com.keepassdroid.stream.LEDataInputStream lis, com.keepassdroid.database.PwDbHeaderV4 header) throws java.io.IOException {
    byte fieldId;
    fieldId = ((byte) (lis.read()));
    int size;
    size = lis.readInt();
    if (size < 0)
        throw new java.io.IOException("Corrupted file");

    byte[] data;
    data = new byte[0];
    if (size > 0) {
        if (fieldId != com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.Binary)
            data = lis.readBytes(size);

    }
    boolean result;
    result = true;
    switch (fieldId) {
        case com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.EndOfHeader :
            result = false;
            break;
        case com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.InnerRandomStreamID :
            header.setRandomStreamID(data);
            break;
        case com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.InnerRandomstreamKey :
            header.innerRandomStreamKey = data;
            break;
        case com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.Binary :
            byte flag;
            flag = lis.readBytes(1)[0];
            boolean protectedFlag;
            protectedFlag = (flag & com.keepassdroid.database.PwDbHeaderV4.KdbxBinaryFlags.Protected) != com.keepassdroid.database.PwDbHeaderV4.KdbxBinaryFlags.None;
            // Read the serialized binary
            int binaryKey;
            binaryKey = db.binPool.findUnusedKey();
            java.io.File file;
            file = new java.io.File(streamDir, java.lang.String.valueOf(binaryKey));
            com.keepassdroid.database.security.ProtectedBinary protectedBinary;
            switch(MUID_STATIC) {
                // ImporterV4_1_BinaryMutator
                case 1152: {
                    protectedBinary = new com.keepassdroid.database.security.ProtectedBinary(protectedFlag, file, size + 1);
                    break;
                }
                default: {
                protectedBinary = new com.keepassdroid.database.security.ProtectedBinary(protectedFlag, file, size - 1);
                break;
            }
        }
        final java.io.OutputStream outputStream;
        outputStream = protectedBinary.getOutputStream();
        switch(MUID_STATIC) {
            // ImporterV4_2_BinaryMutator
            case 2152: {
                com.keepassdroid.utils.Util.copyStream(lis, outputStream, size + 1);
                break;
            }
            default: {
            com.keepassdroid.utils.Util.copyStream(lis, outputStream, size - 1);
            break;
        }
    }
    outputStream.close();
    db.binPool.poolAdd(protectedBinary);
    break;
default :
    assert false;
    break;
}
return result;
}


private enum KdbContext {

Null,
KeePassFile,
Meta,
Root,
MemoryProtection,
CustomIcons,
CustomIcon,
CustomData,
CustomDataItem,
RootDeletedObjects,
DeletedObject,
Group,
GroupTimes,
GroupCustomData,
GroupCustomDataItem,
Entry,
EntryTimes,
EntryString,
EntryBinary,
EntryAutoType,
EntryAutoTypeItem,
EntryHistory,
EntryCustomData,
EntryCustomDataItem,
Binaries;}

private static final long DEFAULT_HISTORY_DAYS = 365;

private boolean readNextNode = true;

private java.util.Stack<com.keepassdroid.database.PwGroupV4> ctxGroups = new java.util.Stack<com.keepassdroid.database.PwGroupV4>();

private com.keepassdroid.database.PwGroupV4 ctxGroup = null;

private com.keepassdroid.database.PwEntryV4 ctxEntry = null;

private java.lang.String ctxStringName = null;

private com.keepassdroid.database.security.ProtectedString ctxStringValue = null;

private java.lang.String ctxBinaryName = null;

private com.keepassdroid.database.security.ProtectedBinary ctxBinaryValue = null;

private java.lang.String ctxATName = null;

private java.lang.String ctxATSeq = null;

private boolean entryInHistory = false;

private com.keepassdroid.database.PwEntryV4 ctxHistoryBase = null;

private com.keepassdroid.database.PwDeletedObject ctxDeletedObject = null;

private java.util.UUID customIconID = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

private byte[] customIconData;

private java.lang.String customIconName = null;

private java.util.Date customIconLastMod = null;

private java.lang.String customDataKey = null;

private java.lang.String customDataValue = null;

private java.util.Date customDataLastMod = null;

private java.lang.String groupCustomDataKey = null;

private java.lang.String groupCustomDataValue = null;

private java.lang.String entryCustomDataKey = null;

private java.lang.String entryCustomDataValue = null;

private void ReadXmlStreamed(java.io.InputStream readerStream) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
try {
ReadDocumentStreamed(com.keepassdroid.database.load.ImporterV4.CreatePullParser(readerStream));
} catch (org.xmlpull.v1.XmlPullParserException e) {
e.printStackTrace();
throw new java.io.IOException(e.getLocalizedMessage());
}
}


private static org.xmlpull.v1.XmlPullParser CreatePullParser(java.io.InputStream readerStream) throws org.xmlpull.v1.XmlPullParserException {
org.xmlpull.v1.XmlPullParserFactory xppf;
xppf = org.xmlpull.v1.XmlPullParserFactory.newInstance();
xppf.setNamespaceAware(false);
org.xmlpull.v1.XmlPullParser xpp;
xpp = xppf.newPullParser();
xpp.setInput(readerStream, null);
return xpp;
}


private void ReadDocumentStreamed(org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
ctxGroups.clear();
com.keepassdroid.database.load.ImporterV4.KdbContext ctx;
ctx = com.keepassdroid.database.load.ImporterV4.KdbContext.Null;
readNextNode = true;
while (true) {
if (readNextNode) {
    if (xpp.next() == org.xmlpull.v1.XmlPullParser.END_DOCUMENT)
        break;

} else {
    readNextNode = true;
}
switch (xpp.getEventType()) {
    case org.xmlpull.v1.XmlPullParser.START_TAG :
        ctx = ReadXmlElement(ctx, xpp);
        break;
    case org.xmlpull.v1.XmlPullParser.END_TAG :
        ctx = EndXmlElement(ctx, xpp);
        break;
    case org.xmlpull.v1.XmlPullParser.TEXT :
        // Only expect all whitespace text nodes
        java.lang.String text;
        text = xpp.getText();
        assert text.trim().length() == 0;
        break;
    default :
        assert false;
        break;
}
} 
// Error checks
if (ctx != com.keepassdroid.database.load.ImporterV4.KdbContext.Null)
throw new java.io.IOException("Malformed");

if (ctxGroups.size() != 0)
throw new java.io.IOException("Malformed");

}


private com.keepassdroid.database.load.ImporterV4.KdbContext ReadXmlElement(com.keepassdroid.database.load.ImporterV4.KdbContext ctx, org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
java.lang.String name;
name = xpp.getName();
switch (ctx) {
case Null :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDocNode)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.KeePassFile, xpp);
    } else
        ReadUnknown(xpp);

    break;
case KeePassFile :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemMeta)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.Meta, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemRoot)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.Root, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case Meta :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemGenerator)) {
        ReadString(xpp)// Ignore
        ;// Ignore

    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemHeaderHash)) {
        java.lang.String encodedHash;
        encodedHash = ReadString(xpp);
        if ((!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(encodedHash)) && (hashOfHeader != null)) {
            byte[] hash;
            hash = android.util.Base64.decode(encodedHash, android.util.Base64.NO_WRAP);
            if (!java.util.Arrays.equals(hash, hashOfHeader)) {
                throw new com.keepassdroid.database.exception.InvalidDBException();
            }
        }
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemSettingsChanged)) {
        db.settingsChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbName)) {
        db.name = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbNameChanged)) {
        db.nameChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDesc)) {
        db.description = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDescChanged)) {
        db.descriptionChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDefaultUser)) {
        db.defaultUserName = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbDefaultUserChanged)) {
        db.defaultUserNameChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbColor)) {
        // TODO: Add support to interpret the color if we want to allow changing the database color
        db.color = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbMntncHistoryDays)) {
        db.maintenanceHistoryDays = ReadUInt(xpp, com.keepassdroid.database.load.ImporterV4.DEFAULT_HISTORY_DAYS);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChanged)) {
        db.keyLastChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChangeRec)) {
        db.keyChangeRecDays = ReadLong(xpp, -1);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChangeForce)) {
        db.keyChangeForceDays = ReadLong(xpp, -1);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDbKeyChangeForceOnce)) {
        db.keyChangeForceOnce = ReadBool(xpp, false);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemMemoryProt)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.MemoryProtection, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIcons)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.CustomIcons, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinEnabled)) {
        db.recycleBinEnabled = ReadBool(xpp, true);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinUuid)) {
        db.recycleBinUUID = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemRecycleBinChanged)) {
        db.recycleBinChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEntryTemplatesGroup)) {
        db.entryTemplatesGroup = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEntryTemplatesGroupChanged)) {
        db.entryTemplatesGroupChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemHistoryMaxItems)) {
        db.historyMaxItems = ReadInt(xpp, -1);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemHistoryMaxSize)) {
        db.historyMaxSize = ReadLong(xpp, -1);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEntryTemplatesGroupChanged)) {
        db.entryTemplatesGroupChanged = ReadTime(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLastSelectedGroup)) {
        db.lastSelectedGroup = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLastTopVisibleGroup)) {
        db.lastTopVisibleGroup = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemBinaries)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.Binaries, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.CustomData, xpp);
    }
    break;
case MemoryProtection :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemProtTitle)) {
        db.memoryProtection.protectTitle = ReadBool(xpp, false);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemProtUserName)) {
        db.memoryProtection.protectUserName = ReadBool(xpp, false);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemProtPassword)) {
        db.memoryProtection.protectPassword = ReadBool(xpp, false);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemProtURL)) {
        db.memoryProtection.protectUrl = ReadBool(xpp, false);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemProtNotes)) {
        db.memoryProtection.protectNotes = ReadBool(xpp, false);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemProtAutoHide)) {
        db.memoryProtection.autoEnableVisualHiding = ReadBool(xpp, false);
    } else {
        ReadUnknown(xpp);
    }
    break;
case CustomIcons :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItem)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.CustomIcon, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case CustomIcon :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItemID)) {
        customIconID = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItemData)) {
        java.lang.String strData;
        strData = ReadString(xpp);
        if ((strData != null) && (strData.length() > 0)) {
            customIconData = android.util.Base64.decode(strData, android.util.Base64.NO_WRAP);
        } else {
            assert false;
        }
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemName)) {
        customIconName = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLastModTime)) {
        customIconLastMod = ReadTime(xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case Binaries :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemBinary)) {
        java.lang.String key;
        key = xpp.getAttributeValue(null, com.keepassdroid.database.PwDatabaseV4XML.AttrId);
        if (key != null) {
            com.keepassdroid.database.security.ProtectedBinary pbData;
            pbData = ReadProtectedBinary(xpp);
            int id;
            id = java.lang.Integer.parseInt(key);
            db.binPool.put(id, pbData);
        } else {
            ReadUnknown(xpp);
        }
    } else {
        ReadUnknown(xpp);
    }
    break;
case CustomData :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.CustomDataItem, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case CustomDataItem :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemKey)) {
        customDataKey = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemValue)) {
        customDataValue = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLastModTime)) {
        customDataLastMod = ReadTime(xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case Root :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemGroup)) {
        assert ctxGroups.size() == 0;
        if (ctxGroups.size() != 0)
            throw new java.io.IOException("Group list should be empty.");

        db.rootGroup = new com.keepassdroid.database.PwGroupV4();
        ctxGroups.push(((com.keepassdroid.database.PwGroupV4) (db.rootGroup)));
        ctxGroup = ctxGroups.peek();
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.Group, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObjects)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.RootDeletedObjects, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case Group :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemUuid)) {
        ctxGroup.uuid = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemName)) {
        ctxGroup.name = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemNotes)) {
        ctxGroup.notes = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemIcon)) {
        ctxGroup.icon = db.iconFactory.getIcon(((int) (ReadUInt(xpp, 0))));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconID)) {
        ctxGroup.customIcon = db.iconFactory.getIcon(ReadUuid(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemTimes)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.GroupTimes, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemIsExpanded)) {
        ctxGroup.isExpanded = ReadBool(xpp, true);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemGroupDefaultAutoTypeSeq)) {
        ctxGroup.defaultAutoTypeSequence = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEnableAutoType)) {
        ctxGroup.enableAutoType = StringToBoolean(ReadString(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEnableSearching)) {
        ctxGroup.enableSearching = StringToBoolean(ReadString(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLastTopVisibleEntry)) {
        ctxGroup.lastTopVisibleEntry = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemPreviousParentGroup)) {
        ctxGroup.prevParentGroup = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemTags)) {
        ctxGroup.tags = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.GroupCustomData, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemGroup)) {
        ctxGroup = new com.keepassdroid.database.PwGroupV4();
        ctxGroups.peek().AddGroup(ctxGroup, true);
        ctxGroups.push(ctxGroup);
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.Group, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEntry)) {
        ctxEntry = new com.keepassdroid.database.PwEntryV4();
        ctxGroup.AddEntry(ctxEntry, true);
        entryInHistory = false;
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.Entry, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case GroupCustomData :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.GroupCustomDataItem, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case GroupCustomDataItem :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemKey)) {
        groupCustomDataKey = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemValue)) {
        groupCustomDataValue = ReadString(xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case Entry :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemUuid)) {
        ctxEntry.setUUID(ReadUuid(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemIcon)) {
        ctxEntry.icon = db.iconFactory.getIcon(((int) (ReadUInt(xpp, 0))));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconID)) {
        ctxEntry.customIcon = db.iconFactory.getIcon(ReadUuid(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemFgColor)) {
        ctxEntry.foregroundColor = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemBgColor)) {
        ctxEntry.backgroupColor = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemOverrideUrl)) {
        ctxEntry.overrideURL = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemQualityCheck)) {
        ctxEntry.qualityCheck = ReadBool(xpp, true);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemTags)) {
        ctxEntry.tags = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemPreviousParentGroup)) {
        ctxEntry.prevParentGroup = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemTimes)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryTimes, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemString)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryString, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemBinary)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryBinary, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoType)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryAutoType, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryCustomData, xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemHistory)) {
        assert !entryInHistory;
        if (!entryInHistory) {
            ctxHistoryBase = ctxEntry;
            return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryHistory, xpp);
        } else {
            ReadUnknown(xpp);
        }
    } else {
        ReadUnknown(xpp);
    }
    break;
case EntryCustomData :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryCustomDataItem, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case EntryCustomDataItem :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemKey)) {
        entryCustomDataKey = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemValue)) {
        entryCustomDataValue = ReadString(xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case GroupTimes :
case EntryTimes :
    com.keepassdroid.database.ITimeLogger tl;
    if (ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.GroupTimes) {
        tl = ctxGroup;
    } else {
        tl = ctxEntry;
    }
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLastModTime)) {
        tl.setLastModificationTime(ReadTime(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCreationTime)) {
        tl.setCreationTime(ReadTime(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLastAccessTime)) {
        tl.setLastAccessTime(ReadTime(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemExpiryTime)) {
        tl.setExpiryTime(ReadTime(xpp));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemExpires)) {
        tl.setExpires(ReadBool(xpp, false));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemUsageCount)) {
        tl.setUsageCount(ReadULong(xpp, 0));
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemLocationChanged)) {
        tl.setLocationChanged(ReadTime(xpp));
    } else {
        ReadUnknown(xpp);
    }
    break;
case EntryString :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemKey)) {
        ctxStringName = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemValue)) {
        ctxStringValue = ReadProtectedString(xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case EntryBinary :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemKey)) {
        ctxBinaryName = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemValue)) {
        ctxBinaryValue = ReadProtectedBinary(xpp);
    }
    break;
case EntryAutoType :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeEnabled)) {
        ctxEntry.autoType.enabled = ReadBool(xpp, true);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeObfuscation)) {
        ctxEntry.autoType.obfuscationOptions = ReadUInt(xpp, 0);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeDefaultSeq)) {
        ctxEntry.autoType.defaultSequence = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeItem)) {
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.EntryAutoTypeItem, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case EntryAutoTypeItem :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemWindow)) {
        ctxATName = ReadString(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemKeystrokeSequence)) {
        ctxATSeq = ReadString(xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case EntryHistory :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEntry)) {
        ctxEntry = new com.keepassdroid.database.PwEntryV4();
        ctxHistoryBase.history.add(ctxEntry);
        entryInHistory = true;
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.Entry, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case RootDeletedObjects :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObject)) {
        ctxDeletedObject = new com.keepassdroid.database.PwDeletedObject();
        db.deletedObjects.add(ctxDeletedObject);
        return SwitchContext(ctx, com.keepassdroid.database.load.ImporterV4.KdbContext.DeletedObject, xpp);
    } else {
        ReadUnknown(xpp);
    }
    break;
case DeletedObject :
    if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemUuid)) {
        ctxDeletedObject.uuid = ReadUuid(xpp);
    } else if (name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletionTime)) {
        ctxDeletedObject.setDeletionTime(ReadTime(xpp));
    } else {
        ReadUnknown(xpp);
    }
    break;
default :
    ReadUnknown(xpp);
    break;
}
return ctx;
}


private com.keepassdroid.database.load.ImporterV4.KdbContext EndXmlElement(com.keepassdroid.database.load.ImporterV4.KdbContext ctx, org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException {
assert xpp.getEventType() == org.xmlpull.v1.XmlPullParser.END_TAG;
java.lang.String name;
name = xpp.getName();
if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.KeePassFile) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDocNode)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Null;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.Meta) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemMeta)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.KeePassFile;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.Root) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemRoot)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.KeePassFile;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.MemoryProtection) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemMemoryProt)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Meta;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.CustomIcons) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIcons)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Meta;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.CustomIcon) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomIconItem)) {
if (!customIconID.equals(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO)) {
    com.keepassdroid.database.PwIconCustom icon;
    icon = new com.keepassdroid.database.PwIconCustom(customIconID, customIconData);
    if (customIconName != null) {
        icon.name = customIconName;
    }
    if (customIconLastMod != null) {
        icon.lastMod = customIconLastMod;
    }
    db.customIcons.add(icon);
    db.iconFactory.put(icon);
} else
    assert false;

customIconID = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;
customIconData = null;
customIconName = null;
customIconLastMod = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.CustomIcons;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.Binaries) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemBinaries)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Meta;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.CustomData) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Meta;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.CustomDataItem) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem)) {
if ((customDataKey != null) && (customDataValue != null)) {
    db.customData.put(customDataKey, customDataValue, customDataLastMod);
} else
    assert false;

customDataKey = null;
customDataValue = null;
customDataLastMod = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.CustomData;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.Group) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemGroup)) {
if ((ctxGroup.uuid == null) || ctxGroup.uuid.equals(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO)) {
    ctxGroup.uuid = java.util.UUID.randomUUID();
}
ctxGroups.pop();
if (ctxGroups.size() == 0) {
    ctxGroup = null;
    return com.keepassdroid.database.load.ImporterV4.KdbContext.Root;
} else {
    ctxGroup = ctxGroups.peek();
    return com.keepassdroid.database.load.ImporterV4.KdbContext.Group;
}
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.GroupTimes) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemTimes)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Group;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.GroupCustomData) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Group;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.GroupCustomDataItem) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem)) {
if ((groupCustomDataKey != null) && (groupCustomDataValue != null)) {
    ctxGroup.customData.put(groupCustomDataKey, groupCustomDataKey, customDataLastMod);
} else {
    assert false;
}
groupCustomDataKey = null;
groupCustomDataValue = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.GroupCustomData;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.Entry) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemEntry)) {
if ((ctxEntry.uuid == null) || ctxEntry.uuid.equals(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO)) {
    ctxEntry.uuid = java.util.UUID.randomUUID();
}
if (entryInHistory) {
    ctxEntry = ctxHistoryBase;
    return com.keepassdroid.database.load.ImporterV4.KdbContext.EntryHistory;
}
return com.keepassdroid.database.load.ImporterV4.KdbContext.Group;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryTimes) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemTimes)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Entry;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryString) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemString)) {
ctxEntry.strings.put(ctxStringName, ctxStringValue);
ctxStringName = null;
ctxStringValue = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.Entry;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryBinary) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemBinary)) {
ctxEntry.binaries.put(ctxBinaryName, ctxBinaryValue);
ctxBinaryName = null;
ctxBinaryValue = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.Entry;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryAutoType) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoType)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Entry;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryAutoTypeItem) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemAutoTypeItem)) {
ctxEntry.autoType.put(ctxATName, ctxATSeq);
ctxATName = null;
ctxATSeq = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.EntryAutoType;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryCustomData) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemCustomData)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Entry;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryCustomDataItem) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemStringDictExItem)) {
if ((entryCustomDataKey != null) && (entryCustomDataValue != null)) {
    ctxEntry.customData.put(entryCustomDataKey, entryCustomDataValue);
} else {
    assert false;
}
entryCustomDataKey = null;
entryCustomDataValue = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.EntryCustomData;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.EntryHistory) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemHistory)) {
entryInHistory = false;
return com.keepassdroid.database.load.ImporterV4.KdbContext.Entry;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.RootDeletedObjects) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObjects)) {
return com.keepassdroid.database.load.ImporterV4.KdbContext.Root;
} else if ((ctx == com.keepassdroid.database.load.ImporterV4.KdbContext.DeletedObject) && name.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ElemDeletedObject)) {
ctxDeletedObject = null;
return com.keepassdroid.database.load.ImporterV4.KdbContext.RootDeletedObjects;
} else {
assert false;
java.lang.String contextName;
contextName = "";
if (ctx != null) {
    contextName = ctx.name();
}
throw new java.lang.RuntimeException((("Invalid end element: Context " + contextName) + "End element: ") + name);
}
}


private java.util.Date ReadTime(org.xmlpull.v1.XmlPullParser xpp) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
java.lang.String sDate;
sDate = ReadString(xpp);
java.util.Date utcDate;
utcDate = null;
if (version >= com.keepassdroid.database.PwDbHeaderV4.FILE_VERSION_32_4) {
byte[] buf;
buf = android.util.Base64.decode(sDate, android.util.Base64.NO_WRAP);
if (buf.length != 8) {
    byte[] buf8;
    buf8 = new byte[8];
    java.lang.System.arraycopy(buf, 0, buf8, 0, java.lang.Math.min(buf.length, 8));
    buf = buf8;
}
long seconds;
seconds = com.keepassdroid.stream.LEDataInputStream.readLong(buf, 0);
utcDate = com.keepassdroid.utils.DateUtil.convertKDBX4Time(seconds);
} else {
try {
    utcDate = com.keepassdroid.database.PwDatabaseV4XML.dateFormatter.get().parse(sDate);
} catch (java.text.ParseException e) {
    // Catch with null test below
}
if (utcDate == null) {
    utcDate = new java.util.Date(0L);
}
}
return utcDate;
}


private void ReadUnknown(org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
assert false;
if (xpp.isEmptyElementTag())
return;

java.lang.String unknownName;
unknownName = xpp.getName();
ProcessNode(xpp);
while (xpp.next() != org.xmlpull.v1.XmlPullParser.END_DOCUMENT) {
if (xpp.getEventType() == org.xmlpull.v1.XmlPullParser.END_TAG)
    break;

if (xpp.getEventType() == org.xmlpull.v1.XmlPullParser.START_TAG)
    continue;

ReadUnknown(xpp);
} 
assert xpp.getName().equals(unknownName);
}


private boolean ReadBool(org.xmlpull.v1.XmlPullParser xpp, boolean bDefault) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
java.lang.String str;
str = ReadString(xpp);
if (str.equalsIgnoreCase("true")) {
return true;
} else if (str.equalsIgnoreCase("false")) {
return false;
} else {
return bDefault;
}
}


private java.util.UUID ReadUuid(org.xmlpull.v1.XmlPullParser xpp) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
java.lang.String encoded;
encoded = ReadString(xpp);
if ((encoded == null) || (encoded.length() == 0)) {
return com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;
}
byte[] buf;
buf = android.util.Base64.decode(encoded, android.util.Base64.NO_WRAP);
return com.keepassdroid.utils.Types.bytestoUUID(buf);
}


private int ReadInt(org.xmlpull.v1.XmlPullParser xpp, int def) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
java.lang.String str;
str = ReadString(xpp);
int u;
try {
u = java.lang.Integer.parseInt(str);
} catch (java.lang.NumberFormatException e) {
u = def;
}
return u;
}


private static final long MAX_UINT = 4294967296L;// 2^32


private long ReadUInt(org.xmlpull.v1.XmlPullParser xpp, long uDefault) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
long u;
u = ReadULong(xpp, uDefault);
if ((u < 0) || (u > com.keepassdroid.database.load.ImporterV4.MAX_UINT)) {
throw new java.lang.NumberFormatException("Outside of the uint size");
}
return u;
}


private long ReadLong(org.xmlpull.v1.XmlPullParser xpp, long def) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
java.lang.String str;
str = ReadString(xpp);
long u;
try {
u = java.lang.Long.parseLong(str);
} catch (java.lang.NumberFormatException e) {
u = def;
}
return u;
}


private long ReadULong(org.xmlpull.v1.XmlPullParser xpp, long uDefault) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
long u;
u = ReadLong(xpp, uDefault);
if (u < 0) {
u = uDefault;
}
return u;
}


private com.keepassdroid.database.security.ProtectedString ReadProtectedString(org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
byte[] buf;
buf = ProcessNode(xpp);
if (buf != null) {
try {
    return new com.keepassdroid.database.security.ProtectedString(true, new java.lang.String(buf, "UTF-8"));
} catch (java.io.UnsupportedEncodingException e) {
    e.printStackTrace();
    throw new java.io.IOException(e.getLocalizedMessage());
}
}
return new com.keepassdroid.database.security.ProtectedString(false, ReadString(xpp));
}


private com.keepassdroid.database.security.ProtectedBinary ReadProtectedBinary(org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
java.lang.String ref;
ref = xpp.getAttributeValue(null, com.keepassdroid.database.PwDatabaseV4XML.AttrRef);
if (ref != null) {
xpp.next()// Consume end tag
;// Consume end tag

int id;
id = java.lang.Integer.parseInt(ref);
return db.binPool.get(id);
}
boolean compressed;
compressed = false;
java.lang.String comp;
comp = xpp.getAttributeValue(null, com.keepassdroid.database.PwDatabaseV4XML.AttrCompressed);
if (comp != null) {
compressed = comp.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ValTrue);
}
byte[] buf;
buf = ProcessNode(xpp);
if (buf != null)
return new com.keepassdroid.database.security.ProtectedBinary(true, buf);

java.lang.String base64;
base64 = ReadString(xpp);
if (base64.length() == 0)
return com.keepassdroid.database.security.ProtectedBinary.EMPTY;

byte[] data;
data = android.util.Base64.decode(base64, android.util.Base64.NO_WRAP);
if (compressed) {
data = com.keepassdroid.utils.MemUtil.decompress(data);
}
return new com.keepassdroid.database.security.ProtectedBinary(false, data);
}


private java.lang.String ReadString(org.xmlpull.v1.XmlPullParser xpp) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
byte[] buf;
buf = ProcessNode(xpp);
if (buf != null) {
try {
    return new java.lang.String(buf, "UTF-8");
} catch (java.io.UnsupportedEncodingException e) {
    throw new java.io.IOException(e.getLocalizedMessage());
}
}
// readNextNode = false;
return xpp.nextText();
}


private java.lang.String ReadStringRaw(org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
// readNextNode = false;
return xpp.nextText();
}


private byte[] ProcessNode(org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
assert xpp.getEventType() == org.xmlpull.v1.XmlPullParser.START_TAG;
byte[] buf;
buf = null;
if (xpp.getAttributeCount() > 0) {
java.lang.String protect;
protect = xpp.getAttributeValue(null, com.keepassdroid.database.PwDatabaseV4XML.AttrProtected);
if ((protect != null) && protect.equalsIgnoreCase(com.keepassdroid.database.PwDatabaseV4XML.ValTrue)) {
    java.lang.String encrypted;
    encrypted = ReadStringRaw(xpp);
    if (encrypted.length() > 0) {
        buf = android.util.Base64.decode(encrypted, android.util.Base64.NO_WRAP);
        byte[] plainText;
        plainText = new byte[buf.length];
        randomStream.processBytes(buf, 0, buf.length, plainText, 0);
        return plainText;
    } else {
        buf = new byte[0];
    }
}
}
return buf;
}


private com.keepassdroid.database.load.ImporterV4.KdbContext SwitchContext(com.keepassdroid.database.load.ImporterV4.KdbContext ctxCurrent, com.keepassdroid.database.load.ImporterV4.KdbContext ctxNew, org.xmlpull.v1.XmlPullParser xpp) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
if (xpp.isEmptyElementTag()) {
xpp.next()// Consume the end tag
;// Consume the end tag

return ctxCurrent;
}
return ctxNew;
}


private java.lang.Boolean StringToBoolean(java.lang.String str) {
if ((str == null) || (str.length() == 0)) {
return null;
}
java.lang.String trimmed;
trimmed = str.trim();
if (trimmed.equalsIgnoreCase("true")) {
return true;
} else if (trimmed.equalsIgnoreCase("false")) {
return false;
}
return null;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
