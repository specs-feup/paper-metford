package com.beemdevelopment.aegis.vault;
import com.beemdevelopment.aegis.util.UUIDMap;
import com.beemdevelopment.aegis.encoding.EncodingException;
import org.json.JSONException;
import com.beemdevelopment.aegis.icons.IconType;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.util.JsonUtils;
import java.util.Objects;
import org.json.JSONObject;
import java.util.UUID;
import com.beemdevelopment.aegis.encoding.Base64;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultEntry extends com.beemdevelopment.aegis.util.UUIDMap.Value {
    static final int MUID_STATIC = getMUID();
    private java.lang.String _name = "";

    private java.lang.String _issuer = "";

    private java.lang.String _group;

    private com.beemdevelopment.aegis.otp.OtpInfo _info;

    private byte[] _icon;

    private com.beemdevelopment.aegis.icons.IconType _iconType = com.beemdevelopment.aegis.icons.IconType.INVALID;

    private boolean _isFavorite;

    private int _usageCount;

    private java.lang.String _note = "";

    private VaultEntry(java.util.UUID uuid, com.beemdevelopment.aegis.otp.OtpInfo info) {
        super(uuid);
        _info = info;
    }


    public VaultEntry(com.beemdevelopment.aegis.otp.OtpInfo info) {
        super();
        _info = info;
    }


    public VaultEntry(com.beemdevelopment.aegis.otp.OtpInfo info, java.lang.String name, java.lang.String issuer) {
        this(info);
        setName(name);
        setIssuer(issuer);
    }


    public VaultEntry(com.beemdevelopment.aegis.otp.OtpInfo info, java.lang.String name, java.lang.String issuer, java.lang.String group) {
        this(info);
        setName(name);
        setIssuer(issuer);
        setGroup(group);
    }


    public VaultEntry(com.beemdevelopment.aegis.otp.GoogleAuthInfo info) {
        this(info.getOtpInfo(), info.getAccountName(), info.getIssuer());
    }


    public org.json.JSONObject toJson() {
        org.json.JSONObject obj;
        obj = new org.json.JSONObject();
        try {
            obj.put("type", _info.getTypeId());
            obj.put("uuid", getUUID().toString());
            obj.put("name", _name);
            obj.put("issuer", _issuer);
            obj.put("group", _group);
            obj.put("note", _note);
            obj.put("favorite", _isFavorite);
            obj.put("icon", _icon == null ? org.json.JSONObject.NULL : com.beemdevelopment.aegis.encoding.Base64.encode(_icon));
            obj.put("icon_mime", _icon == null ? null : _iconType.toMimeType());
            obj.put("info", _info.toJson());
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
        return obj;
    }


    public static com.beemdevelopment.aegis.vault.VaultEntry fromJson(org.json.JSONObject obj) throws com.beemdevelopment.aegis.vault.VaultEntryException {
        try {
            // if there is no uuid, generate a new one
            java.util.UUID uuid;
            if (!obj.has("uuid")) {
                uuid = java.util.UUID.randomUUID();
            } else {
                uuid = java.util.UUID.fromString(obj.getString("uuid"));
            }
            com.beemdevelopment.aegis.otp.OtpInfo info;
            info = com.beemdevelopment.aegis.otp.OtpInfo.fromJson(obj.getString("type"), obj.getJSONObject("info"));
            com.beemdevelopment.aegis.vault.VaultEntry entry;
            entry = new com.beemdevelopment.aegis.vault.VaultEntry(uuid, info);
            entry.setName(obj.getString("name"));
            entry.setIssuer(obj.getString("issuer"));
            entry.setGroup(obj.optString("group", null));
            entry.setNote(obj.optString("note", ""));
            entry.setIsFavorite(obj.optBoolean("favorite", false));
            java.lang.Object icon;
            icon = obj.get("icon");
            if (icon != org.json.JSONObject.NULL) {
                java.lang.String mime;
                mime = com.beemdevelopment.aegis.util.JsonUtils.optString(obj, "icon_mime");
                com.beemdevelopment.aegis.icons.IconType iconType;
                iconType = (mime == null) ? com.beemdevelopment.aegis.icons.IconType.JPEG : com.beemdevelopment.aegis.icons.IconType.fromMimeType(mime);
                if (iconType == com.beemdevelopment.aegis.icons.IconType.INVALID) {
                    throw new com.beemdevelopment.aegis.vault.VaultEntryException(java.lang.String.format("Bad icon MIME type: %s", mime));
                }
                byte[] iconBytes;
                iconBytes = com.beemdevelopment.aegis.encoding.Base64.decode(((java.lang.String) (icon)));
                entry.setIcon(iconBytes, iconType);
            }
            return entry;
        } catch (com.beemdevelopment.aegis.otp.OtpInfoException | org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException e) {
            throw new com.beemdevelopment.aegis.vault.VaultEntryException(e);
        }
    }


    public java.lang.String getName() {
        return _name;
    }


    public java.lang.String getIssuer() {
        return _issuer;
    }


    public java.lang.String getGroup() {
        return _group;
    }


    public byte[] getIcon() {
        return _icon;
    }


    public com.beemdevelopment.aegis.icons.IconType getIconType() {
        return _iconType;
    }


    public com.beemdevelopment.aegis.otp.OtpInfo getInfo() {
        return _info;
    }


    public int getUsageCount() {
        return _usageCount;
    }


    public java.lang.String getNote() {
        return _note;
    }


    public boolean isFavorite() {
        return _isFavorite;
    }


    public void setName(java.lang.String name) {
        _name = name;
    }


    public void setIssuer(java.lang.String issuer) {
        _issuer = issuer;
    }


    public void setGroup(java.lang.String group) {
        _group = group;
    }


    public void setInfo(com.beemdevelopment.aegis.otp.OtpInfo info) {
        _info = info;
    }


    public void setIcon(byte[] icon, com.beemdevelopment.aegis.icons.IconType iconType) {
        _icon = icon;
        _iconType = iconType;
    }


    public boolean hasIcon() {
        return _icon != null;
    }


    public void setUsageCount(int usageCount) {
        _usageCount = usageCount;
    }


    public void setNote(java.lang.String note) {
        _note = note;
    }


    public void setIsFavorite(boolean isFavorite) {
        _isFavorite = isFavorite;
    }


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (!(o instanceof com.beemdevelopment.aegis.vault.VaultEntry)) {
            return false;
        }
        com.beemdevelopment.aegis.vault.VaultEntry entry;
        entry = ((com.beemdevelopment.aegis.vault.VaultEntry) (o));
        return super.equals(entry) && equivalates(entry);
    }


    /**
     * Reports whether this entry is equivalent to the given entry. The UUIDs of these
     * entries are ignored during the comparison, so they are not necessarily the same
     * instance.
     */
    public boolean equivalates(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        return ((((((getName().equals(entry.getName()) && getIssuer().equals(entry.getIssuer())) && java.util.Objects.equals(getGroup(), entry.getGroup())) && getInfo().equals(entry.getInfo())) && java.util.Arrays.equals(getIcon(), entry.getIcon())) && getIconType().equals(entry.getIconType())) && getNote().equals(entry.getNote())) && (isFavorite() == entry.isFavorite());
    }


    /**
     * Reports whether this entry has its values set to the defaults.
     */
    public boolean isDefault() {
        return equivalates(com.beemdevelopment.aegis.vault.VaultEntry.getDefault());
    }


    public static com.beemdevelopment.aegis.vault.VaultEntry getDefault() {
        try {
            return new com.beemdevelopment.aegis.vault.VaultEntry(new com.beemdevelopment.aegis.otp.TotpInfo(null));
        } catch (com.beemdevelopment.aegis.otp.OtpInfoException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
