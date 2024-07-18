package com.automattic.simplenote;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationException;
import java.net.URISyntaxException;
import com.automattic.simplenote.utils.WordPressUtils;
import android.net.Uri;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.net.URI;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;
import com.automattic.simplenote.utils.PrefUtils;
import com.automattic.simplenote.utils.DrawableUtils;
import okhttp3.Call;
import com.automattic.simplenote.utils.StrUtils;
import android.content.SharedPreferences;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import org.json.JSONException;
import android.os.Bundle;
import android.view.ViewGroup;
import android.text.TextUtils;
import java.io.IOException;
import android.text.Spanned;
import android.content.Intent;
import org.json.JSONArray;
import androidx.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import net.openid.appauth.AuthorizationResponse;
import android.content.ClipboardManager;
import okhttp3.Callback;
import android.widget.CheckBox;
import okhttp3.Response;
import net.openid.appauth.AuthorizationService;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import com.automattic.simplenote.models.Note;
import androidx.appcompat.view.ContextThemeWrapper;
import org.json.JSONObject;
import android.content.ClipData;
import androidx.annotation.Nullable;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class WordPressDialogFragment extends androidx.appcompat.app.AppCompatDialogFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String DIALOG_TAG = "wordpress_dialog";

    private static final java.lang.String API_FIELD_URL = "URL";

    private static final java.lang.String API_FIELD_NAME = "name";

    private static final java.lang.String API_FIELD_SITES = "sites";

    private android.view.View mConnectSection;

    private android.view.View mPostingSection;

    private android.view.View mFieldsSection;

    private android.view.View mSuccessSection;

    private android.widget.ListView mListView;

    private android.widget.CheckBox mDraftCheckbox;

    private android.widget.TextView mPostSuccessTextView;

    private android.widget.Button mPostButton;

    private android.widget.Button mCancelButton;

    private java.lang.String mPostUrl;

    private org.json.JSONArray mSitesArray = new org.json.JSONArray();

    private com.automattic.simplenote.models.Note mNote;

    private java.lang.String mAuthState;

    private enum DialogStatus {

        CONNECT,
        FIELDS,
        POSTING,
        SUCCESS;}

    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        android.view.View view;
        view = android.view.View.inflate(getActivity(), com.automattic.simplenote.R.layout.dialog_wordpress_post, null);
        switch(MUID_STATIC) {
            // WordPressDialogFragment_0_InvalidViewFocusOperatorMutator
            case 94: {
                /**
                * Inserted by Kadabra
                */
                mConnectSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_connect);
                mConnectSection.requestFocus();
                break;
            }
            // WordPressDialogFragment_1_ViewComponentNotVisibleOperatorMutator
            case 194: {
                /**
                * Inserted by Kadabra
                */
                mConnectSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_connect);
                mConnectSection.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mConnectSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_connect);
            break;
        }
    }
    switch(MUID_STATIC) {
        // WordPressDialogFragment_2_InvalidViewFocusOperatorMutator
        case 294: {
            /**
            * Inserted by Kadabra
            */
            mPostingSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_posting);
            mPostingSection.requestFocus();
            break;
        }
        // WordPressDialogFragment_3_ViewComponentNotVisibleOperatorMutator
        case 394: {
            /**
            * Inserted by Kadabra
            */
            mPostingSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_posting);
            mPostingSection.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mPostingSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_posting);
        break;
    }
}
switch(MUID_STATIC) {
    // WordPressDialogFragment_4_InvalidViewFocusOperatorMutator
    case 494: {
        /**
        * Inserted by Kadabra
        */
        mFieldsSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_fields);
        mFieldsSection.requestFocus();
        break;
    }
    // WordPressDialogFragment_5_ViewComponentNotVisibleOperatorMutator
    case 594: {
        /**
        * Inserted by Kadabra
        */
        mFieldsSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_fields);
        mFieldsSection.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mFieldsSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_fields);
    break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_6_InvalidViewFocusOperatorMutator
