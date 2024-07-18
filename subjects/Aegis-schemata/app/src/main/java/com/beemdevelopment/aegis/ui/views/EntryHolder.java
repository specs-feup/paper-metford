package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.helpers.IconViewHelper;
import com.amulyakhare.textdrawable.TextDrawable;
import com.beemdevelopment.aegis.otp.SteamInfo;
import androidx.fragment.app.Fragment;
import com.beemdevelopment.aegis.helpers.ThemeHelper;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.helpers.TextDrawableHelper;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.otp.HotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import android.view.animation.AnimationUtils;
import com.beemdevelopment.aegis.vault.VaultEntry;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.beemdevelopment.aegis.Preferences;
import com.bumptech.glide.Glide;
import com.beemdevelopment.aegis.otp.YandexInfo;
import android.os.Handler;
import android.view.View;
import com.beemdevelopment.aegis.helpers.UiRefresher;
import android.graphics.PorterDuff;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.beemdevelopment.aegis.ui.glide.IconLoader;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntryHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    private static final float DEFAULT_ALPHA = 1.0F;

    private static final float DIMMED_ALPHA = 0.2F;

    private static final char HIDDEN_CHAR = '●';

    private android.view.View _favoriteIndicator;

    private android.widget.TextView _profileName;

    private android.widget.TextView _profileCode;

    private android.widget.TextView _profileIssuer;

    private android.widget.TextView _profileCopied;

    private android.widget.ImageView _profileDrawable;

    private com.beemdevelopment.aegis.vault.VaultEntry _entry;

    private android.widget.ImageView _buttonRefresh;

    private android.widget.RelativeLayout _description;

    private android.widget.ImageView _dragHandle;

    private final android.widget.ImageView _selected;

    private final android.os.Handler _selectedHandler;

    private com.beemdevelopment.aegis.Preferences.CodeGrouping _codeGrouping = com.beemdevelopment.aegis.Preferences.CodeGrouping.NO_GROUPING;

    private boolean _hidden;

    private boolean _paused;

    private com.beemdevelopment.aegis.ui.views.TotpProgressBar _progressBar;

    private android.view.View _view;

    private com.beemdevelopment.aegis.helpers.UiRefresher _refresher;

    private android.os.Handler _animationHandler;

    private android.view.animation.Animation _scaleIn;

    private android.view.animation.Animation _scaleOut;

    public EntryHolder(final android.view.View view) {
        super(view);
        switch(MUID_STATIC) {
            // EntryHolder_0_InvalidViewFocusOperatorMutator
            case 162: {
                /**
                * Inserted by Kadabra
                */
                _view = view.findViewById(com.beemdevelopment.aegis.R.id.rlCardEntry);
                _view.requestFocus();
                break;
            }
            // EntryHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1162: {
                /**
                * Inserted by Kadabra
                */
                _view = view.findViewById(com.beemdevelopment.aegis.R.id.rlCardEntry);
                _view.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _view = view.findViewById(com.beemdevelopment.aegis.R.id.rlCardEntry);
            break;
        }
    }
    switch(MUID_STATIC) {
        // EntryHolder_2_InvalidViewFocusOperatorMutator
        case 2162: {
            /**
            * Inserted by Kadabra
            */
            _profileName = view.findViewById(com.beemdevelopment.aegis.R.id.profile_account_name);
            _profileName.requestFocus();
            break;
        }
        // EntryHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3162: {
            /**
            * Inserted by Kadabra
            */
            _profileName = view.findViewById(com.beemdevelopment.aegis.R.id.profile_account_name);
            _profileName.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _profileName = view.findViewById(com.beemdevelopment.aegis.R.id.profile_account_name);
        break;
    }
}
switch(MUID_STATIC) {
    // EntryHolder_4_InvalidViewFocusOperatorMutator
    case 4162: {
        /**
        * Inserted by Kadabra
        */
        _profileCode = view.findViewById(com.beemdevelopment.aegis.R.id.profile_code);
        _profileCode.requestFocus();
        break;
    }
    // EntryHolder_5_ViewComponentNotVisibleOperatorMutator
    case 5162: {
        /**
        * Inserted by Kadabra
        */
        _profileCode = view.findViewById(com.beemdevelopment.aegis.R.id.profile_code);
        _profileCode.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _profileCode = view.findViewById(com.beemdevelopment.aegis.R.id.profile_code);
    break;
}
}
switch(MUID_STATIC) {
// EntryHolder_6_InvalidViewFocusOperatorMutator
case 6162: {
    /**
    * Inserted by Kadabra
    */
    _profileIssuer = view.findViewById(com.beemdevelopment.aegis.R.id.profile_issuer);
    _profileIssuer.requestFocus();
    break;
}
// EntryHolder_7_ViewComponentNotVisibleOperatorMutator
case 7162: {
    /**
    * Inserted by Kadabra
    */
    _profileIssuer = view.findViewById(com.beemdevelopment.aegis.R.id.profile_issuer);
    _profileIssuer.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
_profileIssuer = view.findViewById(com.beemdevelopment.aegis.R.id.profile_issuer);
break;
}
}
switch(MUID_STATIC) {
// EntryHolder_8_InvalidViewFocusOperatorMutator
case 8162: {
/**
* Inserted by Kadabra
*/
_profileCopied = view.findViewById(com.beemdevelopment.aegis.R.id.profile_copied);
_profileCopied.requestFocus();
break;
}
// EntryHolder_9_ViewComponentNotVisibleOperatorMutator
case 9162: {
/**
* Inserted by Kadabra
*/
_profileCopied = view.findViewById(com.beemdevelopment.aegis.R.id.profile_copied);
_profileCopied.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_profileCopied = view.findViewById(com.beemdevelopment.aegis.R.id.profile_copied);
break;
}
}
switch(MUID_STATIC) {
// EntryHolder_10_InvalidViewFocusOperatorMutator
case 10162: {
/**
* Inserted by Kadabra
*/
_description = view.findViewById(com.beemdevelopment.aegis.R.id.description);
_description.requestFocus();
break;
}
// EntryHolder_11_ViewComponentNotVisibleOperatorMutator
case 11162: {
/**
* Inserted by Kadabra
*/
_description = view.findViewById(com.beemdevelopment.aegis.R.id.description);
_description.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_description = view.findViewById(com.beemdevelopment.aegis.R.id.description);
break;
}
}
switch(MUID_STATIC) {
// EntryHolder_12_InvalidViewFocusOperatorMutator
case 12162: {
/**
* Inserted by Kadabra
*/
_profileDrawable = view.findViewById(com.beemdevelopment.aegis.R.id.ivTextDrawable);
_profileDrawable.requestFocus();
break;
}
// EntryHolder_13_ViewComponentNotVisibleOperatorMutator
case 13162: {
/**
* Inserted by Kadabra
*/
_profileDrawable = view.findViewById(com.beemdevelopment.aegis.R.id.ivTextDrawable);
_profileDrawable.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_profileDrawable = view.findViewById(com.beemdevelopment.aegis.R.id.ivTextDrawable);
break;
}
}
switch(MUID_STATIC) {
// EntryHolder_14_InvalidViewFocusOperatorMutator
case 14162: {
/**
* Inserted by Kadabra
*/
_buttonRefresh = view.findViewById(com.beemdevelopment.aegis.R.id.buttonRefresh);
_buttonRefresh.requestFocus();
break;
}
// EntryHolder_15_ViewComponentNotVisibleOperatorMutator
case 15162: {
/**
* Inserted by Kadabra
*/
_buttonRefresh = view.findViewById(com.beemdevelopment.aegis.R.id.buttonRefresh);
_buttonRefresh.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_buttonRefresh = view.findViewById(com.beemdevelopment.aegis.R.id.buttonRefresh);
break;
}
}
switch(MUID_STATIC) {
// EntryHolder_16_InvalidViewFocusOperatorMutator
case 16162: {
/**
* Inserted by Kadabra
*/
_selected = view.findViewById(com.beemdevelopment.aegis.R.id.ivSelected);
_selected.requestFocus();
break;
}
// EntryHolder_17_ViewComponentNotVisibleOperatorMutator
case 17162: {
/**
* Inserted by Kadabra
*/
_selected = view.findViewById(com.beemdevelopment.aegis.R.id.ivSelected);
_selected.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_selected = view.findViewById(com.beemdevelopment.aegis.R.id.ivSelected);
break;
}
}
switch(MUID_STATIC) {
// EntryHolder_18_InvalidViewFocusOperatorMutator
case 18162: {
/**
* Inserted by Kadabra
*/
_dragHandle = view.findViewById(com.beemdevelopment.aegis.R.id.drag_handle);
_dragHandle.requestFocus();
break;
}
// EntryHolder_19_ViewComponentNotVisibleOperatorMutator
case 19162: {
/**
* Inserted by Kadabra
*/
_dragHandle = view.findViewById(com.beemdevelopment.aegis.R.id.drag_handle);
_dragHandle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_dragHandle = view.findViewById(com.beemdevelopment.aegis.R.id.drag_handle);
break;
}
}
switch(MUID_STATIC) {
// EntryHolder_20_InvalidViewFocusOperatorMutator
case 20162: {
/**
* Inserted by Kadabra
*/
_favoriteIndicator = view.findViewById(com.beemdevelopment.aegis.R.id.favorite_indicator);
_favoriteIndicator.requestFocus();
break;
}
// EntryHolder_21_ViewComponentNotVisibleOperatorMutator
case 21162: {
/**
* Inserted by Kadabra
*/
_favoriteIndicator = view.findViewById(com.beemdevelopment.aegis.R.id.favorite_indicator);
_favoriteIndicator.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_favoriteIndicator = view.findViewById(com.beemdevelopment.aegis.R.id.favorite_indicator);
break;
}
}
_selectedHandler = new android.os.Handler();
_animationHandler = new android.os.Handler();
switch(MUID_STATIC) {
// EntryHolder_22_InvalidViewFocusOperatorMutator
case 22162: {
/**
* Inserted by Kadabra
*/
_progressBar = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
_progressBar.requestFocus();
break;
}
// EntryHolder_23_ViewComponentNotVisibleOperatorMutator
case 23162: {
/**
* Inserted by Kadabra
*/
_progressBar = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
_progressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_progressBar = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
break;
}
}
int primaryColorId;
primaryColorId = view.getContext().getResources().getColor(com.beemdevelopment.aegis.R.color.colorPrimary);
_progressBar.getProgressDrawable().setColorFilter(primaryColorId, android.graphics.PorterDuff.Mode.SRC_IN);
_view.setBackground(_view.getContext().getResources().getDrawable(com.beemdevelopment.aegis.R.color.card_background));
_scaleIn = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), com.beemdevelopment.aegis.R.anim.item_scale_in);
_scaleOut = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), com.beemdevelopment.aegis.R.anim.item_scale_out);
_refresher = new com.beemdevelopment.aegis.helpers.UiRefresher(new com.beemdevelopment.aegis.helpers.UiRefresher.Listener() {
@java.lang.Override
public void onRefresh() {
if ((!_hidden) && (!_paused)) {
refreshCode();
}
}


@java.lang.Override
public long getMillisTillNextRefresh() {
return ((com.beemdevelopment.aegis.otp.TotpInfo) (_entry.getInfo())).getMillisTillNextRotation();
}

});
}


