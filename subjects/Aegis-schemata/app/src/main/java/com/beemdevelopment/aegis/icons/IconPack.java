package com.beemdevelopment.aegis.icons;
import java.util.stream.Collectors;
import org.json.JSONException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import com.google.common.io.Files;
import androidx.annotation.NonNull;
import com.google.common.base.Objects;
import org.json.JSONObject;
import java.util.List;
import java.util.UUID;
import java.io.File;
import androidx.annotation.Nullable;
import java.io.Serializable;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconPack {
    static final int MUID_STATIC = getMUID();
    private java.util.UUID _uuid;

    private java.lang.String _name;

    private int _version;

    private java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> _icons;

    private java.io.File _dir;

    private IconPack(java.util.UUID uuid, java.lang.String name, int version, java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> icons) {
        _uuid = uuid;
        _name = name;
        _version = version;
        _icons = icons;
    }


    public java.util.UUID getUUID() {
        return _uuid;
    }


    public java.lang.String getName() {
        return _name;
    }


    public int getVersion() {
        return _version;
    }


    public java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> getIcons() {
        return java.util.Collections.unmodifiableList(_icons);
    }


    /**
     * Retrieves a list of icons suggested for the given issuer.
     */
    public java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> getSuggestedIcons(java.lang.String issuer) {
        if ((issuer == null) || issuer.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return _icons.stream().filter((com.beemdevelopment.aegis.icons.IconPack.Icon i) -> i.isSuggestedFor(issuer)).collect(java.util.stream.Collectors.toList());
    }


    @androidx.annotation.Nullable
    public java.io.File getDirectory() {
        return _dir;
    }


    void setDirectory(@androidx.annotation.NonNull
    java.io.File dir) {
        _dir = dir;
    }


    /**
     * Indicates whether some other object is "equal to" this one. The object does not
     * necessarily have to be the same instance. Equality of UUID and version will make
     * this method return true;
     */
    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (!(o instanceof com.beemdevelopment.aegis.icons.IconPack)) {
            return false;
        }
        com.beemdevelopment.aegis.icons.IconPack pack;
        pack = ((com.beemdevelopment.aegis.icons.IconPack) (o));
        return super.equals(pack) || (getUUID().equals(pack.getUUID()) && (getVersion() == pack.getVersion()));
    }


    @java.lang.Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(_uuid, _version);
    }


    public static com.beemdevelopment.aegis.icons.IconPack fromJson(org.json.JSONObject obj) throws org.json.JSONException {
        java.util.UUID uuid;
        java.lang.String uuidString;
        uuidString = obj.getString("uuid");
        try {
            uuid = java.util.UUID.fromString(uuidString);
        } catch (java.lang.IllegalArgumentException e) {
            throw new org.json.JSONException(java.lang.String.format("Bad UUID format: %s", uuidString));
        }
        java.lang.String name;
        name = obj.getString("name");
        int version;
        version = obj.getInt("version");
        org.json.JSONArray array;
        array = obj.getJSONArray("icons");
        java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> icons;
        icons = new java.util.ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            com.beemdevelopment.aegis.icons.IconPack.Icon icon;
            icon = com.beemdevelopment.aegis.icons.IconPack.Icon.fromJson(array.getJSONObject(i));
            icons.add(icon);
        }
        return new com.beemdevelopment.aegis.icons.IconPack(uuid, name, version, icons);
    }


    public static com.beemdevelopment.aegis.icons.IconPack fromBytes(byte[] data) throws org.json.JSONException {
        org.json.JSONObject obj;
        obj = new org.json.JSONObject(new java.lang.String(data, java.nio.charset.StandardCharsets.UTF_8));
        return com.beemdevelopment.aegis.icons.IconPack.fromJson(obj);
    }


    public static class Icon implements java.io.Serializable {
        private final java.lang.String _relFilename;

        private final java.lang.String _category;

        private final java.util.List<java.lang.String> _issuers;

        private java.io.File _file;

        protected Icon(java.lang.String filename, java.lang.String category, java.util.List<java.lang.String> issuers) {
            _relFilename = filename;
            _category = category;
            _issuers = issuers;
        }


        public java.lang.String getRelativeFilename() {
            return _relFilename;
        }


        @androidx.annotation.Nullable
        public java.io.File getFile() {
            return _file;
        }


        void setFile(@androidx.annotation.NonNull
        java.io.File file) {
            _file = file;
        }


        public com.beemdevelopment.aegis.icons.IconType getIconType() {
            return com.beemdevelopment.aegis.icons.IconType.fromFilename(_relFilename);
        }


        public java.lang.String getName() {
            return com.google.common.io.Files.getNameWithoutExtension(new java.io.File(_relFilename).getName());
        }


        public java.lang.String getCategory() {
            return _category;
        }


        public java.util.List<java.lang.String> getIssuers() {
            return java.util.Collections.unmodifiableList(_issuers);
        }


        public boolean isSuggestedFor(java.lang.String issuer) {
            java.lang.String lowerIssuer;
            lowerIssuer = issuer.toLowerCase();
            return getIssuers().stream().map(java.lang.String::toLowerCase).anyMatch((java.lang.String is) -> is.contains(lowerIssuer) || lowerIssuer.contains(is));
        }


        public static com.beemdevelopment.aegis.icons.IconPack.Icon fromJson(org.json.JSONObject obj) throws org.json.JSONException {
            java.lang.String filename;
            filename = obj.getString("filename");
            java.lang.String category;
            category = (obj.isNull("category")) ? null : obj.getString("category");
            org.json.JSONArray array;
            array = obj.getJSONArray("issuer");
            java.util.List<java.lang.String> issuers;
            issuers = new java.util.ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                java.lang.String issuer;
                issuer = array.getString(i);
                issuers.add(issuer);
            }
            return new com.beemdevelopment.aegis.icons.IconPack.Icon(filename, category, issuers);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
