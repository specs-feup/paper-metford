package de.danoeh.antennapod.view;
import de.danoeh.antennapod.core.util.ShareUtils;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.ContextMenu;
import android.net.Uri;
import de.danoeh.antennapod.core.util.NetworkUtils;
import androidx.core.content.ContextCompat;
import de.danoeh.antennapod.R;
import androidx.core.util.Consumer;
import android.os.Build;
import de.danoeh.antennapod.core.util.Converter;
import android.webkit.WebViewClient;
import android.webkit.WebView;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import de.danoeh.antennapod.core.util.gui.ShownotesCleaner;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.content.ClipboardManager;
import android.util.AttributeSet;
import de.danoeh.antennapod.core.util.IntentUtils;
import android.webkit.WebSettings;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import android.content.ClipData;
import com.google.android.material.snackbar.Snackbar;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ShownotesWebView extends android.webkit.WebView implements android.view.View.OnLongClickListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "ShownotesWebView";

    /**
     * URL that was selected via long-press.
     */
    private java.lang.String selectedUrl;

    private androidx.core.util.Consumer<java.lang.Integer> timecodeSelectedListener;

    private java.lang.Runnable pageFinishedListener;

    public ShownotesWebView(android.content.Context context) {
        super(context);
        setup();
    }


    public ShownotesWebView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        setup();
    }


    public ShownotesWebView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }


    private void setup() {
        setBackgroundColor(android.graphics.Color.TRANSPARENT);
        if (!de.danoeh.antennapod.core.util.NetworkUtils.networkAvailable()) {
            getSettings().setCacheMode(android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK);
            // Use cached resources, even if they have expired
        }
        getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        getSettings().setUseWideViewPort(false);
        getSettings().setLoadWithOverviewMode(true);
        setOnLongClickListener(this);
        setWebViewClient(new android.webkit.WebViewClient() {
            @java.lang.Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, java.lang.String url) {
                if (de.danoeh.antennapod.core.util.gui.ShownotesCleaner.isTimecodeLink(url) && (timecodeSelectedListener != null)) {
                    timecodeSelectedListener.accept(de.danoeh.antennapod.core.util.gui.ShownotesCleaner.getTimecodeLinkTime(url));
                } else {
                    de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(getContext(), url);
                }
                return true;
            }


            @java.lang.Override
            public void onPageFinished(android.webkit.WebView view, java.lang.String url) {
                super.onPageFinished(view, url);
                android.util.Log.d(de.danoeh.antennapod.view.ShownotesWebView.TAG, "Page finished");
                if (pageFinishedListener != null) {
                    pageFinishedListener.run();
                }
            }

        });
    }


    @java.lang.Override
    public boolean onLongClick(android.view.View v) {
        android.webkit.WebView.HitTestResult r;
        r = getHitTestResult();
        if ((r != null) && (r.getType() == android.webkit.WebView.HitTestResult.SRC_ANCHOR_TYPE)) {
            android.util.Log.d(de.danoeh.antennapod.view.ShownotesWebView.TAG, "Link of webview was long-pressed. Extra: " + r.getExtra());
            selectedUrl = r.getExtra();
            showContextMenu();
            return true;
        } else if ((r != null) && (r.getType() == android.webkit.WebView.HitTestResult.EMAIL_TYPE)) {
            android.util.Log.d(de.danoeh.antennapod.view.ShownotesWebView.TAG, "E-Mail of webview was long-pressed. Extra: " + r.getExtra());
            android.content.ClipboardManager clipboardManager;
            clipboardManager = androidx.core.content.ContextCompat.getSystemService(this.getContext(), android.content.ClipboardManager.class);
            if (clipboardManager != null) {
                clipboardManager.setPrimaryClip(android.content.ClipData.newPlainText("AntennaPod", r.getExtra()));
            }
            if ((android.os.Build.VERSION.SDK_INT <= 32) && (this.getContext() instanceof de.danoeh.antennapod.activity.MainActivity)) {
                ((de.danoeh.antennapod.activity.MainActivity) (this.getContext())).showSnackbarAbovePlayer(getResources().getString(de.danoeh.antennapod.R.string.copied_to_clipboard), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
            }
            return true;
        }
        selectedUrl = null;
        return false;
    }


    public boolean onContextItemSelected(android.view.MenuItem item) {
        if (selectedUrl == null) {
            return false;
        }
        final int itemId;
        itemId = item.getItemId();
        if (itemId == de.danoeh.antennapod.R.id.open_in_browser_item) {
            de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(getContext(), selectedUrl);
        } else if (itemId == de.danoeh.antennapod.R.id.share_url_item) {
            de.danoeh.antennapod.core.util.ShareUtils.shareLink(getContext(), selectedUrl);
        } else if (itemId == de.danoeh.antennapod.R.id.copy_url_item) {
            android.content.ClipData clipData;
            clipData = android.content.ClipData.newPlainText(selectedUrl, selectedUrl);
            android.content.ClipboardManager cm;
            cm = ((android.content.ClipboardManager) (getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
            cm.setPrimaryClip(clipData);
            if (android.os.Build.VERSION.SDK_INT < 32) {
                com.google.android.material.snackbar.Snackbar s;
                s = com.google.android.material.snackbar.Snackbar.make(this, de.danoeh.antennapod.R.string.copied_to_clipboard, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
                s.getView().setElevation(100);
                s.show();
            }
        } else if (itemId == de.danoeh.antennapod.R.id.go_to_position_item) {
            if (de.danoeh.antennapod.core.util.gui.ShownotesCleaner.isTimecodeLink(selectedUrl) && (timecodeSelectedListener != null)) {
                timecodeSelectedListener.accept(de.danoeh.antennapod.core.util.gui.ShownotesCleaner.getTimecodeLinkTime(selectedUrl));
            } else {
                android.util.Log.e(de.danoeh.antennapod.view.ShownotesWebView.TAG, "Selected go_to_position_item, but URL was no timecode link: " + selectedUrl);
            }
        } else {
            selectedUrl = null;
            return false;
        }
        selectedUrl = null;
        return true;
    }


    @java.lang.Override
    protected void onCreateContextMenu(android.view.ContextMenu menu) {
        super.onCreateContextMenu(menu);
        if (selectedUrl == null) {
            return;
        }
        if (de.danoeh.antennapod.core.util.gui.ShownotesCleaner.isTimecodeLink(selectedUrl)) {
            menu.add(android.view.Menu.NONE, de.danoeh.antennapod.R.id.go_to_position_item, android.view.Menu.NONE, de.danoeh.antennapod.R.string.go_to_position_label);
            menu.setHeaderTitle(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(de.danoeh.antennapod.core.util.gui.ShownotesCleaner.getTimecodeLinkTime(selectedUrl)));
        } else {
            android.net.Uri uri;
            uri = android.net.Uri.parse(selectedUrl);
            final android.content.Intent intent;
            switch(MUID_STATIC) {
                // ShownotesWebView_0_NullIntentOperatorMutator
                case 16: {
                    intent = null;
                    break;
                }
                // ShownotesWebView_1_InvalidKeyIntentOperatorMutator
                case 1016: {
                    intent = new android.content.Intent((String) null, uri);
                    break;
                }
                // ShownotesWebView_2_RandomActionIntentDefinitionOperatorMutator
                case 2016: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(android.content.Intent.ACTION_VIEW, uri);
                break;
            }
        }
        if (de.danoeh.antennapod.core.util.IntentUtils.isCallable(getContext(), intent)) {
            menu.add(android.view.Menu.NONE, de.danoeh.antennapod.R.id.open_in_browser_item, android.view.Menu.NONE, de.danoeh.antennapod.R.string.open_in_browser_label);
        }
        menu.add(android.view.Menu.NONE, de.danoeh.antennapod.R.id.copy_url_item, android.view.Menu.NONE, de.danoeh.antennapod.R.string.copy_url_label);
        menu.add(android.view.Menu.NONE, de.danoeh.antennapod.R.id.share_url_item, android.view.Menu.NONE, de.danoeh.antennapod.R.string.share_url_label);
        menu.setHeaderTitle(selectedUrl);
    }
    de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, this::onContextItemSelected);
}


public void setTimecodeSelectedListener(androidx.core.util.Consumer<java.lang.Integer> timecodeSelectedListener) {
    this.timecodeSelectedListener = timecodeSelectedListener;
}


public void setPageFinishedListener(java.lang.Runnable pageFinishedListener) {
    this.pageFinishedListener = pageFinishedListener;
}


@java.lang.Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(java.lang.Math.max(getMeasuredWidth(), getMinimumWidth()), java.lang.Math.max(getMeasuredHeight(), getMinimumHeight()));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