public void setData(com.beemdevelopment.aegis.vault.VaultEntry entry, com.beemdevelopment.aegis.Preferences.CodeGrouping groupSize, boolean showAccountName, boolean showIcon, boolean showProgress, boolean hidden, boolean paused, boolean dimmed) {
_entry = entry;
_hidden = hidden;
_paused = paused;
_codeGrouping = groupSize;
_selected.clearAnimation();
_selected.setVisibility(android.view.View.GONE);
_selectedHandler.removeCallbacksAndMessages(null);
_animationHandler.removeCallbacksAndMessages(null);
_favoriteIndicator.setVisibility(_entry.isFavorite() ? android.view.View.VISIBLE : android.view.View.INVISIBLE);
// only show the progress bar if there is no uniform period and the entry type is TotpInfo
setShowProgress(showProgress);
// only show the button if this entry is of type HotpInfo
_buttonRefresh.setVisibility(entry.getInfo() instanceof com.beemdevelopment.aegis.otp.HotpInfo ? android.view.View.VISIBLE : android.view.View.GONE);
java.lang.String profileIssuer;
profileIssuer = entry.getIssuer();
java.lang.String profileName;
profileName = (showAccountName) ? entry.getName() : "";
if ((!profileIssuer.isEmpty()) && (!profileName.isEmpty())) {
profileName = java.lang.String.format(" (%s)", profileName);
}
_profileIssuer.setText(profileIssuer);
_profileName.setText(profileName);
if (_hidden) {
hideCode();
} else if (!_paused) {
refreshCode();
}
showIcon(showIcon);
itemView.setAlpha(dimmed ? com.beemdevelopment.aegis.ui.views.EntryHolder.DIMMED_ALPHA : com.beemdevelopment.aegis.ui.views.EntryHolder.DEFAULT_ALPHA);
}


