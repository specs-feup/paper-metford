package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.helpers.ContextHelper;
import com.beemdevelopment.aegis.vault.VaultFileException;
import android.content.DialogInterface;
import androidx.lifecycle.Lifecycle;
import org.json.JSONException;
import com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask;
import java.io.InputStream;
import java.io.IOException;
import com.topjohnwu.superuser.io.SuFile;
import org.json.JSONArray;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.vault.VaultEntryException;
import com.beemdevelopment.aegis.vault.slots.SlotList;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.util.IOUtils;
import com.beemdevelopment.aegis.vault.VaultEntry;
import com.beemdevelopment.aegis.vault.VaultFile;
import org.json.JSONObject;
import java.util.List;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.vault.VaultFileCredentials;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AegisImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    public AegisImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() {
        throw new java.lang.UnsupportedOperationException();
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            byte[] bytes;
            bytes = com.beemdevelopment.aegis.util.IOUtils.readAll(stream);
            com.beemdevelopment.aegis.vault.VaultFile file;
            file = com.beemdevelopment.aegis.vault.VaultFile.fromBytes(bytes);
            if (file.isEncrypted()) {
                return new com.beemdevelopment.aegis.importers.AegisImporter.EncryptedState(file);
            }
            return new com.beemdevelopment.aegis.importers.AegisImporter.DecryptedState(file.getContent());
        } catch (com.beemdevelopment.aegis.vault.VaultFileException | java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    public static class EncryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private com.beemdevelopment.aegis.vault.VaultFile _file;

        private EncryptedState(com.beemdevelopment.aegis.vault.VaultFile file) {
            super(true);
            _file = file;
        }


        public com.beemdevelopment.aegis.vault.slots.SlotList getSlots() {
            return _file.getHeader().getSlots();
        }


        public com.beemdevelopment.aegis.importers.DatabaseImporter.State decrypt(com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            org.json.JSONObject obj;
            try {
                obj = _file.getContent(creds);
            } catch (com.beemdevelopment.aegis.vault.VaultFileException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
            return new com.beemdevelopment.aegis.importers.AegisImporter.DecryptedState(obj, creds);
        }


        public com.beemdevelopment.aegis.importers.DatabaseImporter.State decrypt(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> slots;
            slots = getSlots().findAll(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class);
            com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result result;
            result = com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.decrypt(slots, password);
            com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
            creds = new com.beemdevelopment.aegis.vault.VaultFileCredentials(result.getKey(), getSlots());
            return decrypt(creds);
        }


        @java.lang.Override
        public void decrypt(android.content.Context context, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(context, com.beemdevelopment.aegis.R.string.enter_password_aegis_title, 0, ((com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener) (( password) -> {
                java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> slots;
                slots = getSlots().findAll(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class);
                com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params params;
                params = new com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params(slots, password);
                com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask task;
                task = new com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask(context, (com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result result) -> {
                    try {
                        if (result == null) {
                            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException("Password incorrect");
                        }
                        com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
                        creds = new com.beemdevelopment.aegis.vault.VaultFileCredentials(result.getKey(), getSlots());
                        com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
                        state = decrypt(creds);
                        listener.onStateDecrypted(state);
                    } catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
                        listener.onError(e);
                    }
                });
                androidx.lifecycle.Lifecycle lifecycle;
                lifecycle = com.beemdevelopment.aegis.helpers.ContextHelper.getLifecycle(context);
                task.execute(lifecycle, params);
            })), ((android.content.DialogInterface.OnCancelListener) ((android.content.DialogInterface dialog) -> listener.onCanceled())));
        }

    }

    public static class DecryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private org.json.JSONObject _obj;

        private com.beemdevelopment.aegis.vault.VaultFileCredentials _creds;

        private DecryptedState(org.json.JSONObject obj) {
            this(obj, null);
        }


        private DecryptedState(org.json.JSONObject obj, com.beemdevelopment.aegis.vault.VaultFileCredentials creds) {
            super(false);
            _obj = obj;
            _creds = creds;
        }


        @androidx.annotation.Nullable
        public com.beemdevelopment.aegis.vault.VaultFileCredentials getCredentials() {
            return _creds;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            try {
                org.json.JSONArray array;
                array = _obj.getJSONArray("entries");
                for (int i = 0; i < array.length(); i++) {
                    org.json.JSONObject entryObj;
                    entryObj = array.getJSONObject(i);
                    try {
                        com.beemdevelopment.aegis.vault.VaultEntry entry;
                        entry = com.beemdevelopment.aegis.importers.AegisImporter.DecryptedState.convertEntry(entryObj);
                        result.addEntry(entry);
                    } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                        result.addError(e);
                    }
                }
            } catch (org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(org.json.JSONObject obj) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                return com.beemdevelopment.aegis.vault.VaultEntry.fromJson(obj);
            } catch (com.beemdevelopment.aegis.vault.VaultEntryException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, obj.toString());
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
