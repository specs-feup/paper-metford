package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import de.danoeh.antennapod.databinding.SubscriptionSelectionActivityBinding;
import java.util.ArrayList;
import android.graphics.Bitmap;
import static de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID;
import com.bumptech.glide.request.target.Target;
import de.danoeh.antennapod.R;
import android.widget.ListView;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import com.bumptech.glide.Glide;
import de.danoeh.antennapod.model.feed.Feed;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.util.Log;
import com.bumptech.glide.request.RequestListener;
import de.danoeh.antennapod.core.storage.NavDrawerData;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import android.content.Intent;
import com.bumptech.glide.load.DataSource;
import de.danoeh.antennapod.core.storage.DBReader;
import androidx.core.content.pm.ShortcutManagerCompat;
import com.bumptech.glide.load.engine.GlideException;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SelectSubscriptionActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "SelectSubscription";

    private io.reactivex.disposables.Disposable disposable;

    private volatile java.util.List<de.danoeh.antennapod.model.feed.Feed> listItems;

    private de.danoeh.antennapod.databinding.SubscriptionSelectionActivityBinding viewBinding;

    @java.lang.Override
    protected void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        setTheme(de.danoeh.antennapod.core.preferences.ThemeSwitcher.getTranslucentTheme(this));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SelectSubscriptionActivity_0_LengthyGUICreationOperatorMutator
            case 142: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    viewBinding = de.danoeh.antennapod.databinding.SubscriptionSelectionActivityBinding.inflate(getLayoutInflater());
    setContentView(viewBinding.getRoot());
    setSupportActionBar(viewBinding.toolbar);
    setTitle(de.danoeh.antennapod.R.string.shortcut_select_subscription);
    switch(MUID_STATIC) {
        // SelectSubscriptionActivity_1_BuggyGUIListenerOperatorMutator
        case 1142: {
            viewBinding.transparentBackground.setOnClickListener(null);
            break;
        }
        default: {
        viewBinding.transparentBackground.setOnClickListener((android.view.View v) -> finish());
        break;
    }
}
viewBinding.card.setOnClickListener(null);
loadSubscriptions();
final java.lang.Integer[] checkedPosition;
checkedPosition = new java.lang.Integer[1];
viewBinding.list.setChoiceMode(android.widget.ListView.CHOICE_MODE_SINGLE);
viewBinding.list.setOnItemClickListener((android.widget.AdapterView<?> listView,android.view.View view1,int position,long rowId) -> checkedPosition[0] = position);
switch(MUID_STATIC) {
    // SelectSubscriptionActivity_2_BuggyGUIListenerOperatorMutator
    case 2142: {
        viewBinding.shortcutBtn.setOnClickListener(null);
        break;
    }
    default: {
    viewBinding.shortcutBtn.setOnClickListener((android.view.View view) -> {
        if ((checkedPosition[0] != null) && android.content.Intent.ACTION_CREATE_SHORTCUT.equals(getIntent().getAction())) {
            getBitmapFromUrl(listItems.get(checkedPosition[0]));
        }
    });
    break;
}
}
}