public com.beemdevelopment.aegis.vault.VaultEntry getEntry() {
return _entry;
}


public void loadIcon(androidx.fragment.app.Fragment fragment) {
if (_entry.hasIcon()) {
com.beemdevelopment.aegis.helpers.IconViewHelper.setLayerType(_profileDrawable, _entry.getIconType());
com.bumptech.glide.Glide.with(fragment).asDrawable().load(_entry).set(com.beemdevelopment.aegis.ui.glide.IconLoader.ICON_TYPE, _entry.getIconType()).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).skipMemoryCache(false).into(_profileDrawable);
} else {
com.amulyakhare.textdrawable.TextDrawable drawable;
drawable = com.beemdevelopment.aegis.helpers.TextDrawableHelper.generate(_entry.getIssuer(), _entry.getName(), _profileDrawable);
_profileDrawable.setImageDrawable(drawable);
}
}


public android.widget.ImageView getIconView() {
return _profileDrawable;
}


public void setOnRefreshClickListener(android.view.View.OnClickListener listener) {
_buttonRefresh.setOnClickListener(listener);
}


public void setShowDragHandle(boolean showDragHandle) {
if (showDragHandle) {
_dragHandle.setVisibility(android.view.View.VISIBLE);
} else {
_dragHandle.setVisibility(android.view.View.INVISIBLE);
}
}


