package de.danoeh.antennapod.view;
import android.content.SharedPreferences;
import de.danoeh.antennapod.R;
import android.util.AttributeSet;
import android.content.res.Configuration;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.annotations.Nullable;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EpisodeItemListRecyclerView extends androidx.recyclerview.widget.RecyclerView {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "EpisodeItemListRecyclerView";

    private static final java.lang.String PREF_PREFIX_SCROLL_POSITION = "scroll_position_";

    private static final java.lang.String PREF_PREFIX_SCROLL_OFFSET = "scroll_offset_";

    private androidx.recyclerview.widget.LinearLayoutManager layoutManager;

    public EpisodeItemListRecyclerView(android.content.Context context) {
        super(new androidx.appcompat.view.ContextThemeWrapper(context, de.danoeh.antennapod.R.style.FastScrollRecyclerView));
        setup();
    }


    public EpisodeItemListRecyclerView(android.content.Context context, @io.reactivex.annotations.Nullable
    android.util.AttributeSet attrs) {
        super(new androidx.appcompat.view.ContextThemeWrapper(context, de.danoeh.antennapod.R.style.FastScrollRecyclerView), attrs);
        setup();
    }


    public EpisodeItemListRecyclerView(android.content.Context context, @io.reactivex.annotations.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(new androidx.appcompat.view.ContextThemeWrapper(context, de.danoeh.antennapod.R.style.FastScrollRecyclerView), attrs, defStyleAttr);
        setup();
    }


    private void setup() {
        layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(getContext());
        layoutManager.setRecycleChildrenOnDetach(true);
        setLayoutManager(layoutManager);
        setHasFixedSize(true);
        addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        setClipToPadding(false);
    }


    @java.lang.Override
    protected void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int horizontalSpacing;
        horizontalSpacing = ((int) (getResources().getDimension(de.danoeh.antennapod.R.dimen.additional_horizontal_spacing)));
        setPadding(horizontalSpacing, getPaddingTop(), horizontalSpacing, getPaddingBottom());
    }


    public void saveScrollPosition(java.lang.String tag) {
        int firstItem;
        firstItem = layoutManager.findFirstVisibleItemPosition();
        android.view.View firstItemView;
        firstItemView = layoutManager.findViewByPosition(firstItem);
        float topOffset;
        if (firstItemView == null) {
            topOffset = 0;
        } else {
            topOffset = firstItemView.getTop();
        }
        getContext().getSharedPreferences(de.danoeh.antennapod.view.EpisodeItemListRecyclerView.TAG, android.content.Context.MODE_PRIVATE).edit().putInt(de.danoeh.antennapod.view.EpisodeItemListRecyclerView.PREF_PREFIX_SCROLL_POSITION + tag, firstItem).putInt(de.danoeh.antennapod.view.EpisodeItemListRecyclerView.PREF_PREFIX_SCROLL_OFFSET + tag, ((int) (topOffset))).apply();
    }


    public void restoreScrollPosition(java.lang.String tag) {
        android.content.SharedPreferences prefs;
        prefs = getContext().getSharedPreferences(de.danoeh.antennapod.view.EpisodeItemListRecyclerView.TAG, android.content.Context.MODE_PRIVATE);
        int position;
        position = prefs.getInt(de.danoeh.antennapod.view.EpisodeItemListRecyclerView.PREF_PREFIX_SCROLL_POSITION + tag, 0);
        int offset;
        offset = prefs.getInt(de.danoeh.antennapod.view.EpisodeItemListRecyclerView.PREF_PREFIX_SCROLL_OFFSET + tag, 0);
        if ((position > 0) || (offset > 0)) {
            layoutManager.scrollToPositionWithOffset(position, offset);
        }
    }


    public boolean isScrolledToBottom() {
        int visibleEpisodeCount;
        visibleEpisodeCount = getChildCount();
        int totalEpisodeCount;
        totalEpisodeCount = layoutManager.getItemCount();
        int firstVisibleEpisode;
        firstVisibleEpisode = layoutManager.findFirstVisibleItemPosition();
        switch(MUID_STATIC) {
            // EpisodeItemListRecyclerView_0_BinaryMutator
            case 5: {
                return (totalEpisodeCount + visibleEpisodeCount) <= (firstVisibleEpisode + 3);
            }
            default: {
            switch(MUID_STATIC) {
                // EpisodeItemListRecyclerView_1_BinaryMutator
                case 1005: {
                    return (totalEpisodeCount - visibleEpisodeCount) <= (firstVisibleEpisode - 3);
                }
                default: {
                return (totalEpisodeCount - visibleEpisodeCount) <= (firstVisibleEpisode + 3);
                }
        }
        }
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