case 694: {
    /**
    * Inserted by Kadabra
    */
    mSuccessSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_success);
    mSuccessSection.requestFocus();
    break;
}
// WordPressDialogFragment_7_ViewComponentNotVisibleOperatorMutator
case 794: {
    /**
    * Inserted by Kadabra
    */
    mSuccessSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_success);
    mSuccessSection.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mSuccessSection = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_section_success);
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_8_InvalidViewFocusOperatorMutator
case 894: {
/**
* Inserted by Kadabra
*/
mListView = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_list_view);
mListView.requestFocus();
break;
}
// WordPressDialogFragment_9_ViewComponentNotVisibleOperatorMutator
case 994: {
/**
* Inserted by Kadabra
*/
mListView = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_list_view);
mListView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mListView = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_list_view);
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_10_InvalidViewFocusOperatorMutator
case 1094: {
/**
* Inserted by Kadabra
*/
mDraftCheckbox = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_draft_checkbox);
mDraftCheckbox.requestFocus();
break;
}
// WordPressDialogFragment_11_ViewComponentNotVisibleOperatorMutator
case 1194: {
/**
* Inserted by Kadabra
*/
mDraftCheckbox = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_draft_checkbox);
mDraftCheckbox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDraftCheckbox = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_draft_checkbox);
break;
}
}
android.widget.Button copyUrlButton;
switch(MUID_STATIC) {
// WordPressDialogFragment_12_InvalidViewFocusOperatorMutator
case 1294: {
/**
* Inserted by Kadabra
*/
copyUrlButton = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_copy_url);
copyUrlButton.requestFocus();
break;
}
// WordPressDialogFragment_13_ViewComponentNotVisibleOperatorMutator
case 1394: {
/**
* Inserted by Kadabra
*/
copyUrlButton = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_copy_url);
copyUrlButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
copyUrlButton = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_copy_url);
break;
}
}
android.widget.Button shareUrlButton;
switch(MUID_STATIC) {
// WordPressDialogFragment_14_InvalidViewFocusOperatorMutator
case 1494: {
/**
* Inserted by Kadabra
*/
shareUrlButton = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_share);
shareUrlButton.requestFocus();
break;
}
// WordPressDialogFragment_15_ViewComponentNotVisibleOperatorMutator
case 1594: {
/**
* Inserted by Kadabra
*/
shareUrlButton = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_share);
shareUrlButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
shareUrlButton = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_share);
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_16_InvalidViewFocusOperatorMutator
case 1694: {
/**
* Inserted by Kadabra
*/
mPostSuccessTextView = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_success_summary);
mPostSuccessTextView.requestFocus();
break;
}
// WordPressDialogFragment_17_ViewComponentNotVisibleOperatorMutator
case 1794: {
/**
* Inserted by Kadabra
*/
mPostSuccessTextView = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_success_summary);
mPostSuccessTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mPostSuccessTextView = view.findViewById(com.automattic.simplenote.R.id.wp_dialog_success_summary);
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_18_BuggyGUIListenerOperatorMutator
case 1894: {
copyUrlButton.setOnClickListener(null);
break;
}
default: {
copyUrlButton.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
if (getActivity() == null) {
return;
}
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (getActivity().getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
switch(MUID_STATIC) {
// WordPressDialogFragment_19_LengthyGUIListenerOperatorMutator
case 1994: {
/**
* Inserted by Kadabra
*/
if (clipboard != null) {
android.content.ClipData clip;
clip = android.content.ClipData.newPlainText("Simplenote", mPostUrl);
clipboard.setPrimaryClip(clip);
android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.link_copied, android.widget.Toast.LENGTH_SHORT).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (clipboard != null) {
android.content.ClipData clip;
clip = android.content.ClipData.newPlainText("Simplenote", mPostUrl);
clipboard.setPrimaryClip(clip);
android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.link_copied, android.widget.Toast.LENGTH_SHORT).show();
}
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_20_BuggyGUIListenerOperatorMutator
case 2094: {
shareUrlButton.setOnClickListener(null);
break;
}
default: {
shareUrlButton.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
if (getActivity() == null) {
return;
}
android.content.Intent share;
switch(MUID_STATIC) {
// WordPressDialogFragment_22_InvalidKeyIntentOperatorMutator
case 2294: {
share = new android.content.Intent((String) null);
break;
}
// WordPressDialogFragment_23_RandomActionIntentDefinitionOperatorMutator
case 2394: {
share = new android.content.Intent(android.content.Intent.ACTION_VIEW);
break;
}
default: {
share = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_24_RandomActionIntentDefinitionOperatorMutator
case 2494: {
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
share.setType("text/plain");
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_25_NullValueIntentPutExtraOperatorMutator
case 2594: {
share.putExtra(android.content.Intent.EXTRA_TEXT, new Parcelable[0]);
break;
}
// WordPressDialogFragment_26_IntentPayloadReplacementOperatorMutator
case 2694: {
share.putExtra(android.content.Intent.EXTRA_TEXT, "");
break;
}
default: {
switch(MUID_STATIC) {
// WordPressDialogFragment_27_RandomActionIntentDefinitionOperatorMutator
case 2794: {
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
share.putExtra(android.content.Intent.EXTRA_TEXT, mPostUrl);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// WordPressDialogFragment_21_LengthyGUIListenerOperatorMutator
case 2194: {
/**
* Inserted by Kadabra
*/
startActivity(android.content.Intent.createChooser(share, getString(com.automattic.simplenote.R.string.wordpress_post_link)));
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
startActivity(android.content.Intent.createChooser(share, getString(com.automattic.simplenote.R.string.wordpress_post_link)));
break;
}
}
}

});
break;
}
}
if (getActivity() != null) {
return new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(requireContext(), com.automattic.simplenote.R.style.Dialog)).setView(view).setTitle(com.automattic.simplenote.R.string.post_to_wordpress).setPositiveButton(com.automattic.simplenote.R.string.send_post, null).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).create();
} else {
return super.onCreateDialog(savedInstanceState);
}
}


@java.lang.Override
public void onResume() {
super.onResume();
// Get the alert dialog buttons, so we can set their visibility and titles
final androidx.appcompat.app.AlertDialog dialog;
dialog = ((androidx.appcompat.app.AlertDialog) (getDialog()));
if (dialog != null) {
mPostButton = dialog.getButton(android.app.Dialog.BUTTON_POSITIVE);
mPostButton.setOnClickListener(onPostClickListener);
mCancelButton = dialog.getButton(android.app.Dialog.BUTTON_NEGATIVE);
}
// Don't reset if we've resumed in a state besides connect
if (mConnectSection.getVisibility() != android.view.View.VISIBLE) {
return;
}
if (!com.automattic.simplenote.utils.WordPressUtils.hasWPToken(getActivity())) {
// No WordPress token found, show connect UI
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT);
} else {
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}
loadSites()// Load locally stored sites
;// Load locally stored sites

fetchSitesFromAPI()// Fetch any new sites from the API
;// Fetch any new sites from the API

}


