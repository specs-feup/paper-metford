package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import de.danoeh.antennapod.adapter.DataFolderAdapter;
import de.danoeh.antennapod.R;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ChooseDataFolderDialog {
    static final int MUID_STATIC = getMUID();
    public static void showDialog(final android.content.Context context, androidx.core.util.Consumer<java.lang.String> handlerFunc) {
        android.view.View content;
        content = android.view.View.inflate(context, de.danoeh.antennapod.R.layout.choose_data_folder_dialog, null);
        androidx.appcompat.app.AlertDialog dialog;
        dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setView(content).setTitle(de.danoeh.antennapod.R.string.choose_data_directory).setMessage(de.danoeh.antennapod.R.string.choose_data_directory_message).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).create();
        ((androidx.recyclerview.widget.RecyclerView) (content.findViewById(de.danoeh.antennapod.R.id.recyclerView))).setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(context));
        de.danoeh.antennapod.adapter.DataFolderAdapter adapter;
        adapter = new de.danoeh.antennapod.adapter.DataFolderAdapter(context, (java.lang.String path) -> {
            dialog.dismiss();
            handlerFunc.accept(path);
        });
        ((androidx.recyclerview.widget.RecyclerView) (content.findViewById(de.danoeh.antennapod.R.id.recyclerView))).setAdapter(adapter);
        if (adapter.getItemCount() != 0) {
            dialog.show();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
