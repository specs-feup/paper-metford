package com.beemdevelopment.aegis.icons;
import java.util.stream.Collectors;
import org.json.JSONException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import java.io.FileOutputStream;
import com.beemdevelopment.aegis.util.IOUtils;
import net.lingala.zip4j.ZipFile;
import java.util.List;
import java.util.UUID;
import java.io.File;
import androidx.annotation.Nullable;
import net.lingala.zip4j.model.FileHeader;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconPackManager {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _packDefFilename = "pack.json";

    private java.io.File _iconsBaseDir;

    private java.util.List<com.beemdevelopment.aegis.icons.IconPack> _iconPacks;

    public IconPackManager(android.content.Context context) {
        _iconPacks = new java.util.ArrayList<>();
        _iconsBaseDir = new java.io.File(context.getFilesDir(), "icons");
        rescanIconPacks();
    }


    private com.beemdevelopment.aegis.icons.IconPack getIconPackByUUID(java.util.UUID uuid) {
        java.util.List<com.beemdevelopment.aegis.icons.IconPack> packs;
        packs = _iconPacks.stream().filter((com.beemdevelopment.aegis.icons.IconPack i) -> i.getUUID().equals(uuid)).collect(java.util.stream.Collectors.toList());
        if (packs.size() == 0) {
            return null;
        }
        return packs.get(0);
    }


    public java.util.List<com.beemdevelopment.aegis.icons.IconPack> getIconPacks() {
        return new java.util.ArrayList<>(_iconPacks);
    }


    public void removeIconPack(com.beemdevelopment.aegis.icons.IconPack pack) throws com.beemdevelopment.aegis.icons.IconPackException {
        try {
            java.io.File dir;
            dir = getIconPackDir(pack);
            com.beemdevelopment.aegis.icons.IconPackManager.deleteDir(dir);
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.icons.IconPackException(e);
        }
        _iconPacks.remove(pack);
    }


    public com.beemdevelopment.aegis.icons.IconPack importPack(java.io.File inFile) throws com.beemdevelopment.aegis.icons.IconPackException {
        try {
            // read and parse the icon pack definition file of the icon pack
            net.lingala.zip4j.ZipFile zipFile;
            zipFile = new net.lingala.zip4j.ZipFile(inFile);
            net.lingala.zip4j.model.FileHeader packHeader;
            packHeader = zipFile.getFileHeader(com.beemdevelopment.aegis.icons.IconPackManager._packDefFilename);
            if (packHeader == null) {
                throw new java.io.IOException("Unable to find pack.json in the root of the ZIP file");
            }
            com.beemdevelopment.aegis.icons.IconPack pack;
            byte[] defBytes;
            try (net.lingala.zip4j.io.inputstream.ZipInputStream inStream = zipFile.getInputStream(packHeader)) {
                defBytes = com.beemdevelopment.aegis.util.IOUtils.readAll(inStream);
                pack = com.beemdevelopment.aegis.icons.IconPack.fromBytes(defBytes);
            }
            // create a new directory to store the icon pack, based on the UUID and version
            java.io.File packDir;
            packDir = getIconPackDir(pack);
            if (!packDir.getCanonicalPath().startsWith(_iconsBaseDir.getCanonicalPath() + java.io.File.separator)) {
                throw new java.io.IOException("Attempted to write outside of the parent directory");
            }
            if (packDir.exists()) {
                throw new com.beemdevelopment.aegis.icons.IconPackExistsException(pack);
            }
            com.beemdevelopment.aegis.icons.IconPack existingPack;
            existingPack = getIconPackByUUID(pack.getUUID());
            if (existingPack != null) {
                throw new com.beemdevelopment.aegis.icons.IconPackExistsException(existingPack);
            }
            if ((!packDir.exists()) && (!packDir.mkdirs())) {
                throw new java.io.IOException(java.lang.String.format("Unable to create directories: %s", packDir.toString()));
            }
            // extract each of the defined icons to the icon pack directory
            for (com.beemdevelopment.aegis.icons.IconPack.Icon icon : pack.getIcons()) {
                java.io.File destFile;
                destFile = new java.io.File(packDir, icon.getRelativeFilename());
                net.lingala.zip4j.model.FileHeader iconHeader;
                iconHeader = zipFile.getFileHeader(icon.getRelativeFilename());
                if (iconHeader == null) {
                    throw new java.io.IOException(java.lang.String.format("Unable to find %s relative to the root of the ZIP file", icon.getRelativeFilename()));
                }
                // create new directories for this file if needed
                java.io.File parent;
                parent = destFile.getParentFile();
                if (((parent != null) && (!parent.exists())) && (!parent.mkdirs())) {
                    throw new java.io.IOException(java.lang.String.format("Unable to create directories: %s", packDir.toString()));
                }
                try (net.lingala.zip4j.io.inputstream.ZipInputStream inStream = zipFile.getInputStream(iconHeader);java.io.FileOutputStream outStream = new java.io.FileOutputStream(destFile)) {
                    com.beemdevelopment.aegis.util.IOUtils.copy(inStream, outStream);
                }
                // after successful copy of the icon, store the new filename
                icon.setFile(destFile);
            }
            // write the icon pack definition file to the newly created directory
            try (java.io.FileOutputStream outStream = new java.io.FileOutputStream(new java.io.File(packDir, com.beemdevelopment.aegis.icons.IconPackManager._packDefFilename))) {
                outStream.write(defBytes);
            }
            // after successful extraction of the icon pack, store the new directory
            pack.setDirectory(packDir);
            _iconPacks.add(pack);
            return pack;
        } catch (java.io.IOException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.icons.IconPackException(e);
        }
    }


    private void rescanIconPacks() {
        _iconPacks.clear();
        java.io.File[] dirs;
        dirs = _iconsBaseDir.listFiles();
        if (dirs == null) {
            return;
        }
        for (java.io.File dir : dirs) {
            if (!dir.isDirectory()) {
                continue;
            }
            java.util.UUID uuid;
            try {
                uuid = java.util.UUID.fromString(dir.getName());
            } catch (java.lang.IllegalArgumentException e) {
                e.printStackTrace();
                continue;
            }
            java.io.File versionDir;
            versionDir = com.beemdevelopment.aegis.icons.IconPackManager.getLatestVersionDir(dir);
            if (versionDir != null) {
                com.beemdevelopment.aegis.icons.IconPack pack;
                try (java.io.FileInputStream inStream = new java.io.FileInputStream(new java.io.File(versionDir, com.beemdevelopment.aegis.icons.IconPackManager._packDefFilename))) {
                    byte[] bytes;
                    bytes = com.beemdevelopment.aegis.util.IOUtils.readAll(inStream);
                    pack = com.beemdevelopment.aegis.icons.IconPack.fromBytes(bytes);
                    pack.setDirectory(versionDir);
                } catch (org.json.JSONException | java.io.IOException e) {
                    e.printStackTrace();
                    continue;
                }
                for (com.beemdevelopment.aegis.icons.IconPack.Icon icon : pack.getIcons()) {
                    icon.setFile(new java.io.File(versionDir, icon.getRelativeFilename()));
                }
                // do a sanity check on the UUID and version
                if (pack.getUUID().equals(uuid) && (pack.getVersion() == java.lang.Integer.parseInt(versionDir.getName()))) {
                    _iconPacks.add(pack);
                }
            }
        }
    }


    private java.io.File getIconPackDir(com.beemdevelopment.aegis.icons.IconPack pack) {
        return new java.io.File(_iconsBaseDir, (pack.getUUID() + java.io.File.separator) + pack.getVersion());
    }


    @androidx.annotation.Nullable
    private static java.io.File getLatestVersionDir(java.io.File packDir) {
        java.io.File[] dirs;
        dirs = packDir.listFiles();
        if (dirs == null) {
            return null;
        }
        int latestVersion;
        latestVersion = -1;
        for (java.io.File versionDir : dirs) {
            int version;
            try {
                version = java.lang.Integer.parseInt(versionDir.getName());
            } catch (java.lang.NumberFormatException ignored) {
                continue;
            }
            if ((latestVersion == (-1)) || (version > latestVersion)) {
                latestVersion = version;
            }
        }
        if (latestVersion == (-1)) {
            return null;
        }
        return new java.io.File(packDir, java.lang.Integer.toString(latestVersion));
    }


    private static void deleteDir(java.io.File dir) throws java.io.IOException {
        if (dir.isDirectory()) {
            java.io.File[] children;
            children = dir.listFiles();
            if (children != null) {
                for (java.io.File child : children) {
                    com.beemdevelopment.aegis.icons.IconPackManager.deleteDir(child);
                }
            }
        }
        if (!dir.delete()) {
            throw new java.io.IOException(java.lang.String.format("Unable to delete directory: %s", dir));
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