private void loadSites() {
if ((getActivity() == null) || (!com.automattic.simplenote.utils.WordPressUtils.hasWPToken(getActivity()))) {
return;
}
if (loadSitesFromPreferences()) {
com.automattic.simplenote.WordPressDialogFragment.SitesAdapter sitesAdapter;
sitesAdapter = new com.automattic.simplenote.WordPressDialogFragment.SitesAdapter(getActivity());
mListView.setAdapter(sitesAdapter);
return;
}
fetchSitesFromAPI();
}


private class SitesAdapter extends android.widget.ArrayAdapter<java.lang.String> {
private SitesAdapter(@androidx.annotation.NonNull
android.content.Context context) {
super(context, 0);
}


@java.lang.Override
public int getCount() {
return mSitesArray.length();
}


@androidx.annotation.NonNull
@java.lang.Override
public android.view.View getView(int position, @androidx.annotation.Nullable
android.view.View convertView, @androidx.annotation.NonNull
android.view.ViewGroup parent) {
final com.automattic.simplenote.WordPressDialogFragment.SitesAdapter.SiteViewHolder holder;
if (convertView == null) {
convertView = getLayoutInflater().inflate(com.automattic.simplenote.R.layout.list_item_single_choice, parent, false);
holder = new com.automattic.simplenote.WordPressDialogFragment.SitesAdapter.SiteViewHolder();
switch(MUID_STATIC) {
// WordPressDialogFragment_28_InvalidViewFocusOperatorMutator
case 2894: {
/**
* Inserted by Kadabra
*/
holder.titleTextView = convertView.findViewById(android.R.id.text1);
holder.titleTextView.requestFocus();
break;
}
// WordPressDialogFragment_29_ViewComponentNotVisibleOperatorMutator
case 2994: {
/**
* Inserted by Kadabra
*/
holder.titleTextView = convertView.findViewById(android.R.id.text1);
holder.titleTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.titleTextView = convertView.findViewById(android.R.id.text1);
break;
}
}
convertView.setTag(holder);
} else {
holder = ((com.automattic.simplenote.WordPressDialogFragment.SitesAdapter.SiteViewHolder) (convertView.getTag()));
}
try {
org.json.JSONObject site;
site = mSitesArray.getJSONObject(position);
android.text.Spanned rowText;
rowText = android.text.Html.fromHtml(java.lang.String.format(java.util.Locale.ENGLISH, ("%s<br/><small><span style=\"color:#" + java.lang.Integer.toHexString(com.automattic.simplenote.utils.DrawableUtils.getColor(requireContext(), com.automattic.simplenote.R.attr.notePreviewColor) & 0xffffff)) + "\">%s</span></small>", site.getString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_NAME), site.getString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_URL)));
holder.titleTextView.setText(rowText);
} catch (org.json.JSONException e) {
holder.titleTextView.setText(com.automattic.simplenote.R.string.untitled_site);
}
if ((position == 0) && (mListView.getCheckedItemPosition() < 0)) {
mListView.setItemChecked(0, true);
}
return convertView;
}


