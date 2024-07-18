package de.danoeh.antennapod.fragment.preferences.about;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import de.danoeh.antennapod.R;
import android.widget.ListView;
import androidx.annotation.NonNull;
import android.widget.Toast;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.adapter.SimpleIconListAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import org.w3c.dom.NamedNodeMap;
import io.reactivex.disposables.Disposable;
import org.w3c.dom.NodeList;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import java.io.InputStream;
import java.io.InputStreamReader;
import androidx.fragment.app.ListFragment;
import java.io.IOException;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import io.reactivex.SingleOnSubscribe;
import de.danoeh.antennapod.core.util.IntentUtils;
import io.reactivex.Single;
import javax.xml.parsers.DocumentBuilder;
import java.io.BufferedReader;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LicensesFragment extends androidx.fragment.app.ListFragment {
    static final int MUID_STATIC = getMUID();
    private io.reactivex.disposables.Disposable licensesLoader;

    private final java.util.ArrayList<de.danoeh.antennapod.fragment.preferences.about.LicensesFragment.LicenseItem> licenses = new java.util.ArrayList<>();

    @java.lang.Override
    public void onViewCreated(@androidx.annotation.NonNull
    android.view.View view, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);
        licensesLoader = io.reactivex.Single.create(((io.reactivex.SingleOnSubscribe<java.util.ArrayList<de.danoeh.antennapod.fragment.preferences.about.LicensesFragment.LicenseItem>>) ((io.reactivex.SingleEmitter<java.util.ArrayList<de.danoeh.antennapod.fragment.preferences.about.LicensesFragment.LicenseItem>> emitter) -> {
            licenses.clear();
            java.io.InputStream stream;
            stream = getContext().getAssets().open("licenses.xml");
            javax.xml.parsers.DocumentBuilder docBuilder;
            docBuilder = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.NodeList libraryList;
            libraryList = docBuilder.parse(stream).getElementsByTagName("library");
            for (int i = 0; i < libraryList.getLength(); i++) {
                org.w3c.dom.NamedNodeMap lib;
                lib = libraryList.item(i).getAttributes();
                licenses.add(new de.danoeh.antennapod.fragment.preferences.about.LicensesFragment.LicenseItem(lib.getNamedItem("name").getTextContent(), java.lang.String.format("By %s, %s license", lib.getNamedItem("author").getTextContent(), lib.getNamedItem("license").getTextContent()), null, lib.getNamedItem("website").getTextContent(), lib.getNamedItem("licenseText").getTextContent()));
            }
            emitter.onSuccess(licenses);
        }))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.ArrayList<de.danoeh.antennapod.fragment.preferences.about.LicensesFragment.LicenseItem> developers) -> setListAdapter(new de.danoeh.antennapod.adapter.SimpleIconListAdapter<de.danoeh.antennapod.fragment.preferences.about.LicensesFragment.LicenseItem>(getContext(), developers)), (java.lang.Throwable error) -> android.widget.Toast.makeText(getContext(), error.getMessage(), android.widget.Toast.LENGTH_LONG).show());
    }


    private static class LicenseItem extends de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem {
        final java.lang.String licenseUrl;

        final java.lang.String licenseTextFile;

        LicenseItem(java.lang.String title, java.lang.String subtitle, java.lang.String imageUrl, java.lang.String licenseUrl, java.lang.String licenseTextFile) {
            super(title, subtitle, imageUrl);
            this.licenseUrl = licenseUrl;
            this.licenseTextFile = licenseTextFile;
        }

    }

    @java.lang.Override
    public void onListItemClick(@androidx.annotation.NonNull
    android.widget.ListView l, @androidx.annotation.NonNull
    android.view.View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        de.danoeh.antennapod.fragment.preferences.about.LicensesFragment.LicenseItem item;
        item = licenses.get(position);
        java.lang.CharSequence[] items;
        items = new java.lang.CharSequence[]{ "View website", "View license" };
        switch(MUID_STATIC) {
            // LicensesFragment_0_BuggyGUIListenerOperatorMutator
            case 96: {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext()).setTitle(item.title).setItems(items, null).show();
                break;
            }
            default: {
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext()).setTitle(item.title).setItems(items, (android.content.DialogInterface dialog,int which) -> {
                if (which == 0) {
                    de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(getContext(), item.licenseUrl);
                } else if (which == 1) {
                    showLicenseText(item.licenseTextFile);
                }
            }).show();
            break;
        }
    }
}


private void showLicenseText(java.lang.String licenseTextFile) {
    try {
        java.io.BufferedReader reader;
        reader = new java.io.BufferedReader(new java.io.InputStreamReader(getContext().getAssets().open(licenseTextFile), "UTF-8"));
        java.lang.StringBuilder licenseText;
        licenseText = new java.lang.StringBuilder();
        java.lang.String line;
        while ((line = reader.readLine()) != null) {
            licenseText.append(line).append("\n");
        } 
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext()).setMessage(licenseText).show();
    } catch (java.io.IOException e) {
        e.printStackTrace();
    }
}


@java.lang.Override
public void onStop() {
    super.onStop();
    if (licensesLoader != null) {
        licensesLoader.dispose();
    }
}


@java.lang.Override
public void onStart() {
    super.onStart();
    ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.licenses);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
