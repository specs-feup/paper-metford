/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.feio.android.omninotes;
import static it.feio.android.omninotes.helpers.AppVersionHelper.updateAppVersionInPreferences;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_SHORTCUT_WIDGET;
import java.util.HashMap;
import static it.feio.android.omninotes.OmniNotes.isDebugBuild;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import androidx.fragment.app.Fragment;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_GOOGLE_NOW;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.models.Attachment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import static it.feio.android.omninotes.helpers.ChangelogHelper.showChangelog;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD;
import it.feio.android.omninotes.async.notes.NoteProcessorDelete;
import java.util.Collections;
import android.content.pm.ActivityInfo;
import it.feio.android.omninotes.utils.FileProviderHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_START_APP;
import android.os.Handler;
import android.os.AsyncTask;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_RESTART_APP;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE;
import androidx.fragment.app.FragmentManager;
import it.feio.android.omninotes.models.Note;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_KEY;
import it.feio.android.omninotes.helpers.LogDelegate;
import androidx.annotation.Nullable;
import it.feio.android.omninotes.async.bus.PasswordRemovedEvent;
import lombok.Getter;
import it.feio.android.omninotes.models.Category;
import lombok.Setter;
import androidx.appcompat.app.ActionBarDrawerToggle;
import de.greenrobot.event.EventBus;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_SHORTCUT;
import android.net.Uri;
import androidx.core.view.GravityCompat;
import static it.feio.android.omninotes.helpers.AppVersionHelper.isAppUpdated;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.widget.Toast;
import androidx.drawerlayout.widget.DrawerLayout;
import it.feio.android.omninotes.helpers.NotesHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_NOTIFICATION_CLICK;
import android.content.SharedPreferences;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_SEND_AND_EXIT;
import android.os.Bundle;
import it.feio.android.pixlui.links.UrlCompleter;
import android.content.Intent;
import android.view.MenuItem;
import it.feio.android.omninotes.utils.PasswordHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET;
import android.view.View;
import it.feio.android.omninotes.async.UpdateWidgetsTask;
import it.feio.android.omninotes.async.bus.SwitchFragmentEvent;
import it.feio.android.omninotes.utils.SystemHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET_TAKE_PHOTO;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.models.ONStyle;
import it.feio.android.omninotes.databinding.ActivityMainBinding;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MainActivity extends it.feio.android.omninotes.BaseActivity implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
    static final int MUID_STATIC = getMUID();
    private boolean isPasswordAccepted = false;

    public static final java.lang.String FRAGMENT_DRAWER_TAG = "fragment_drawer";

    public static final java.lang.String FRAGMENT_LIST_TAG = "fragment_list";

    public static final java.lang.String FRAGMENT_DETAIL_TAG = "fragment_detail";

    public static final java.lang.String FRAGMENT_SKETCH_TAG = "fragment_sketch";

    @lombok.Getter
    @lombok.Setter
    private android.net.Uri sketchUri;

    boolean prefsChanged = false;

    private androidx.fragment.app.FragmentManager mFragmentManager;

    it.feio.android.omninotes.databinding.ActivityMainBinding binding;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // MainActivity_0_LengthyGUICreationOperatorMutator
            case 139: {
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
    setTheme(it.feio.android.omninotes.R.style.OmniNotesTheme_ApiSpec);
    binding = it.feio.android.omninotes.databinding.ActivityMainBinding.inflate(getLayoutInflater());
    android.view.View view;
    view = binding.getRoot();
    setContentView(view);
    de.greenrobot.event.EventBus.getDefault().register(this);
    com.pixplicity.easyprefs.library.Prefs.getPreferences().registerOnSharedPreferenceChangeListener(this);
    initUI();
}


@java.lang.Override
protected void onPostCreate(@androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    if (((!launchIntroIfRequired()) && it.feio.android.omninotes.helpers.AppVersionHelper.isAppUpdated(getApplicationContext())) && (!it.feio.android.omninotes.OmniNotes.isDebugBuild())) {
        showChangelogAndUpdateCurrentVersion();
    }
}