private class SiteViewHolder {
androidx.appcompat.widget.AppCompatCheckedTextView titleTextView;
}
}

private void saveSitesToPreferences() {
if (getActivity() == null) {
return;
}
android.content.SharedPreferences.Editor editor;
editor = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
editor.putString(com.automattic.simplenote.utils.PrefUtils.PREF_WORDPRESS_SITES, mSitesArray.toString());
editor.apply();
}


private boolean loadSitesFromPreferences() {
if (getActivity() == null) {
return false;
}
java.lang.String savedSites;
savedSites = com.automattic.simplenote.utils.PrefUtils.getStringPref(getActivity(), com.automattic.simplenote.utils.PrefUtils.PREF_WORDPRESS_SITES);
if (!android.text.TextUtils.isEmpty(savedSites)) {
try {
mSitesArray = new org.json.JSONArray(savedSites);
return true;
} catch (org.json.JSONException e) {
return false;
}
}
return false;
}


public void setNote(com.automattic.simplenote.models.Note note) {
mNote = note;
}


private void setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus status) {
mConnectSection.setVisibility(status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT ? android.view.View.VISIBLE : android.view.View.GONE);
mFieldsSection.setVisibility(status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS ? android.view.View.VISIBLE : android.view.View.GONE);
mPostingSection.setVisibility(status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.POSTING ? android.view.View.VISIBLE : android.view.View.GONE);
mSuccessSection.setVisibility(status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.SUCCESS ? android.view.View.VISIBLE : android.view.View.GONE);
mCancelButton.setVisibility((status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS) || (status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT) ? android.view.View.VISIBLE : android.view.View.GONE);
mPostButton.setVisibility(status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.POSTING ? android.view.View.GONE : android.view.View.VISIBLE);
if (status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.SUCCESS) {
mPostButton.setText(com.automattic.simplenote.R.string.done);
} else if (status == com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT) {
mPostButton.setText(com.automattic.simplenote.R.string.connect_with_wordpress);
} else {
mPostButton.setText(com.automattic.simplenote.R.string.send_post);
}
}


@java.lang.Override
public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
if (((requestCode != com.automattic.simplenote.utils.WordPressUtils.OAUTH_ACTIVITY_CODE) || (data == null)) || (getActivity() == null)) {
return;
}
net.openid.appauth.AuthorizationResponse authResponse;
authResponse = net.openid.appauth.AuthorizationResponse.fromIntent(data);
net.openid.appauth.AuthorizationException authException;
authException = net.openid.appauth.AuthorizationException.fromIntent(data);
if (authException != null) {
// Error encountered
android.net.Uri dataUri;
dataUri = data.getData();
if (dataUri == null) {
return;
}
if (com.automattic.simplenote.utils.StrUtils.isSameStr(dataUri.getQueryParameter("code"), "1")) {
android.widget.Toast.makeText(getActivity(), getString(com.automattic.simplenote.R.string.wpcom_log_in_error_unverified), android.widget.Toast.LENGTH_SHORT).show();
} else {
android.widget.Toast.makeText(getActivity(), getString(com.automattic.simplenote.R.string.wpcom_log_in_error_generic), android.widget.Toast.LENGTH_SHORT).show();
}
} else if (authResponse != null) {
// Save token and finish activity
com.automattic.simplenote.Simplenote app;
app = ((com.automattic.simplenote.Simplenote) (getActivity().getApplication()));
boolean authSuccess;
authSuccess = com.automattic.simplenote.utils.WordPressUtils.processAuthResponse(app, authResponse, mAuthState, false);
if (!authSuccess) {
android.widget.Toast.makeText(getActivity(), getString(com.automattic.simplenote.R.string.wpcom_log_in_error_generic), android.widget.Toast.LENGTH_SHORT).show();
} else {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.WPCC_LOGIN_SUCCEEDED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "wpcc_login_succeeded_post_fragment");
loadSites();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}
}
}