public void setShowProgress(boolean showProgress) {
if (_entry.getInfo() instanceof com.beemdevelopment.aegis.otp.HotpInfo) {
showProgress = false;
}
_progressBar.setVisibility(showProgress ? android.view.View.VISIBLE : android.view.View.GONE);
if (showProgress) {
_progressBar.setPeriod(((com.beemdevelopment.aegis.otp.TotpInfo) (_entry.getInfo())).getPeriod());
startRefreshLoop();
} else {
stopRefreshLoop();
}
}


public void setFocused(boolean focused) {
if (focused) {
_selected.setVisibility(android.view.View.VISIBLE);
_view.setBackgroundColor(com.beemdevelopment.aegis.helpers.ThemeHelper.getThemeColor(com.beemdevelopment.aegis.R.attr.cardBackgroundFocused, _view.getContext().getTheme()));
} else {
_view.setBackgroundColor(com.beemdevelopment.aegis.helpers.ThemeHelper.getThemeColor(com.beemdevelopment.aegis.R.attr.cardBackground, _view.getContext().getTheme()));
}
_view.setSelected(focused);
}


public void setFocusedAndAnimate(boolean focused) {
setFocused(focused);
if (focused) {
_selected.startAnimation(_scaleIn);
} else {
_selected.startAnimation(_scaleOut);
_selectedHandler.postDelayed(() -> _selected.setVisibility(android.view.View.GONE), 150);
}
}


public void destroy() {
_refresher.destroy();
}


public void startRefreshLoop() {
_refresher.start();
_progressBar.start();
}


public void stopRefreshLoop() {
_refresher.stop();
_progressBar.stop();
}


public void refresh() {
_progressBar.restart();
refreshCode();
}


public void refreshCode() {
if ((!_hidden) && (!_paused)) {
updateCode();
}
}