private void showChangelogAndUpdateCurrentVersion() {
    it.feio.android.omninotes.helpers.ChangelogHelper.showChangelog(this);
    it.feio.android.omninotes.helpers.AppVersionHelper.updateAppVersionInPreferences(getApplicationContext());
}


private boolean launchIntroIfRequired() {
    if (it.feio.android.omninotes.intro.IntroActivity.mustRun()) {
        startActivity(new android.content.Intent(getApplicationContext(), it.feio.android.omninotes.intro.IntroActivity.class));
        return true;
    }
    return false;
}


@java.lang.Override
protected void onResume() {
    super.onResume();
    if (isPasswordAccepted) {
        init();
    } else {
        checkPassword();
    }
}


@java.lang.Override
protected void onStop() {
    super.onStop();
    de.greenrobot.event.EventBus.getDefault().unregister(this);
}


private void initUI() {
    setSupportActionBar(binding.toolbar.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
}


/**
 * This method starts the bootstrap chain.
 */
private void checkPassword() {
    if ((com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, null) != null) && com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_password_access", false)) {
        it.feio.android.omninotes.utils.PasswordHelper.requestPassword(this, (it.feio.android.omninotes.models.PasswordValidator.Result passwordConfirmed) -> {
            switch (passwordConfirmed) {
                case SUCCEED :
                    init();
                    break;
                case FAIL :
                    finish();
                    break;
                case RESTORE :
                    it.feio.android.omninotes.utils.PasswordHelper.resetPassword(this);
            }
        });
    } else {
        init();
    }
}


public void onEvent(it.feio.android.omninotes.async.bus.PasswordRemovedEvent passwordRemovedEvent) {
    showMessage(it.feio.android.omninotes.R.string.password_successfully_removed, it.feio.android.omninotes.models.ONStyle.ALERT);
    init();
}


private void init() {
    isPasswordAccepted = true;
    getFragmentManagerInstance();
    it.feio.android.omninotes.NavigationDrawerFragment mNavigationDrawerFragment;
    mNavigationDrawerFragment = ((it.feio.android.omninotes.NavigationDrawerFragment) (getFragmentManagerInstance().findFragmentById(it.feio.android.omninotes.R.id.navigation_drawer)));
    if (mNavigationDrawerFragment == null) {
        androidx.fragment.app.FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManagerInstance().beginTransaction();
        fragmentTransaction.replace(it.feio.android.omninotes.R.id.navigation_drawer, new it.feio.android.omninotes.NavigationDrawerFragment(), it.feio.android.omninotes.MainActivity.FRAGMENT_DRAWER_TAG).commit();
    }
    if (getFragmentManagerInstance().findFragmentByTag(it.feio.android.omninotes.MainActivity.FRAGMENT_LIST_TAG) == null) {
        androidx.fragment.app.FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManagerInstance().beginTransaction();
        fragmentTransaction.add(it.feio.android.omninotes.R.id.fragment_container, new it.feio.android.omninotes.ListFragment(), it.feio.android.omninotes.MainActivity.FRAGMENT_LIST_TAG).commit();
    }
    handleIntents();
}


private androidx.fragment.app.FragmentManager getFragmentManagerInstance() {
    if (mFragmentManager == null) {
        mFragmentManager = getSupportFragmentManager();
    }
    return mFragmentManager;
}


@java.lang.Override
protected void onNewIntent(android.content.Intent intent) {
    if (intent.getAction() == null) {
        switch(MUID_STATIC) {
            // MainActivity_1_RandomActionIntentDefinitionOperatorMutator
            case 1139: {
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
            intent.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_START_APP);
            break;
        }
    }
}
super.onNewIntent(intent);
setIntent(intent);
handleIntents();
it.feio.android.omninotes.helpers.LogDelegate.d("onNewIntent");
}


