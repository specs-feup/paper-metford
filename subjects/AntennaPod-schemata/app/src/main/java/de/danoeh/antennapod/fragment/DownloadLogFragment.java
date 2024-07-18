package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.view.EmptyViewHandler;
import java.util.ArrayList;
import de.danoeh.antennapod.core.storage.DBWriter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import de.danoeh.antennapod.model.download.DownloadResult;
import de.danoeh.antennapod.adapter.DownloadLogAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.databinding.DownloadLogFragmentBinding;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.dialog.DownloadLogDetailsDialog;
import de.danoeh.antennapod.core.event.DownloadLogEvent;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.util.Log;
import android.view.Menu;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import android.widget.AdapterView;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows the download log
 */
public class DownloadLogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment implements android.widget.AdapterView.OnItemClickListener , androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "DownloadLogFragment";

    private java.util.List<de.danoeh.antennapod.model.download.DownloadResult> downloadLog = new java.util.ArrayList<>();

    private de.danoeh.antennapod.adapter.DownloadLogAdapter adapter;

    private io.reactivex.disposables.Disposable disposable;

    private de.danoeh.antennapod.databinding.DownloadLogFragmentBinding viewBinding;

    @java.lang.Override
    public void onStart() {
        super.onStart();
        loadDownloadLog();
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
        }
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        viewBinding = de.danoeh.antennapod.databinding.DownloadLogFragmentBinding.inflate(inflater);
        viewBinding.toolbar.inflateMenu(de.danoeh.antennapod.R.menu.download_log);
        viewBinding.toolbar.setOnMenuItemClickListener(this);
        de.danoeh.antennapod.view.EmptyViewHandler emptyView;
        emptyView = new de.danoeh.antennapod.view.EmptyViewHandler(getActivity());
        emptyView.setIcon(de.danoeh.antennapod.R.drawable.ic_download);
        emptyView.setTitle(de.danoeh.antennapod.R.string.no_log_downloads_head_label);
        emptyView.setMessage(de.danoeh.antennapod.R.string.no_log_downloads_label);
        emptyView.attachToListView(viewBinding.list);
        adapter = new de.danoeh.antennapod.adapter.DownloadLogAdapter(getActivity());
        viewBinding.list.setAdapter(adapter);
        viewBinding.list.setOnItemClickListener(this);
        viewBinding.list.setNestedScrollingEnabled(true);
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        return viewBinding.getRoot();
    }


    @java.lang.Override
    public void onDestroyView() {
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }


    @java.lang.Override
    public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
        java.lang.Object item;
        item = adapter.getItem(position);
        if (item instanceof de.danoeh.antennapod.model.download.DownloadResult) {
            new de.danoeh.antennapod.dialog.DownloadLogDetailsDialog(getContext(), ((de.danoeh.antennapod.model.download.DownloadResult) (item))).show();
        }
    }


    @org.greenrobot.eventbus.Subscribe
    public void onDownloadLogChanged(de.danoeh.antennapod.core.event.DownloadLogEvent event) {
        loadDownloadLog();
    }


    @java.lang.Override
    public void onPrepareOptionsMenu(@androidx.annotation.NonNull
    android.view.Menu menu) {
        menu.findItem(de.danoeh.antennapod.R.id.clear_logs_item).setVisible(!downloadLog.isEmpty());
    }


    @java.lang.Override
    public boolean onMenuItemClick(android.view.MenuItem item) {
        if (super.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == de.danoeh.antennapod.R.id.clear_logs_item) {
            de.danoeh.antennapod.core.storage.DBWriter.clearDownloadLog();
            return true;
        }
        return false;
    }


    private void loadDownloadLog() {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = io.reactivex.Observable.fromCallable(de.danoeh.antennapod.core.storage.DBReader::getDownloadLog).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.download.DownloadResult> result) -> {
            if (result != null) {
                downloadLog = result;
                adapter.setDownloadLog(downloadLog);
            }
        }, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.DownloadLogFragment.TAG, android.util.Log.getStackTraceString(error)));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