private void updateCode() {
com.beemdevelopment.aegis.otp.OtpInfo info;
info = _entry.getInfo();
// In previous versions of Aegis, it was possible to import entries with an empty
// secret. Attempting to generate OTP's for such entries would result in a crash.
// In case we encounter an old entry that has this issue, we display "ERROR" as
// the OTP, instead of crashing.
java.lang.String otp;
try {
otp = info.getOtp();
if (!((info instanceof com.beemdevelopment.aegis.otp.SteamInfo) || (info instanceof com.beemdevelopment.aegis.otp.YandexInfo))) {
otp = formatCode(otp);
}
} catch (com.beemdevelopment.aegis.otp.OtpInfoException e) {
otp = _view.getResources().getString(com.beemdevelopment.aegis.R.string.error_all_caps);
}
_profileCode.setText(otp);
}


private java.lang.String formatCode(java.lang.String code) {
int groupSize;
java.lang.StringBuilder sb;
sb = new java.lang.StringBuilder();
switch (_codeGrouping) {
case NO_GROUPING :
groupSize = code.length();
break;
case HALVES :
switch(MUID_STATIC) {
// EntryHolder_24_BinaryMutator
case 24162: {
groupSize = (code.length() / 2) - (code.length() % 2);
break;
}
default: {
switch(MUID_STATIC) {
// EntryHolder_25_BinaryMutator
case 25162: {
groupSize = (code.length() * 2) + (code.length() % 2);
break;
}
default: {
groupSize = (code.length() / 2) + (code.length() % 2);
break;
}
}
break;
}
}
break;
default :
groupSize = _codeGrouping.getValue();
if (groupSize <= 0) {
throw new java.lang.IllegalArgumentException("Code group size cannot be zero or negative");
}
}
for (int i = 0; i < code.length(); i++) {
if ((i != 0) && ((i % groupSize) == 0)) {
sb.append(" ");
}
sb.append(code.charAt(i));
}
code = sb.toString();
return code;
}


public void revealCode() {
updateCode();
_hidden = false;
}


public void hideCode() {
java.lang.String hiddenText;
hiddenText = new java.lang.String(new char[_entry.getInfo().getDigits()]).replace("\u0000", java.lang.Character.toString(com.beemdevelopment.aegis.ui.views.EntryHolder.HIDDEN_CHAR));
hiddenText = formatCode(hiddenText);
_profileCode.setText(hiddenText);
_hidden = true;
}


public void showIcon(boolean show) {
if (show) {
_profileDrawable.setVisibility(android.view.View.VISIBLE);
} else {
_profileDrawable.setVisibility(android.view.View.GONE);
}
}


public boolean isHidden() {
return _hidden;
}


public void setPaused(boolean paused) {
_paused = paused;
if ((!_hidden) && (!_paused)) {
updateCode();
}
}


public void dim() {
animateAlphaTo(com.beemdevelopment.aegis.ui.views.EntryHolder.DIMMED_ALPHA);
}


public void highlight() {
animateAlphaTo(com.beemdevelopment.aegis.ui.views.EntryHolder.DEFAULT_ALPHA);
}


public void animateCopyText() {
_animationHandler.removeCallbacksAndMessages(null);
android.view.animation.Animation slideDownFadeIn;
slideDownFadeIn = android.view.animation.AnimationUtils.loadAnimation(itemView.getContext(), com.beemdevelopment.aegis.R.anim.slide_down_fade_in);
android.view.animation.Animation slideDownFadeOut;
slideDownFadeOut = android.view.animation.AnimationUtils.loadAnimation(itemView.getContext(), com.beemdevelopment.aegis.R.anim.slide_down_fade_out);
android.view.animation.Animation fadeOut;
fadeOut = android.view.animation.AnimationUtils.loadAnimation(itemView.getContext(), com.beemdevelopment.aegis.R.anim.fade_out);
android.view.animation.Animation fadeIn;
fadeIn = android.view.animation.AnimationUtils.loadAnimation(itemView.getContext(), com.beemdevelopment.aegis.R.anim.fade_in);
_profileCopied.startAnimation(slideDownFadeIn);
_description.startAnimation(slideDownFadeOut);
_animationHandler.postDelayed(() -> {
_profileCopied.startAnimation(fadeOut);
_description.startAnimation(fadeIn);
}, 3000);
}


private void animateAlphaTo(float alpha) {
itemView.animate().alpha(alpha).setDuration(200).start();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