public android.view.MenuItem getSearchMenuItem() {
androidx.fragment.app.Fragment f;
f = checkFragmentInstance(it.feio.android.omninotes.R.id.fragment_container, it.feio.android.omninotes.ListFragment.class);
if (f != null) {
    return ((it.feio.android.omninotes.ListFragment) (f)).getSearchMenuItem();
} else {
    return null;
}
}


public void editTag(it.feio.android.omninotes.models.Category tag) {
androidx.fragment.app.Fragment f;
f = checkFragmentInstance(it.feio.android.omninotes.R.id.fragment_container, it.feio.android.omninotes.ListFragment.class);
if (f != null) {
    ((it.feio.android.omninotes.ListFragment) (f)).editCategory(tag);
}
}


public void initNotesList(android.content.Intent intent) {
if (intent != null) {
    androidx.fragment.app.Fragment searchTagFragment;
    searchTagFragment = startSearchView();
    new android.os.Handler(getMainLooper()).post(() -> ((it.feio.android.omninotes.ListFragment) (searchTagFragment)).initNotesList(intent));
}
}


public androidx.fragment.app.Fragment startSearchView() {
androidx.fragment.app.FragmentTransaction transaction;
transaction = getFragmentManagerInstance().beginTransaction();
animateTransition(transaction, it.feio.android.omninotes.BaseActivity.TRANSITION_HORIZONTAL);
it.feio.android.omninotes.ListFragment mListFragment;
mListFragment = new it.feio.android.omninotes.ListFragment();
transaction.replace(it.feio.android.omninotes.R.id.fragment_container, mListFragment, it.feio.android.omninotes.MainActivity.FRAGMENT_LIST_TAG).addToBackStack(it.feio.android.omninotes.MainActivity.FRAGMENT_DETAIL_TAG).commit();
android.os.Bundle args;
args = new android.os.Bundle();
args.putBoolean("setSearchFocus", true);
mListFragment.setArguments(args);
return mListFragment;
}


public void commitPending() {
androidx.fragment.app.Fragment f;
f = checkFragmentInstance(it.feio.android.omninotes.R.id.fragment_container, it.feio.android.omninotes.ListFragment.class);
if (f != null) {
    ((it.feio.android.omninotes.ListFragment) (f)).commitPending();
}
}


/**
 * Checks if allocated fragment is of the required type and then returns it or returns null
 */
private androidx.fragment.app.Fragment checkFragmentInstance(int id, java.lang.Object instanceClass) {
androidx.fragment.app.Fragment result;
result = null;
androidx.fragment.app.Fragment fragment;
fragment = getFragmentManagerInstance().findFragmentById(id);
if ((fragment != null) && instanceClass.equals(fragment.getClass())) {
    result = fragment;
}
return result;
}


