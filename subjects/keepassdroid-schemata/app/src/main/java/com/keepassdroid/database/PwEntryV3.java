/* Copyright 2010-2014 Brian Pellin.

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


This file was derived from 

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
package com.keepassdroid.database;
import com.keepassdroid.utils.Types;
import java.util.Random;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.UUID;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Structure containing information about one entry.
 *
 * <PRE>
 * One entry: [FIELDTYPE(FT)][FIELDSIZE(FS)][FIELDDATA(FD)]
 *            [FT+FS+(FD)][FT+FS+(FD)][FT+FS+(FD)][FT+FS+(FD)][FT+FS+(FD)]...
 *
 * [ 2 bytes] FIELDTYPE
 * [ 4 bytes] FIELDSIZE, size of FIELDDATA in bytes
 * [ n bytes] FIELDDATA, n = FIELDSIZE
 *
 * Notes:
 *  - Strings are stored in UTF-8 encoded form and are null-terminated.
 *  - FIELDTYPE can be one of the FT_ constants.
 * </PRE>
 *
 * @author Naomaru Itoi <nao@phoneid.org>
 * @author Bill Zwicky <wrzwicky@pobox.com>
 * @author Dominik Reichl <dominik.reichl@t-online.de>
 */
public class PwEntryV3 extends com.keepassdroid.database.PwEntry {
    static final int MUID_STATIC = getMUID();
    public static final java.util.Date NEVER_EXPIRE = com.keepassdroid.database.PwEntryV3.getNeverExpire();

    public static final java.util.Date NEVER_EXPIRE_BUG = com.keepassdroid.database.PwEntryV3.getNeverExpireBug();

    public static final java.util.Date DEFAULT_DATE = com.keepassdroid.database.PwEntryV3.getDefaultDate();

    public static final com.keepassdroid.database.PwDate PW_NEVER_EXPIRE = new com.keepassdroid.database.PwDate(com.keepassdroid.database.PwEntryV3.NEVER_EXPIRE);

    public static final com.keepassdroid.database.PwDate PW_NEVER_EXPIRE_BUG = new com.keepassdroid.database.PwDate(com.keepassdroid.database.PwEntryV3.NEVER_EXPIRE_BUG);

    public static final com.keepassdroid.database.PwDate DEFAULT_PWDATE = new com.keepassdroid.database.PwDate(com.keepassdroid.database.PwEntryV3.DEFAULT_DATE);

    /**
     * Size of byte buffer needed to hold this struct.
     */
    public static final java.lang.String PMS_ID_BINDESC = "bin-stream";

    public static final java.lang.String PMS_ID_TITLE = "Meta-Info";

    public static final java.lang.String PMS_ID_USER = "SYSTEM";

    public static final java.lang.String PMS_ID_URL = "$";

    public int groupId;

    public java.lang.String username;

    private byte[] password;

    private byte[] uuid;

    public java.lang.String title;

    public java.lang.String url;

    public java.lang.String additional;

    public com.keepassdroid.database.PwDate tCreation;

    public com.keepassdroid.database.PwDate tLastMod;

    public com.keepassdroid.database.PwDate tLastAccess;

    public com.keepassdroid.database.PwDate tExpire;

    /**
     * A string describing what is in pBinaryData
     */
    public java.lang.String binaryDesc;

    private byte[] binaryData;

    private static java.util.Date getDefaultDate() {
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, 2004);
        cal.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        cal.set(java.util.Calendar.HOUR, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        return cal.getTime();
    }