public java.util.List<de.danoeh.antennapod.model.feed.Feed> getFeedItems(java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> items, java.util.List<de.danoeh.antennapod.model.feed.Feed> result) {
for (de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item : items) {
if (item.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.TAG) {
    getFeedItems(((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (item)).children, result);
} else {
    de.danoeh.antennapod.model.feed.Feed feed;
    feed = ((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (item)).feed;
    if (!result.contains(feed)) {
        result.add(feed);
    }
}
}
return result;
}


private void addShortcut(de.danoeh.antennapod.model.feed.Feed feed, android.graphics.Bitmap bitmap) {
android.content.Intent intent;
switch(MUID_STATIC) {
// SelectSubscriptionActivity_3_NullIntentOperatorMutator
case 3142: {
    intent = null;
    break;
}
// SelectSubscriptionActivity_4_InvalidKeyIntentOperatorMutator
case 4142: {
    intent = new android.content.Intent((SelectSubscriptionActivity) null, de.danoeh.antennapod.activity.MainActivity.class);
    break;
}
// SelectSubscriptionActivity_5_RandomActionIntentDefinitionOperatorMutator
case 5142: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent(this, de.danoeh.antennapod.activity.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// SelectSubscriptionActivity_6_RandomActionIntentDefinitionOperatorMutator
case 6142: {
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
intent.setAction(android.content.Intent.ACTION_MAIN);
break;
}
}
switch(MUID_STATIC) {
// SelectSubscriptionActivity_7_RandomActionIntentDefinitionOperatorMutator
case 7142: {
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
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
break;
}
}
switch(MUID_STATIC) {
// SelectSubscriptionActivity_8_NullValueIntentPutExtraOperatorMutator
case 8142: {
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID, new Parcelable[0]);
break;
}
// SelectSubscriptionActivity_9_IntentPayloadReplacementOperatorMutator
case 9142: {
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID, 0);
break;
}
default: {
switch(MUID_STATIC) {
// SelectSubscriptionActivity_10_RandomActionIntentDefinitionOperatorMutator
case 10142: {
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
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID, feed.getId());
break;
}
}
break;
}
}
java.lang.String id;
id = "subscription-" + feed.getId();
androidx.core.graphics.drawable.IconCompat icon;
if (bitmap != null) {
icon = androidx.core.graphics.drawable.IconCompat.createWithAdaptiveBitmap(bitmap);
} else {
icon = androidx.core.graphics.drawable.IconCompat.createWithResource(this, de.danoeh.antennapod.R.drawable.ic_subscriptions_shortcut);
}
androidx.core.content.pm.ShortcutInfoCompat shortcut;
shortcut = new androidx.core.content.pm.ShortcutInfoCompat.Builder(this, id).setShortLabel(feed.getTitle()).setLongLabel(feed.getFeedTitle()).setIntent(intent).setIcon(icon).build();
setResult(android.app.Activity.RESULT_OK, androidx.core.content.pm.ShortcutManagerCompat.createShortcutResultIntent(this, shortcut));
finish();
}


private void getBitmapFromUrl(de.danoeh.antennapod.model.feed.Feed feed) {
int iconSize;
switch(MUID_STATIC) {
// SelectSubscriptionActivity_11_BinaryMutator
case 11142: {
iconSize = ((int) (128 / getResources().getDisplayMetrics().density));
break;
}
default: {
iconSize = ((int) (128 * getResources().getDisplayMetrics().density));
break;
}
}
com.bumptech.glide.Glide.with(this).asBitmap().load(feed.getImageUrl()).apply(com.bumptech.glide.request.RequestOptions.overrideOf(iconSize, iconSize)).listener(new com.bumptech.glide.request.RequestListener<android.graphics.Bitmap>() {
@java.lang.Override
public boolean onLoadFailed(@androidx.annotation.Nullable
com.bumptech.glide.load.engine.GlideException e, java.lang.Object model, com.bumptech.glide.request.target.Target<android.graphics.Bitmap> target, boolean isFirstResource) {
addShortcut(feed, null);
return true;
}


@java.lang.Override
public boolean onResourceReady(android.graphics.Bitmap resource, java.lang.Object model, com.bumptech.glide.request.target.Target<android.graphics.Bitmap> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
addShortcut(feed, resource);
return true;
}

}).submit();
}


private void loadSubscriptions() {
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Observable.fromCallable(() -> {
de.danoeh.antennapod.core.storage.NavDrawerData data;
data = de.danoeh.antennapod.core.storage.DBReader.getNavDrawerData(de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter());
return getFeedItems(data.items, new java.util.ArrayList<>());
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.feed.Feed> result) -> {
listItems = result;
java.util.ArrayList<java.lang.String> titles;
titles = new java.util.ArrayList<>();
for (de.danoeh.antennapod.model.feed.Feed feed : result) {
titles.add(feed.getTitle());
}
android.widget.ArrayAdapter<java.lang.String> adapter;
adapter = new android.widget.ArrayAdapter<>(this, de.danoeh.antennapod.R.layout.simple_list_item_multiple_choice_on_start, titles);
viewBinding.list.setAdapter(adapter);
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.activity.SelectSubscriptionActivity.TAG, android.util.Log.getStackTraceString(error)));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