@java.lang.Override
public void onBackPressed() {
// SketchFragment
androidx.fragment.app.Fragment f;
f = checkFragmentInstance(it.feio.android.omninotes.R.id.fragment_container, it.feio.android.omninotes.SketchFragment.class);
if (f != null) {
    ((it.feio.android.omninotes.SketchFragment) (f)).save();
    // Removes forced portrait orientation for this fragment
    setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    getFragmentManagerInstance().popBackStack();
    return;
}
// DetailFragment
f = checkFragmentInstance(it.feio.android.omninotes.R.id.fragment_container, it.feio.android.omninotes.DetailFragment.class);
if (f != null) {
    ((it.feio.android.omninotes.DetailFragment) (f)).goBack = true;
    ((it.feio.android.omninotes.DetailFragment) (f)).saveAndExit(((it.feio.android.omninotes.DetailFragment) (f)));
    return;
}
// ListFragment
f = checkFragmentInstance(it.feio.android.omninotes.R.id.fragment_container, it.feio.android.omninotes.ListFragment.class);
if (f != null) {
    // Before exiting from app the navigation drawer is opened
    if ((com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_navdrawer_on_exit", false) && (getDrawerLayout() != null)) && (!getDrawerLayout().isDrawerOpen(androidx.core.view.GravityCompat.START))) {
        getDrawerLayout().openDrawer(androidx.core.view.GravityCompat.START);
    } else if (((!com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_navdrawer_on_exit", false)) && (getDrawerLayout() != null)) && getDrawerLayout().isDrawerOpen(androidx.core.view.GravityCompat.START)) {
        getDrawerLayout().closeDrawer(androidx.core.view.GravityCompat.START);
    } else if (!((it.feio.android.omninotes.ListFragment) (f)).closeFab()) {
        isPasswordAccepted = false;
        super.onBackPressed();
    }
    return;
}
super.onBackPressed();
}


@java.lang.Override
public void onSaveInstanceState(android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putString("navigationTmp", navigationTmp);
}


@java.lang.Override
protected void onPause() {
super.onPause();
de.keyboardsurfer.android.widget.crouton.Crouton.cancelAllCroutons();
}


public androidx.drawerlayout.widget.DrawerLayout getDrawerLayout() {
return binding.drawerLayout;
}


public androidx.appcompat.app.ActionBarDrawerToggle getDrawerToggle() {
if (getFragmentManagerInstance().findFragmentById(it.feio.android.omninotes.R.id.navigation_drawer) != null) {
    return ((it.feio.android.omninotes.NavigationDrawerFragment) (getFragmentManagerInstance().findFragmentById(it.feio.android.omninotes.R.id.navigation_drawer))).mDrawerToggle;
} else {
    return null;
}
}


/**
 * Finishes multiselection mode started by ListFragment
 */
public void finishActionMode() {
it.feio.android.omninotes.ListFragment fragment;
fragment = ((it.feio.android.omninotes.ListFragment) (getFragmentManagerInstance().findFragmentByTag(it.feio.android.omninotes.MainActivity.FRAGMENT_LIST_TAG)));
if (fragment != null) {
    fragment.finishActionMode();
}
}


androidx.appcompat.widget.Toolbar getToolbar() {
return binding.toolbar.toolbar;
}


private void handleIntents() {
android.content.Intent i;
switch(MUID_STATIC) {
    // MainActivity_2_RandomActionIntentDefinitionOperatorMutator
    case 2139: {
        i = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    i = getIntent();
    break;
}
}
if (i.getAction() == null) {
return;
}
if (it.feio.android.omninotes.utils.ConstantsBase.ACTION_RESTART_APP.equals(i.getAction())) {
it.feio.android.omninotes.utils.SystemHelper.restartApp();
}
if (receivedIntent(i)) {
it.feio.android.omninotes.models.Note note;
note = i.getParcelableExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE);
if (note == null) {
    note = it.feio.android.omninotes.db.DbHelper.getInstance().getNote(i.getIntExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_KEY, 0));
}
// Checks if the same note is already opened to avoid to open again
if ((note != null) && noteAlreadyOpened(note)) {
    return;
}
// Empty note instantiation
if (note == null) {
    note = new it.feio.android.omninotes.models.Note();
}
switchToDetail(note);
return;
}
if (it.feio.android.omninotes.utils.ConstantsBase.ACTION_SEND_AND_EXIT.equals(i.getAction())) {
saveAndExit(i);
return;
}
// Tag search
if (android.content.Intent.ACTION_VIEW.equals(i.getAction()) && i.getDataString().startsWith(it.feio.android.pixlui.links.UrlCompleter.HASHTAG_SCHEME)) {
switchToList();
return;
}
// Home launcher shortcut widget
if (android.content.Intent.ACTION_VIEW.equals(i.getAction()) && (i.getData() != null)) {
java.lang.Long id;
id = java.lang.Long.valueOf(android.net.Uri.parse(i.getDataString()).getQueryParameter("id"));
it.feio.android.omninotes.models.Note note;
note = it.feio.android.omninotes.db.DbHelper.getInstance().getNote(id);
if (note == null) {
    showMessage(it.feio.android.omninotes.R.string.note_doesnt_exist, it.feio.android.omninotes.models.ONStyle.ALERT);
    return;
}
switchToDetail(note);
return;
}
// Home launcher "new note" shortcut widget
if (it.feio.android.omninotes.utils.ConstantsBase.ACTION_SHORTCUT_WIDGET.equals(i.getAction())) {
switchToDetail(new it.feio.android.omninotes.models.Note());
return;
}
}


/**
 * Used to perform a quick text-only note saving (eg. Tasker+Pushbullet)
 */
private void saveAndExit(android.content.Intent i) {
it.feio.android.omninotes.models.Note note;
note = new it.feio.android.omninotes.models.Note();
note.setTitle(i.getStringExtra(android.content.Intent.EXTRA_SUBJECT));
note.setContent(i.getStringExtra(android.content.Intent.EXTRA_TEXT));
it.feio.android.omninotes.db.DbHelper.getInstance().updateNote(note, true);
showToast(getString(it.feio.android.omninotes.R.string.note_updated), android.widget.Toast.LENGTH_SHORT);
finish();
}


private boolean receivedIntent(android.content.Intent i) {
return ((((it.feio.android.omninotes.utils.ConstantsBase.ACTION_SHORTCUT.equals(i.getAction()) || it.feio.android.omninotes.utils.ConstantsBase.ACTION_NOTIFICATION_CLICK.equals(i.getAction())) || it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET.equals(i.getAction())) || it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET_TAKE_PHOTO.equals(i.getAction())) || (((android.content.Intent.ACTION_SEND.equals(i.getAction()) || android.content.Intent.ACTION_SEND_MULTIPLE.equals(i.getAction())) || it.feio.android.omninotes.utils.ConstantsBase.INTENT_GOOGLE_NOW.equals(i.getAction())) && (i.getType() != null))) || i.getAction().contains(it.feio.android.omninotes.utils.ConstantsBase.ACTION_NOTIFICATION_CLICK);
}


private boolean noteAlreadyOpened(it.feio.android.omninotes.models.Note note) {
it.feio.android.omninotes.DetailFragment detailFragment;
detailFragment = ((it.feio.android.omninotes.DetailFragment) (getFragmentManagerInstance().findFragmentByTag(it.feio.android.omninotes.MainActivity.FRAGMENT_DETAIL_TAG)));
return (detailFragment != null) && it.feio.android.omninotes.helpers.NotesHelper.haveSameId(note, detailFragment.getCurrentNote());
}


public void switchToList() {
androidx.fragment.app.FragmentTransaction transaction;
transaction = getFragmentManagerInstance().beginTransaction();
animateTransition(transaction, it.feio.android.omninotes.BaseActivity.TRANSITION_HORIZONTAL);
it.feio.android.omninotes.ListFragment mListFragment;
mListFragment = new it.feio.android.omninotes.ListFragment();
transaction.replace(it.feio.android.omninotes.R.id.fragment_container, mListFragment, it.feio.android.omninotes.MainActivity.FRAGMENT_LIST_TAG).addToBackStack(it.feio.android.omninotes.MainActivity.FRAGMENT_DETAIL_TAG).commitAllowingStateLoss();
if (getDrawerToggle() != null) {
getDrawerToggle().setDrawerIndicatorEnabled(false);
}
getFragmentManagerInstance().getFragments();
de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.SwitchFragmentEvent(it.feio.android.omninotes.async.bus.SwitchFragmentEvent.Direction.PARENT));
}