private final android.view.View.OnClickListener onPostClickListener = new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
if ((mNote == null) || (!isAdded())) {
return;
}
// Perform different actions depending on the state of the dialog
if (mSuccessSection.getVisibility() == android.view.View.VISIBLE) {
dismiss();
return;
} else if (mConnectSection.getVisibility() == android.view.View.VISIBLE) {
if (getActivity() == null) {
return;
}
net.openid.appauth.AuthorizationRequest.Builder authBuilder;
authBuilder = com.automattic.simplenote.utils.WordPressUtils.getWordPressAuthorizationRequestBuilder();
// Set a unique state value
mAuthState = "app-" + java.util.UUID.randomUUID();
authBuilder.setState(mAuthState);
net.openid.appauth.AuthorizationRequest request;
request = authBuilder.build();
net.openid.appauth.AuthorizationService authService;
authService = new net.openid.appauth.AuthorizationService(getActivity());
android.content.Intent authIntent;
switch(MUID_STATIC) {
// WordPressDialogFragment_31_RandomActionIntentDefinitionOperatorMutator
case 3194: {
authIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
authIntent = authService.getAuthorizationRequestIntent(request);
break;
}
}
startActivityForResult(authIntent, com.automattic.simplenote.utils.WordPressUtils.OAUTH_ACTIVITY_CODE);
return;
}
int selectedListPosition;
selectedListPosition = mListView.getCheckedItemPosition();
if (selectedListPosition < 0) {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.select_site, android.widget.Toast.LENGTH_SHORT).show();
return;
}
org.json.JSONObject site;
try {
site = mSitesArray.getJSONObject(selectedListPosition);
} catch (org.json.JSONException e) {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.could_not_access_site_data, android.widget.Toast.LENGTH_SHORT).show();
return;
}
java.lang.String noteContent;
noteContent = mNote.getContent();
if (android.text.TextUtils.isEmpty(noteContent)) {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.empty_note_post, android.widget.Toast.LENGTH_SHORT).show();
return;
}
java.lang.String postStatus;
postStatus = (mDraftCheckbox.isChecked()) ? "draft" : "publish";
java.lang.String title;
title = "";
java.lang.String content;
content = mNote.getContent();
if (!mNote.getTitle().equals(mNote.getContent())) {
title = mNote.getTitle();
content = content.substring(title.length());
// Get rid of the #'s in front of markdown note titles
if (mNote.isMarkdownEnabled()) {
title = title.replaceFirst("^(#{1,6}[\\s]?)", "");
}
}
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.POSTING);
switch(MUID_STATIC) {
// WordPressDialogFragment_30_LengthyGUIListenerOperatorMutator
case 3094: {
/**
* Inserted by Kadabra
*/
com.automattic.simplenote.utils.WordPressUtils.publishPost(getContext(), site.optString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_URL, ""), title, content, postStatus, new okhttp3.Callback() {
@java.lang.Override
public void onFailure(@androidx.annotation.NonNull
okhttp3.Call call, @androidx.annotation.NonNull
java.io.IOException e) {
if (getActivity() == null) {
return;
}
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
android.widget.Toast.makeText(getContext(), "A network error was encountered. Please try again.", android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}

});
}


@java.lang.Override
public void onResponse(@androidx.annotation.NonNull
okhttp3.Call call, @androidx.annotation.NonNull
final okhttp3.Response response) {
if (getActivity() == null) {
return;
}
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
try {
if (response.body() == null) {
return;
}
if (response.code() == 200) {
java.lang.String responseString;
responseString = response.body().string();
org.json.JSONObject postResult;
postResult = new org.json.JSONObject(responseString);
mPostUrl = postResult.getString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_URL);
mPostSuccessTextView.setText(getString(com.automattic.simplenote.R.string.success, mPostUrl));
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.SUCCESS);
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_SHARED_TO_WORDPRESS, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "wordpress_note_share_success");
} else if (response.code() == 403) {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.reconnect_to_wordpress, android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT);
} else {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.network_error_message, android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}
} catch (java.io.IOException | org.json.JSONException e) {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.network_error_message, android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}
}

});
}

});
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
com.automattic.simplenote.utils.WordPressUtils.publishPost(getContext(), site.optString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_URL, ""), title, content, postStatus, new okhttp3.Callback() {
@java.lang.Override
public void onFailure(@androidx.annotation.NonNull
okhttp3.Call call, @androidx.annotation.NonNull
java.io.IOException e) {
if (getActivity() == null) {
return;
}
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
android.widget.Toast.makeText(getContext(), "A network error was encountered. Please try again.", android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}

});
}


