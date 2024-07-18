package de.danoeh.antennapod.fragment.preferences.about;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import java.io.InputStreamReader;
import java.util.ArrayList;
import androidx.fragment.app.ListFragment;
import android.view.View;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.Single;
import androidx.annotation.NonNull;
import java.io.BufferedReader;
import android.widget.Toast;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.adapter.SimpleIconListAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DevelopersFragment extends androidx.fragment.app.ListFragment {
    static final int MUID_STATIC = getMUID();
    private io.reactivex.disposables.Disposable developersLoader;

    @java.lang.Override
    public void onViewCreated(@androidx.annotation.NonNull
    android.view.View view, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);
        getListView().setSelector(android.R.color.transparent);
        developersLoader = io.reactivex.Single.create(((io.reactivex.SingleOnSubscribe<java.util.ArrayList<de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem>>) ((io.reactivex.SingleEmitter<java.util.ArrayList<de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem>> emitter) -> {
            java.util.ArrayList<de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem> developers;
            developers = new java.util.ArrayList<>();
            java.io.BufferedReader reader;
            reader = new java.io.BufferedReader(new java.io.InputStreamReader(getContext().getAssets().open("developers.csv"), "UTF-8"));
            java.lang.String line;
            while ((line = reader.readLine()) != null) {
                java.lang.String[] info;
                info = line.split(";");
                developers.add(new de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem(info[0], info[2], ("https://avatars2.githubusercontent.com/u/" + info[1]) + "?s=60&v=4"));
            } 
            emitter.onSuccess(developers);
        }))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.ArrayList<de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem> developers) -> setListAdapter(new de.danoeh.antennapod.adapter.SimpleIconListAdapter<>(getContext(), developers)), (java.lang.Throwable error) -> android.widget.Toast.makeText(getContext(), error.getMessage(), android.widget.Toast.LENGTH_LONG).show());
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        if (developersLoader != null) {
            developersLoader.dispose();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