public void switchToDetail(it.feio.android.omninotes.models.Note note) {
androidx.fragment.app.FragmentTransaction transaction;
transaction = getFragmentManagerInstance().beginTransaction();
animateTransition(transaction, it.feio.android.omninotes.BaseActivity.TRANSITION_HORIZONTAL);
it.feio.android.omninotes.DetailFragment mDetailFragment;
mDetailFragment = new it.feio.android.omninotes.DetailFragment();
android.os.Bundle b;
b = new android.os.Bundle();
b.putParcelable(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE, note);
mDetailFragment.setArguments(b);
if (getFragmentManagerInstance().findFragmentByTag(it.feio.android.omninotes.MainActivity.FRAGMENT_DETAIL_TAG) == null) {
transaction.replace(it.feio.android.omninotes.R.id.fragment_container, mDetailFragment, it.feio.android.omninotes.MainActivity.FRAGMENT_DETAIL_TAG).addToBackStack(it.feio.android.omninotes.MainActivity.FRAGMENT_LIST_TAG).commitAllowingStateLoss();
} else {
getFragmentManagerInstance().popBackStackImmediate();
transaction.replace(it.feio.android.omninotes.R.id.fragment_container, mDetailFragment, it.feio.android.omninotes.MainActivity.FRAGMENT_DETAIL_TAG).addToBackStack(it.feio.android.omninotes.MainActivity.FRAGMENT_DETAIL_TAG).commitAllowingStateLoss();
}
}