    private static java.util.Date getNeverExpire() {
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, 2999);
        cal.set(java.util.Calendar.MONTH, 11);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 28);
        cal.set(java.util.Calendar.HOUR, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        return cal.getTime();
    }


    /**
     * This date was was accidentally being written
     *  out when an entry was supposed to be marked as
     *  expired. We'll use this to silently correct those
     *  entries.
     *
     * @return  */
    private static java.util.Date getNeverExpireBug() {
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, 2999);
        cal.set(java.util.Calendar.MONTH, 11);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 30);
        cal.set(java.util.Calendar.HOUR, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        return cal.getTime();
    }


    public static boolean IsNever(java.util.Date date) {
        return com.keepassdroid.database.PwDate.IsSameDate(com.keepassdroid.database.PwEntryV3.NEVER_EXPIRE, date);
    }


    // for tree traversing
    public com.keepassdroid.database.PwGroupV3 parent = null;

    public PwEntryV3() {
        super();
    }


    /* public PwEntryV3(PwEntryV3 source) {
    assign(source);
    }
     */
    public PwEntryV3(com.keepassdroid.database.PwGroupV3 p) {
        this(p, true, true);
    }


    public PwEntryV3(com.keepassdroid.database.PwGroupV3 p, boolean initId, boolean initDates) {
        parent = p;
        groupId = ((com.keepassdroid.database.PwGroupIdV3) (parent.getId())).getId();
        if (initId) {
            java.util.Random random;
            random = new java.util.Random();
            uuid = new byte[16];
            random.nextBytes(uuid);
        }
        if (initDates) {
            java.util.Calendar cal;
            cal = java.util.Calendar.getInstance();
            java.util.Date now;
            now = cal.getTime();
            tCreation = new com.keepassdroid.database.PwDate(now);
            tLastAccess = new com.keepassdroid.database.PwDate(now);
            tLastMod = new com.keepassdroid.database.PwDate(now);
            tExpire = new com.keepassdroid.database.PwDate(com.keepassdroid.database.PwEntryV3.NEVER_EXPIRE);
        }
    }


    /**
     *
     * @return the actual password byte array.
     */
    @java.lang.Override
    public java.lang.String getPassword(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        if (password == null) {
            return "";
        }
        return new java.lang.String(password);
    }


    public byte[] getPasswordBytes() {
        return password;
    }


    /**
     * fill byte array
     */
    private static void fill(byte[] array, byte value) {
        for (int i = 0; i < array.length; i++)
            array[i] = value;

        return;
    }


    /**
     * Securely erase old password before copying new.
     */
    public void setPassword(byte[] buf, int offset, int len) {
        if (password != null) {
            com.keepassdroid.database.PwEntryV3.fill(password, ((byte) (0)));
            password = null;
        }
        password = new byte[len];
        java.lang.System.arraycopy(buf, offset, password, 0, len);
    }


    @java.lang.Override
    public void setPassword(java.lang.String pass, com.keepassdroid.database.PwDatabase db) {
        byte[] password;
        try {
            password = pass.getBytes("UTF-8");
            setPassword(password, 0, password.length);
        } catch (java.io.UnsupportedEncodingException e) {
            assert false;
            password = pass.getBytes();
            setPassword(password, 0, password.length);
        }
    }


    /**
     *
     * @return the actual binaryData byte array.
     */
    public byte[] getBinaryData() {
        return binaryData;
    }


    /**
     * Securely erase old data before copying new.
     */
    public void setBinaryData(byte[] buf, int offset, int len) {
        if (binaryData != null) {
            com.keepassdroid.database.PwEntryV3.fill(binaryData, ((byte) (0)));
            binaryData = null;
        }
        binaryData = new byte[len];
        java.lang.System.arraycopy(buf, offset, binaryData, 0, len);
    }


    // Determine if this is a MetaStream entry
    @java.lang.Override
    public boolean isMetaStream() {
        if (binaryData == null)
            return false;

        if ((additional == null) || (additional.length() == 0))
            return false;

        if (!binaryDesc.equals(com.keepassdroid.database.PwEntryV3.PMS_ID_BINDESC))
            return false;

        if (title == null)
            return false;

        if (!title.equals(com.keepassdroid.database.PwEntryV3.PMS_ID_TITLE))
            return false;

        if (username == null)
            return false;

        if (!username.equals(com.keepassdroid.database.PwEntryV3.PMS_ID_USER))
            return false;

        if (url == null)
            return false;

        if (!url.equals(com.keepassdroid.database.PwEntryV3.PMS_ID_URL))
            return false;

        if (!icon.isMetaStreamIcon())
            return false;

        return true;
    }


    @java.lang.Override
    public void assign(com.keepassdroid.database.PwEntry source) {
        if (!(source instanceof com.keepassdroid.database.PwEntryV3)) {
            throw new java.lang.RuntimeException("DB version mix");
        }
        super.assign(source);
        com.keepassdroid.database.PwEntryV3 src;
        src = ((com.keepassdroid.database.PwEntryV3) (source));
        assign(src);
    }


    private void assign(com.keepassdroid.database.PwEntryV3 source) {
        title = source.title;
        url = source.url;
        groupId = source.groupId;
        username = source.username;
        additional = source.additional;
        uuid = source.uuid;
        int passLen;
        passLen = source.password.length;
        password = new byte[passLen];
        java.lang.System.arraycopy(source.password, 0, password, 0, passLen);
        tCreation = ((com.keepassdroid.database.PwDate) (source.tCreation.clone()));
        tLastMod = ((com.keepassdroid.database.PwDate) (source.tLastMod.clone()));
        tLastAccess = ((com.keepassdroid.database.PwDate) (source.tLastAccess.clone()));
        tExpire = ((com.keepassdroid.database.PwDate) (source.tExpire.clone()));
        binaryDesc = source.binaryDesc;
        if (source.binaryData != null) {
            int descLen;
            descLen = source.binaryData.length;
            binaryData = new byte[descLen];
            java.lang.System.arraycopy(source.binaryData, 0, binaryData, 0, descLen);
        }
        parent = source.parent;
    }


    @java.lang.Override
    public java.lang.Object clone() {
        com.keepassdroid.database.PwEntryV3 newEntry;
        newEntry = ((com.keepassdroid.database.PwEntryV3) (super.clone()));
        if (password != null) {
            int passLen;
            passLen = password.length;
            password = new byte[passLen];
            java.lang.System.arraycopy(password, 0, newEntry.password, 0, passLen);
        }
        newEntry.tCreation = ((com.keepassdroid.database.PwDate) (tCreation.clone()));
        newEntry.tLastMod = ((com.keepassdroid.database.PwDate) (tLastMod.clone()));
        newEntry.tLastAccess = ((com.keepassdroid.database.PwDate) (tLastAccess.clone()));
        newEntry.tExpire = ((com.keepassdroid.database.PwDate) (tExpire.clone()));
        newEntry.binaryDesc = binaryDesc;
        if (binaryData != null) {
            int descLen;
            descLen = binaryData.length;
            newEntry.binaryData = new byte[descLen];
            java.lang.System.arraycopy(binaryData, 0, newEntry.binaryData, 0, descLen);
        }
        newEntry.parent = parent;
        return newEntry;
    }


    @java.lang.Override
    public java.util.Date getLastAccessTime() {
        return tLastAccess.getJDate();
    }


    @java.lang.Override
    public java.util.Date getCreationTime() {
        return tCreation.getJDate();
    }


    @java.lang.Override
    public java.util.Date getExpiryTime() {
        return tExpire.getJDate();
    }


    @java.lang.Override
    public java.util.Date getLastModificationTime() {
        return tLastMod.getJDate();
    }


    @java.lang.Override
    public void setCreationTime(java.util.Date create) {
        tCreation = new com.keepassdroid.database.PwDate(create);
    }


    @java.lang.Override
    public void setLastModificationTime(java.util.Date mod) {
        tLastMod = new com.keepassdroid.database.PwDate(mod);
    }


    @java.lang.Override
    public void setLastAccessTime(java.util.Date access) {
        tLastAccess = new com.keepassdroid.database.PwDate(access);
    }


    @java.lang.Override
    public void setExpires(boolean expires) {
        if (!expires) {
            tExpire = com.keepassdroid.database.PwEntryV3.PW_NEVER_EXPIRE;
        }
    }


    @java.lang.Override
    public void setExpiryTime(java.util.Date expires) {
        tExpire = new com.keepassdroid.database.PwDate(expires);
    }


    @java.lang.Override
    public com.keepassdroid.database.PwGroupV3 getParent() {
        return parent;
    }


    @java.lang.Override
    public java.util.UUID getUUID() {
        return com.keepassdroid.utils.Types.bytestoUUID(uuid);
    }


    @java.lang.Override
    public void setUUID(java.util.UUID u) {
        uuid = com.keepassdroid.utils.Types.UUIDtoBytes(u);
    }


    @java.lang.Override
    public java.lang.String getUsername(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        if (username == null) {
            return "";
        }
        return username;
    }


    @java.lang.Override
    public void setUsername(java.lang.String user, com.keepassdroid.database.PwDatabase db) {
        username = user;
    }


    @java.lang.Override
    public java.lang.String getTitle(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return title;
    }


    @java.lang.Override
    public void setTitle(java.lang.String title, com.keepassdroid.database.PwDatabase db) {
        this.title = title;
    }


    @java.lang.Override
    public java.lang.String getNotes(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return additional;
    }


    @java.lang.Override
    public void setNotes(java.lang.String notes, com.keepassdroid.database.PwDatabase db) {
        additional = notes;
    }


    @java.lang.Override
    public java.lang.String getUrl(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return url;
    }


    @java.lang.Override
    public void setUrl(java.lang.String url, com.keepassdroid.database.PwDatabase db) {
        this.url = url;
    }


    @java.lang.Override
    public boolean expires() {
        return !com.keepassdroid.database.PwEntryV3.IsNever(tExpire.getJDate());
    }


    public void populateBlankFields(com.keepassdroid.database.PwDatabaseV3 db) {
        if (icon == null) {
            icon = db.iconFactory.getIcon(1);
        }
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = new byte[0];
        }
        if (uuid == null) {
            uuid = com.keepassdroid.utils.Types.UUIDtoBytes(java.util.UUID.randomUUID());
        }
        if (title == null) {
            title = "";
        }
        if (url == null) {
            url = "";
        }
        if (additional == null) {
            additional = "";
        }
        if (tCreation == null) {
            tCreation = com.keepassdroid.database.PwEntryV3.DEFAULT_PWDATE;
        }
        if (tLastMod == null) {
            tLastMod = com.keepassdroid.database.PwEntryV3.DEFAULT_PWDATE;
        }
        if (tLastAccess == null) {
            tLastAccess = com.keepassdroid.database.PwEntryV3.DEFAULT_PWDATE;
        }
        if (tExpire == null) {
            tExpire = com.keepassdroid.database.PwEntryV3.PW_NEVER_EXPIRE;
        }
        if (binaryDesc == null) {
            binaryDesc = "";
        }
        if (binaryData == null) {
            binaryData = new byte[0];
        }
    }


    @java.lang.Override
    public void setParent(com.keepassdroid.database.PwGroup parent) {
        this.parent = ((com.keepassdroid.database.PwGroupV3) (parent));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
