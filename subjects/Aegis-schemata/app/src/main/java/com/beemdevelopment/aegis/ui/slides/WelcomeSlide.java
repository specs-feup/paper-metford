package com.beemdevelopment.aegis.ui.slides;
import com.beemdevelopment.aegis.ui.tasks.ImportFileTask;
import com.beemdevelopment.aegis.importers.DatabaseImporterException;
import com.beemdevelopment.aegis.ui.intro.SlideFragment;
import android.os.Bundle;
import android.view.ViewGroup;
import java.io.IOException;
import com.beemdevelopment.aegis.importers.AegisImporter;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import java.io.FileInputStream;
import com.beemdevelopment.aegis.vault.VaultRepository;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import com.beemdevelopment.aegis.importers.DatabaseImporter;
import androidx.annotation.NonNull;
import java.io.File;
import com.beemdevelopment.aegis.vault.VaultFileCredentials;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class WelcomeSlide extends com.beemdevelopment.aegis.ui.intro.SlideFragment {
    static final int MUID_STATIC = getMUID();
    public static final int CODE_IMPORT_VAULT = 0;

    private boolean _imported;

    private com.beemdevelopment.aegis.vault.VaultFileCredentials _creds;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view;
        view = inflater.inflate(com.beemdevelopment.aegis.R.layout.fragment_welcome_slide, container, false);
        switch(MUID_STATIC) {
            // WelcomeSlide_0_BuggyGUIListenerOperatorMutator
            case 118: {
                view.findViewById(com.beemdevelopment.aegis.R.id.btnImport).setOnClickListener(null);
                break;
            }
            default: {
            view.findViewById(com.beemdevelopment.aegis.R.id.btnImport).setOnClickListener((android.view.View v) -> {
                android.content.Intent intent;
                switch(MUID_STATIC) {
                    // WelcomeSlide_1_InvalidKeyIntentOperatorMutator
                    case 1118: {
                        intent = new android.content.Intent((String) null);
                        break;
                    }
                    // WelcomeSlide_2_RandomActionIntentDefinitionOperatorMutator
                    case 2118: {
                        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                        break;
                    }
                    default: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
                    break;
                }
            }
            switch(MUID_STATIC) {
                // WelcomeSlide_3_RandomActionIntentDefinitionOperatorMutator
                case 3118: {
                    /**
                    * Inserted by Kadabra
                    */
                    /**
                    * Inserted by Kadabra
                    */
                    new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent.setType("*/*");
                break;
            }
        }
        startActivityForResult(intent, com.beemdevelopment.aegis.ui.slides.WelcomeSlide.CODE_IMPORT_VAULT);
    });
    break;
}
}
return view;
}


@java.lang.Override
public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
if (((requestCode == com.beemdevelopment.aegis.ui.slides.WelcomeSlide.CODE_IMPORT_VAULT) && (data != null)) && (data.getData() != null)) {
startImportVault(data.getData());
}
}


@java.lang.Override
public void onSaveIntroState(@androidx.annotation.NonNull
android.os.Bundle introState) {
introState.putBoolean("imported", _imported);
introState.putSerializable("creds", _creds);
}


private void startImportVault(android.net.Uri uri) {
com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params params;
params = new com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params(uri, "intro-import", null);
com.beemdevelopment.aegis.ui.tasks.ImportFileTask task;
task = new com.beemdevelopment.aegis.ui.tasks.ImportFileTask(requireContext(), (com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result result) -> {
if (result.getError() != null) {
    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.reading_file_error, result.getError());
    return;
}
try (java.io.FileInputStream inStream = new java.io.FileInputStream(result.getFile())) {
    com.beemdevelopment.aegis.importers.AegisImporter importer;
    importer = new com.beemdevelopment.aegis.importers.AegisImporter(requireContext());
    com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
    state = importer.read(inStream, false);
    if (state.isEncrypted()) {
        state.decrypt(requireContext(), new com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener() {
            @java.lang.Override
            protected void onStateDecrypted(com.beemdevelopment.aegis.importers.DatabaseImporter.State state) {
                _creds = ((com.beemdevelopment.aegis.importers.AegisImporter.DecryptedState) (state)).getCredentials();
                importVault(result.getFile());
            }


            @java.lang.Override
            protected void onError(java.lang.Exception e) {
                e.printStackTrace();
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.decryption_error, e);
            }


            @java.lang.Override
            protected void onCanceled() {
            }

        });
    } else {
        importVault(result.getFile());
    }
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException | java.io.IOException e) {
    e.printStackTrace();
    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.intro_import_error_title, e);
}
});
task.execute(getLifecycle(), params);
}


private void importVault(java.io.File file) {
try (java.io.FileInputStream inStream = new java.io.FileInputStream(file)) {
com.beemdevelopment.aegis.vault.VaultRepository.writeToFile(requireContext(), inStream);
} catch (java.io.IOException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.intro_import_error_title, e);
return;
}
_imported = true;
goToNextSlide();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