public void shareNote(it.feio.android.omninotes.models.Note note) {
java.lang.String titleText;
titleText = note.getTitle();
java.lang.String contentText;
contentText = (titleText + java.lang.System.getProperty("line.separator")) + note.getContent();
android.content.Intent shareIntent;
switch(MUID_STATIC) {
// MainActivity_3_NullIntentOperatorMutator
case 3139: {
    shareIntent = null;
    break;
}
// MainActivity_4_RandomActionIntentDefinitionOperatorMutator
case 4139: {
    shareIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
shareIntent = new android.content.Intent();
break;
}
}
// Prepare sharing intent with only text
if (note.getAttachmentsList().isEmpty()) {
switch(MUID_STATIC) {
// MainActivity_5_RandomActionIntentDefinitionOperatorMutator
case 5139: {
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
shareIntent.setAction(android.content.Intent.ACTION_SEND);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_6_RandomActionIntentDefinitionOperatorMutator
case 6139: {
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
shareIntent.setType("text/plain");
break;
}
}
// Intent with single image attachment
} else if (note.getAttachmentsList().size() == 1) {
it.feio.android.omninotes.models.Attachment attachment;
attachment = note.getAttachmentsList().get(0);
android.net.Uri shareableAttachmentUri;
shareableAttachmentUri = getShareableAttachmentUri(attachment);
if (shareableAttachmentUri != null) {
switch(MUID_STATIC) {
// MainActivity_7_RandomActionIntentDefinitionOperatorMutator
case 7139: {
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
shareIntent.setAction(android.content.Intent.ACTION_SEND);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_8_RandomActionIntentDefinitionOperatorMutator
case 8139: {
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
shareIntent.setType(attachment.getMime_type());
break;
}
}
switch(MUID_STATIC) {
// MainActivity_9_NullValueIntentPutExtraOperatorMutator
case 9139: {
shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, new Parcelable[0]);
break;
}
// MainActivity_10_IntentPayloadReplacementOperatorMutator
case 10139: {
shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, (android.net.Uri) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_11_RandomActionIntentDefinitionOperatorMutator
case 11139: {
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
shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, shareableAttachmentUri);
break;
}
}
break;
}
}
}
// Intent with multiple images
} else if (note.getAttachmentsList().size() > 1) {
switch(MUID_STATIC) {
// MainActivity_12_RandomActionIntentDefinitionOperatorMutator
case 12139: {
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
shareIntent.setAction(android.content.Intent.ACTION_SEND_MULTIPLE);
break;
}
}
java.util.ArrayList<android.net.Uri> uris;
uris = new java.util.ArrayList<>();
// A check to decide the mime type of attachments to share is done here
java.util.HashMap<java.lang.String, java.lang.Boolean> mimeTypes;
mimeTypes = new java.util.HashMap<>();
for (it.feio.android.omninotes.models.Attachment attachment : note.getAttachmentsList()) {
android.net.Uri shareableAttachmentUri;
shareableAttachmentUri = getShareableAttachmentUri(attachment);
if (shareableAttachmentUri != null) {
uris.add(shareableAttachmentUri);
mimeTypes.put(attachment.getMime_type(), true);
}
}
// If many mime types are present a general type is assigned to intent
if (mimeTypes.size() > 1) {
switch(MUID_STATIC) {
// MainActivity_13_RandomActionIntentDefinitionOperatorMutator
case 13139: {
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
shareIntent.setType("*/*");
break;
}
}
} else {
switch(MUID_STATIC) {
// MainActivity_14_RandomActionIntentDefinitionOperatorMutator
case 14139: {
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
shareIntent.setType(((java.lang.String) (mimeTypes.keySet().toArray()[0])));
break;
}
}
}
switch(MUID_STATIC) {
// MainActivity_15_RandomActionIntentDefinitionOperatorMutator
case 15139: {
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
shareIntent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, uris);
break;
}
}
}
switch(MUID_STATIC) {
// MainActivity_16_NullValueIntentPutExtraOperatorMutator
case 16139: {
shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, new Parcelable[0]);
break;
}
// MainActivity_17_IntentPayloadReplacementOperatorMutator
case 17139: {
shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_18_RandomActionIntentDefinitionOperatorMutator
case 18139: {
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
shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titleText);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainActivity_19_NullValueIntentPutExtraOperatorMutator
case 19139: {
shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, new Parcelable[0]);
break;
}
// MainActivity_20_IntentPayloadReplacementOperatorMutator
case 20139: {
shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_21_RandomActionIntentDefinitionOperatorMutator
case 21139: {
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
shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, contentText);
break;
}
}
break;
}
}
startActivity(android.content.Intent.createChooser(shareIntent, getResources().getString(it.feio.android.omninotes.R.string.share_message_chooser)));
}