@java.lang.Override
public void onResponse(@androidx.annotation.NonNull
okhttp3.Call call, @androidx.annotation.NonNull
final okhttp3.Response response) {
if (getActivity() == null) {
return;
}
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
try {
if (response.body() == null) {
return;
}
if (response.code() == 200) {
java.lang.String responseString;
responseString = response.body().string();
org.json.JSONObject postResult;
postResult = new org.json.JSONObject(responseString);
mPostUrl = postResult.getString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_URL);
mPostSuccessTextView.setText(getString(com.automattic.simplenote.R.string.success, mPostUrl));
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.SUCCESS);
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_SHARED_TO_WORDPRESS, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "wordpress_note_share_success");
} else if (response.code() == 403) {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.reconnect_to_wordpress, android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT);
} else {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.network_error_message, android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}
} catch (java.io.IOException | org.json.JSONException e) {
android.widget.Toast.makeText(getContext(), com.automattic.simplenote.R.string.network_error_message, android.widget.Toast.LENGTH_SHORT).show();
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.FIELDS);
}
}

});
}

});
break;
}
}
}

};

private void fetchSitesFromAPI() {
com.automattic.simplenote.utils.WordPressUtils.getSites(getActivity(), new okhttp3.Callback() {
@java.lang.Override
public void onFailure(@androidx.annotation.NonNull
final okhttp3.Call call, @androidx.annotation.NonNull
java.io.IOException e) {
if (getActivity() == null) {
return;
}
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
if (mSitesArray.length() == 0) {
// Reset to connect state if we reached an error
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT);
}
}

});
}


@java.lang.Override
public void onResponse(@androidx.annotation.NonNull
okhttp3.Call call, @androidx.annotation.NonNull
final okhttp3.Response response) throws java.io.IOException {
if (getActivity() == null) {
return;
}
if ((response.code() == 200) && (response.body() != null)) {
java.lang.String resultString;
resultString = response.body().string();
try {
org.json.JSONArray sitesArray;
sitesArray = new org.json.JSONObject(resultString).getJSONArray(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_SITES);
final org.json.JSONArray newSitesArray;
newSitesArray = new org.json.JSONArray();
for (int i = 0; i < sitesArray.length(); i++) {
org.json.JSONObject site;
site = sitesArray.getJSONObject(i);
org.json.JSONObject parsedSite;
parsedSite = new org.json.JSONObject();
parsedSite.put(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_NAME, site.getString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_NAME));
java.net.URI uri;
try {
uri = new java.net.URI(site.getString(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_URL));
} catch (java.net.URISyntaxException e) {
// Reset to connect state if we reach an error
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT);
return;
}
parsedSite.put(com.automattic.simplenote.WordPressDialogFragment.API_FIELD_URL, uri.getHost());
newSitesArray.put(i, parsedSite);
}
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
if (newSitesArray.length() > 0) {
mSitesArray = newSitesArray;
saveSitesToPreferences();
}
com.automattic.simplenote.WordPressDialogFragment.SitesAdapter sitesAdapter;
sitesAdapter = new com.automattic.simplenote.WordPressDialogFragment.SitesAdapter(getActivity());
mListView.setAdapter(sitesAdapter);
}

});
} catch (org.json.JSONException e) {
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
// Reset to connect state if we reached an error
if (mSitesArray.length() == 0) {
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT);
}
}

});
}
} else if ((response.code() == 400) || (mSitesArray.length() == 0)) {
if (!isAdded()) {
return;
}
getActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
// Remove WordPress sites
android.content.SharedPreferences.Editor editor;
editor = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
editor.remove(com.automattic.simplenote.utils.PrefUtils.PREF_WORDPRESS_SITES);
editor.apply();
// Reset to connect state if we reached an error
setDialogStatus(com.automattic.simplenote.WordPressDialogFragment.DialogStatus.CONNECT);
}

});
}
}

});
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