@androidx.annotation.Nullable
public android.net.Uri getShareableAttachmentUri(it.feio.android.omninotes.models.Attachment attachment) {
try {
return it.feio.android.omninotes.utils.FileProviderHelper.getShareableUri(attachment);
} catch (java.io.FileNotFoundException e) {
it.feio.android.omninotes.helpers.LogDelegate.e(e.getMessage());
android.widget.Toast.makeText(this, it.feio.android.omninotes.R.string.attachment_not_found, android.widget.Toast.LENGTH_SHORT).show();
return null;
}
}


/**
 * Single note permanent deletion
 *
 * @param note
 * 		Note to be deleted
 */
public void deleteNote(it.feio.android.omninotes.models.Note note) {
new it.feio.android.omninotes.async.notes.NoteProcessorDelete(java.util.Collections.singletonList(note)).process();
it.feio.android.omninotes.BaseActivity.notifyAppWidgets(this);
it.feio.android.omninotes.helpers.LogDelegate.d(("Deleted permanently note with ID '" + note.get_id()) + "'");
}


public void updateWidgets() {
new it.feio.android.omninotes.async.UpdateWidgetsTask(getApplicationContext()).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
}


public void showMessage(int messageId, de.keyboardsurfer.android.widget.crouton.Style style) {
showMessage(getString(messageId), style);
}


public void showMessage(java.lang.String message, de.keyboardsurfer.android.widget.crouton.Style style) {
// ViewGroup used to show Crouton keeping compatibility with the new Toolbar
runOnUiThread(() -> de.keyboardsurfer.android.widget.crouton.Crouton.makeText(this, message, style, binding.croutonHandle.croutonHandle).show());
}


@java.lang.Override
public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String key) {
prefsChanged = true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
