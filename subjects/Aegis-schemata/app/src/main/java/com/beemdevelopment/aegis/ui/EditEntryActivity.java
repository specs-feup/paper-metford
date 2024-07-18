package com.beemdevelopment.aegis.ui;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import com.beemdevelopment.aegis.encoding.EncodingException;
import com.amulyakhare.textdrawable.TextDrawable;
import java.util.ArrayList;
import com.beemdevelopment.aegis.helpers.EditTextHelper;
import java.util.Comparator;
import com.beemdevelopment.aegis.icons.IconPack;
import com.beemdevelopment.aegis.otp.OtpInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import com.bumptech.glide.request.target.CustomTarget;
import com.beemdevelopment.aegis.helpers.TextDrawableHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.util.TreeSet;
import android.widget.ImageView;
import java.util.List;
import java.util.UUID;
import androidx.appcompat.app.ActionBar;
import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.stream.Collectors;
import android.view.Menu;
import com.beemdevelopment.aegis.ui.tasks.ImportFileTask;
import com.beemdevelopment.aegis.ui.dialogs.IconPickerDialog;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.beemdevelopment.aegis.icons.IconType;
import com.beemdevelopment.aegis.helpers.DropdownHelper;
import com.beemdevelopment.aegis.util.Cloner;
import java.io.FileInputStream;
import com.beemdevelopment.aegis.helpers.SimpleTextWatcher;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.beemdevelopment.aegis.vault.VaultRepository;
import android.content.res.Resources;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.RelativeLayout;
import android.widget.AdapterView;
import java.io.File;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.encoding.Base32;
import com.beemdevelopment.aegis.otp.MotpInfo;
import com.beemdevelopment.aegis.helpers.IconViewHelper;
import android.text.TextWatcher;
import com.beemdevelopment.aegis.ui.views.IconAdapter;
import com.beemdevelopment.aegis.otp.SteamInfo;
import android.net.Uri;
import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import com.beemdevelopment.aegis.helpers.SimpleAnimationEndListener;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.avito.android.krop.KropView;
import com.beemdevelopment.aegis.otp.HotpInfo;
import androidx.activity.OnBackPressedCallback;
import com.beemdevelopment.aegis.helpers.SafHelper;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.beemdevelopment.aegis.otp.YandexInfo;
import android.widget.LinearLayout;
import android.view.animation.AccelerateInterpolator;
import android.os.Bundle;
import android.view.ViewGroup;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import android.content.Intent;
import android.view.MenuItem;
import java.util.concurrent.atomic.AtomicReference;
import android.view.View;
import com.beemdevelopment.aegis.util.IOUtils;
import android.view.animation.Animation;
import com.bumptech.glide.request.transition.Transition;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import com.beemdevelopment.aegis.encoding.Hex;
import android.widget.AutoCompleteTextView;
import com.beemdevelopment.aegis.ui.glide.IconLoader;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EditEntryActivity extends com.beemdevelopment.aegis.ui.AegisActivity {
    static final int MUID_STATIC = getMUID();
    private static final int PICK_IMAGE_REQUEST = 0;

    private boolean _isNew = false;

    private boolean _isManual = false;

    private com.beemdevelopment.aegis.vault.VaultEntry _origEntry;

    private java.util.TreeSet<java.lang.String> _groups;

    private boolean _hasCustomIcon = false;

    // keep track of icon changes separately as the generated jpeg's are not deterministic
    private boolean _hasChangedIcon = false;

    private com.beemdevelopment.aegis.icons.IconPack.Icon _selectedIcon;

    private de.hdodenhof.circleimageview.CircleImageView _iconView;

    private android.widget.ImageView _saveImageButton;

    private com.google.android.material.textfield.TextInputEditText _textName;

    private com.google.android.material.textfield.TextInputEditText _textIssuer;

    private com.google.android.material.textfield.TextInputEditText _textPeriodCounter;

    private com.google.android.material.textfield.TextInputLayout _textPeriodCounterLayout;

    private com.google.android.material.textfield.TextInputEditText _textDigits;

    private com.google.android.material.textfield.TextInputLayout _textDigitsLayout;

    private com.google.android.material.textfield.TextInputEditText _textSecret;

    private com.google.android.material.textfield.TextInputEditText _textPin;

    private android.widget.LinearLayout _textPinLayout;

    private com.google.android.material.textfield.TextInputEditText _textUsageCount;

    private com.google.android.material.textfield.TextInputEditText _textNote;

    private android.widget.AutoCompleteTextView _dropdownType;

    private android.widget.AutoCompleteTextView _dropdownAlgo;

    private com.google.android.material.textfield.TextInputLayout _dropdownAlgoLayout;

    private android.widget.AutoCompleteTextView _dropdownGroup;

    private java.util.List<java.lang.String> _dropdownGroupList = new java.util.ArrayList<>();

    private com.avito.android.krop.KropView _kropView;

    private android.widget.RelativeLayout _advancedSettingsHeader;

    private android.widget.RelativeLayout _advancedSettings;

    private com.beemdevelopment.aegis.ui.EditEntryActivity.BackPressHandler _backPressHandler;

    private com.beemdevelopment.aegis.ui.EditEntryActivity.IconBackPressHandler _iconBackPressHandler;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // EditEntryActivity_0_LengthyGUICreationOperatorMutator
            case 175: {
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
    if (abortIfOrphan(savedInstanceState)) {
        return;
    }
    setContentView(com.beemdevelopment.aegis.R.layout.activity_edit_entry);
    switch(MUID_STATIC) {
        // EditEntryActivity_1_InvalidIDFindViewOperatorMutator
        case 1175: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
_groups = _vaultManager.getVault().getGroups();
androidx.appcompat.app.ActionBar bar;
bar = getSupportActionBar();
if (bar != null) {
    bar.setHomeAsUpIndicator(com.beemdevelopment.aegis.R.drawable.ic_close);
    bar.setDisplayHomeAsUpEnabled(true);
}
_backPressHandler = new com.beemdevelopment.aegis.ui.EditEntryActivity.BackPressHandler();
getOnBackPressedDispatcher().addCallback(this, _backPressHandler);
_iconBackPressHandler = new com.beemdevelopment.aegis.ui.EditEntryActivity.IconBackPressHandler();
getOnBackPressedDispatcher().addCallback(this, _iconBackPressHandler);
// retrieve info from the calling activity
android.content.Intent intent;
switch(MUID_STATIC) {
    // EditEntryActivity_2_RandomActionIntentDefinitionOperatorMutator
    case 2175: {
        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent = getIntent();
    break;
}
}
java.util.UUID entryUUID;
entryUUID = ((java.util.UUID) (intent.getSerializableExtra("entryUUID")));
if (entryUUID != null) {
_origEntry = _vaultManager.getVault().getEntryByUUID(entryUUID);
} else {
_origEntry = ((com.beemdevelopment.aegis.vault.VaultEntry) (intent.getSerializableExtra("newEntry")));
_isManual = intent.getBooleanExtra("isManual", false);
_isNew = true;
setTitle(com.beemdevelopment.aegis.R.string.add_new_entry);
}
switch(MUID_STATIC) {
// EditEntryActivity_3_FindViewByIdReturnsNullOperatorMutator
case 3175: {
    // set up fields
    _iconView = null;
    break;
}
// EditEntryActivity_4_InvalidIDFindViewOperatorMutator
case 4175: {
    // set up fields
    _iconView = findViewById(732221);
    break;
}
// EditEntryActivity_5_InvalidViewFocusOperatorMutator
case 5175: {
    /**
    * Inserted by Kadabra
    */
    // set up fields
    _iconView = findViewById(com.beemdevelopment.aegis.R.id.profile_drawable);
    _iconView.requestFocus();
    break;
}
// EditEntryActivity_6_ViewComponentNotVisibleOperatorMutator
case 6175: {
    /**
    * Inserted by Kadabra
    */
    // set up fields
    _iconView = findViewById(com.beemdevelopment.aegis.R.id.profile_drawable);
    _iconView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
// set up fields
_iconView = findViewById(com.beemdevelopment.aegis.R.id.profile_drawable);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_7_FindViewByIdReturnsNullOperatorMutator
case 7175: {
_kropView = null;
break;
}
// EditEntryActivity_8_InvalidIDFindViewOperatorMutator
case 8175: {
_kropView = findViewById(732221);
break;
}
// EditEntryActivity_9_InvalidViewFocusOperatorMutator
case 9175: {
/**
* Inserted by Kadabra
*/
_kropView = findViewById(com.beemdevelopment.aegis.R.id.krop_view);
_kropView.requestFocus();
break;
}
// EditEntryActivity_10_ViewComponentNotVisibleOperatorMutator
case 10175: {
/**
* Inserted by Kadabra
*/
_kropView = findViewById(com.beemdevelopment.aegis.R.id.krop_view);
_kropView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_kropView = findViewById(com.beemdevelopment.aegis.R.id.krop_view);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_11_FindViewByIdReturnsNullOperatorMutator
case 11175: {
_saveImageButton = null;
break;
}
// EditEntryActivity_12_InvalidIDFindViewOperatorMutator
case 12175: {
_saveImageButton = findViewById(732221);
break;
}
// EditEntryActivity_13_InvalidViewFocusOperatorMutator
case 13175: {
/**
* Inserted by Kadabra
*/
_saveImageButton = findViewById(com.beemdevelopment.aegis.R.id.iv_saveImage);
_saveImageButton.requestFocus();
break;
}
// EditEntryActivity_14_ViewComponentNotVisibleOperatorMutator
case 14175: {
/**
* Inserted by Kadabra
*/
_saveImageButton = findViewById(com.beemdevelopment.aegis.R.id.iv_saveImage);
_saveImageButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_saveImageButton = findViewById(com.beemdevelopment.aegis.R.id.iv_saveImage);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_15_FindViewByIdReturnsNullOperatorMutator
case 15175: {
_textName = null;
break;
}
// EditEntryActivity_16_InvalidIDFindViewOperatorMutator
case 16175: {
_textName = findViewById(732221);
break;
}
// EditEntryActivity_17_InvalidViewFocusOperatorMutator
case 17175: {
/**
* Inserted by Kadabra
*/
_textName = findViewById(com.beemdevelopment.aegis.R.id.text_name);
_textName.requestFocus();
break;
}
// EditEntryActivity_18_ViewComponentNotVisibleOperatorMutator
case 18175: {
/**
* Inserted by Kadabra
*/
_textName = findViewById(com.beemdevelopment.aegis.R.id.text_name);
_textName.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textName = findViewById(com.beemdevelopment.aegis.R.id.text_name);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_19_FindViewByIdReturnsNullOperatorMutator
case 19175: {
_textIssuer = null;
break;
}
// EditEntryActivity_20_InvalidIDFindViewOperatorMutator
case 20175: {
_textIssuer = findViewById(732221);
break;
}
// EditEntryActivity_21_InvalidViewFocusOperatorMutator
case 21175: {
/**
* Inserted by Kadabra
*/
_textIssuer = findViewById(com.beemdevelopment.aegis.R.id.text_issuer);
_textIssuer.requestFocus();
break;
}
// EditEntryActivity_22_ViewComponentNotVisibleOperatorMutator
case 22175: {
/**
* Inserted by Kadabra
*/
_textIssuer = findViewById(com.beemdevelopment.aegis.R.id.text_issuer);
_textIssuer.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textIssuer = findViewById(com.beemdevelopment.aegis.R.id.text_issuer);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_23_FindViewByIdReturnsNullOperatorMutator
case 23175: {
_textPeriodCounter = null;
break;
}
// EditEntryActivity_24_InvalidIDFindViewOperatorMutator
case 24175: {
_textPeriodCounter = findViewById(732221);
break;
}
// EditEntryActivity_25_InvalidViewFocusOperatorMutator
case 25175: {
/**
* Inserted by Kadabra
*/
_textPeriodCounter = findViewById(com.beemdevelopment.aegis.R.id.text_period_counter);
_textPeriodCounter.requestFocus();
break;
}
// EditEntryActivity_26_ViewComponentNotVisibleOperatorMutator
case 26175: {
/**
* Inserted by Kadabra
*/
_textPeriodCounter = findViewById(com.beemdevelopment.aegis.R.id.text_period_counter);
_textPeriodCounter.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textPeriodCounter = findViewById(com.beemdevelopment.aegis.R.id.text_period_counter);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_27_FindViewByIdReturnsNullOperatorMutator
case 27175: {
_textPeriodCounterLayout = null;
break;
}
// EditEntryActivity_28_InvalidIDFindViewOperatorMutator
case 28175: {
_textPeriodCounterLayout = findViewById(732221);
break;
}
// EditEntryActivity_29_InvalidViewFocusOperatorMutator
case 29175: {
/**
* Inserted by Kadabra
*/
_textPeriodCounterLayout = findViewById(com.beemdevelopment.aegis.R.id.text_period_counter_layout);
_textPeriodCounterLayout.requestFocus();
break;
}
// EditEntryActivity_30_ViewComponentNotVisibleOperatorMutator
case 30175: {
/**
* Inserted by Kadabra
*/
_textPeriodCounterLayout = findViewById(com.beemdevelopment.aegis.R.id.text_period_counter_layout);
_textPeriodCounterLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textPeriodCounterLayout = findViewById(com.beemdevelopment.aegis.R.id.text_period_counter_layout);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_31_FindViewByIdReturnsNullOperatorMutator
case 31175: {
_textDigits = null;
break;
}
// EditEntryActivity_32_InvalidIDFindViewOperatorMutator
case 32175: {
_textDigits = findViewById(732221);
break;
}
// EditEntryActivity_33_InvalidViewFocusOperatorMutator
case 33175: {
/**
* Inserted by Kadabra
*/
_textDigits = findViewById(com.beemdevelopment.aegis.R.id.text_digits);
_textDigits.requestFocus();
break;
}
// EditEntryActivity_34_ViewComponentNotVisibleOperatorMutator
case 34175: {
/**
* Inserted by Kadabra
*/
_textDigits = findViewById(com.beemdevelopment.aegis.R.id.text_digits);
_textDigits.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textDigits = findViewById(com.beemdevelopment.aegis.R.id.text_digits);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_35_FindViewByIdReturnsNullOperatorMutator
case 35175: {
_textDigitsLayout = null;
break;
}
// EditEntryActivity_36_InvalidIDFindViewOperatorMutator
case 36175: {
_textDigitsLayout = findViewById(732221);
break;
}
// EditEntryActivity_37_InvalidViewFocusOperatorMutator
case 37175: {
/**
* Inserted by Kadabra
*/
_textDigitsLayout = findViewById(com.beemdevelopment.aegis.R.id.text_digits_layout);
_textDigitsLayout.requestFocus();
break;
}
// EditEntryActivity_38_ViewComponentNotVisibleOperatorMutator
case 38175: {
/**
* Inserted by Kadabra
*/
_textDigitsLayout = findViewById(com.beemdevelopment.aegis.R.id.text_digits_layout);
_textDigitsLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textDigitsLayout = findViewById(com.beemdevelopment.aegis.R.id.text_digits_layout);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_39_FindViewByIdReturnsNullOperatorMutator
case 39175: {
_textSecret = null;
break;
}
// EditEntryActivity_40_InvalidIDFindViewOperatorMutator
case 40175: {
_textSecret = findViewById(732221);
break;
}
// EditEntryActivity_41_InvalidViewFocusOperatorMutator
case 41175: {
/**
* Inserted by Kadabra
*/
_textSecret = findViewById(com.beemdevelopment.aegis.R.id.text_secret);
_textSecret.requestFocus();
break;
}
// EditEntryActivity_42_ViewComponentNotVisibleOperatorMutator
case 42175: {
/**
* Inserted by Kadabra
*/
_textSecret = findViewById(com.beemdevelopment.aegis.R.id.text_secret);
_textSecret.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textSecret = findViewById(com.beemdevelopment.aegis.R.id.text_secret);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_43_FindViewByIdReturnsNullOperatorMutator
case 43175: {
_textPin = null;
break;
}
// EditEntryActivity_44_InvalidIDFindViewOperatorMutator
case 44175: {
_textPin = findViewById(732221);
break;
}
// EditEntryActivity_45_InvalidViewFocusOperatorMutator
case 45175: {
/**
* Inserted by Kadabra
*/
_textPin = findViewById(com.beemdevelopment.aegis.R.id.text_pin);
_textPin.requestFocus();
break;
}
// EditEntryActivity_46_ViewComponentNotVisibleOperatorMutator
case 46175: {
/**
* Inserted by Kadabra
*/
_textPin = findViewById(com.beemdevelopment.aegis.R.id.text_pin);
_textPin.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textPin = findViewById(com.beemdevelopment.aegis.R.id.text_pin);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_47_FindViewByIdReturnsNullOperatorMutator
case 47175: {
_textPinLayout = null;
break;
}
// EditEntryActivity_48_InvalidIDFindViewOperatorMutator
case 48175: {
_textPinLayout = findViewById(732221);
break;
}
// EditEntryActivity_49_InvalidViewFocusOperatorMutator
case 49175: {
/**
* Inserted by Kadabra
*/
_textPinLayout = findViewById(com.beemdevelopment.aegis.R.id.layout_pin);
_textPinLayout.requestFocus();
break;
}
// EditEntryActivity_50_ViewComponentNotVisibleOperatorMutator
case 50175: {
/**
* Inserted by Kadabra
*/
_textPinLayout = findViewById(com.beemdevelopment.aegis.R.id.layout_pin);
_textPinLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textPinLayout = findViewById(com.beemdevelopment.aegis.R.id.layout_pin);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_51_FindViewByIdReturnsNullOperatorMutator
case 51175: {
_textUsageCount = null;
break;
}
// EditEntryActivity_52_InvalidIDFindViewOperatorMutator
case 52175: {
_textUsageCount = findViewById(732221);
break;
}
// EditEntryActivity_53_InvalidViewFocusOperatorMutator
case 53175: {
/**
* Inserted by Kadabra
*/
_textUsageCount = findViewById(com.beemdevelopment.aegis.R.id.text_usage_count);
_textUsageCount.requestFocus();
break;
}
// EditEntryActivity_54_ViewComponentNotVisibleOperatorMutator
case 54175: {
/**
* Inserted by Kadabra
*/
_textUsageCount = findViewById(com.beemdevelopment.aegis.R.id.text_usage_count);
_textUsageCount.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textUsageCount = findViewById(com.beemdevelopment.aegis.R.id.text_usage_count);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_55_FindViewByIdReturnsNullOperatorMutator
case 55175: {
_textNote = null;
break;
}
// EditEntryActivity_56_InvalidIDFindViewOperatorMutator
case 56175: {
_textNote = findViewById(732221);
break;
}
// EditEntryActivity_57_InvalidViewFocusOperatorMutator
case 57175: {
/**
* Inserted by Kadabra
*/
_textNote = findViewById(com.beemdevelopment.aegis.R.id.text_note);
_textNote.requestFocus();
break;
}
// EditEntryActivity_58_ViewComponentNotVisibleOperatorMutator
case 58175: {
/**
* Inserted by Kadabra
*/
_textNote = findViewById(com.beemdevelopment.aegis.R.id.text_note);
_textNote.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textNote = findViewById(com.beemdevelopment.aegis.R.id.text_note);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_59_FindViewByIdReturnsNullOperatorMutator
case 59175: {
_dropdownType = null;
break;
}
// EditEntryActivity_60_InvalidIDFindViewOperatorMutator
case 60175: {
_dropdownType = findViewById(732221);
break;
}
// EditEntryActivity_61_InvalidViewFocusOperatorMutator
case 61175: {
/**
* Inserted by Kadabra
*/
_dropdownType = findViewById(com.beemdevelopment.aegis.R.id.dropdown_type);
_dropdownType.requestFocus();
break;
}
// EditEntryActivity_62_ViewComponentNotVisibleOperatorMutator
case 62175: {
/**
* Inserted by Kadabra
*/
_dropdownType = findViewById(com.beemdevelopment.aegis.R.id.dropdown_type);
_dropdownType.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_dropdownType = findViewById(com.beemdevelopment.aegis.R.id.dropdown_type);
break;
}
}
com.beemdevelopment.aegis.helpers.DropdownHelper.fillDropdown(this, _dropdownType, com.beemdevelopment.aegis.R.array.otp_types_array);
switch(MUID_STATIC) {
// EditEntryActivity_63_FindViewByIdReturnsNullOperatorMutator
case 63175: {
_dropdownAlgoLayout = null;
break;
}
// EditEntryActivity_64_InvalidIDFindViewOperatorMutator
case 64175: {
_dropdownAlgoLayout = findViewById(732221);
break;
}
// EditEntryActivity_65_InvalidViewFocusOperatorMutator
case 65175: {
/**
* Inserted by Kadabra
*/
_dropdownAlgoLayout = findViewById(com.beemdevelopment.aegis.R.id.dropdown_algo_layout);
_dropdownAlgoLayout.requestFocus();
break;
}
// EditEntryActivity_66_ViewComponentNotVisibleOperatorMutator
case 66175: {
/**
* Inserted by Kadabra
*/
_dropdownAlgoLayout = findViewById(com.beemdevelopment.aegis.R.id.dropdown_algo_layout);
_dropdownAlgoLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_dropdownAlgoLayout = findViewById(com.beemdevelopment.aegis.R.id.dropdown_algo_layout);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_67_FindViewByIdReturnsNullOperatorMutator
case 67175: {
_dropdownAlgo = null;
break;
}
// EditEntryActivity_68_InvalidIDFindViewOperatorMutator
case 68175: {
_dropdownAlgo = findViewById(732221);
break;
}
// EditEntryActivity_69_InvalidViewFocusOperatorMutator
case 69175: {
/**
* Inserted by Kadabra
*/
_dropdownAlgo = findViewById(com.beemdevelopment.aegis.R.id.dropdown_algo);
_dropdownAlgo.requestFocus();
break;
}
// EditEntryActivity_70_ViewComponentNotVisibleOperatorMutator
case 70175: {
/**
* Inserted by Kadabra
*/
_dropdownAlgo = findViewById(com.beemdevelopment.aegis.R.id.dropdown_algo);
_dropdownAlgo.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_dropdownAlgo = findViewById(com.beemdevelopment.aegis.R.id.dropdown_algo);
break;
}
}
com.beemdevelopment.aegis.helpers.DropdownHelper.fillDropdown(this, _dropdownAlgo, com.beemdevelopment.aegis.R.array.otp_algo_array);
switch(MUID_STATIC) {
// EditEntryActivity_71_FindViewByIdReturnsNullOperatorMutator
case 71175: {
_dropdownGroup = null;
break;
}
// EditEntryActivity_72_InvalidIDFindViewOperatorMutator
case 72175: {
_dropdownGroup = findViewById(732221);
break;
}
// EditEntryActivity_73_InvalidViewFocusOperatorMutator
case 73175: {
/**
* Inserted by Kadabra
*/
_dropdownGroup = findViewById(com.beemdevelopment.aegis.R.id.dropdown_group);
_dropdownGroup.requestFocus();
break;
}
// EditEntryActivity_74_ViewComponentNotVisibleOperatorMutator
case 74175: {
/**
* Inserted by Kadabra
*/
_dropdownGroup = findViewById(com.beemdevelopment.aegis.R.id.dropdown_group);
_dropdownGroup.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_dropdownGroup = findViewById(com.beemdevelopment.aegis.R.id.dropdown_group);
break;
}
}
updateGroupDropdownList();
com.beemdevelopment.aegis.helpers.DropdownHelper.fillDropdown(this, _dropdownGroup, _dropdownGroupList);
// if this is NOT a manually entered entry, move the "Secret" field from basic to advanced settings
if ((!_isNew) || (!_isManual)) {
int secretIndex;
secretIndex = 0;
android.widget.LinearLayout layoutSecret;
switch(MUID_STATIC) {
// EditEntryActivity_75_FindViewByIdReturnsNullOperatorMutator
case 75175: {
layoutSecret = null;
break;
}
// EditEntryActivity_76_InvalidIDFindViewOperatorMutator
case 76175: {
layoutSecret = findViewById(732221);
break;
}
// EditEntryActivity_77_InvalidViewFocusOperatorMutator
case 77175: {
/**
* Inserted by Kadabra
*/
layoutSecret = findViewById(com.beemdevelopment.aegis.R.id.layout_secret);
layoutSecret.requestFocus();
break;
}
// EditEntryActivity_78_ViewComponentNotVisibleOperatorMutator
case 78175: {
/**
* Inserted by Kadabra
*/
layoutSecret = findViewById(com.beemdevelopment.aegis.R.id.layout_secret);
layoutSecret.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
layoutSecret = findViewById(com.beemdevelopment.aegis.R.id.layout_secret);
break;
}
}
android.widget.LinearLayout layoutBasic;
switch(MUID_STATIC) {
// EditEntryActivity_79_FindViewByIdReturnsNullOperatorMutator
case 79175: {
layoutBasic = null;
break;
}
// EditEntryActivity_80_InvalidIDFindViewOperatorMutator
case 80175: {
layoutBasic = findViewById(732221);
break;
}
// EditEntryActivity_81_InvalidViewFocusOperatorMutator
case 81175: {
/**
* Inserted by Kadabra
*/
layoutBasic = findViewById(com.beemdevelopment.aegis.R.id.layout_basic);
layoutBasic.requestFocus();
break;
}
// EditEntryActivity_82_ViewComponentNotVisibleOperatorMutator
case 82175: {
/**
* Inserted by Kadabra
*/
layoutBasic = findViewById(com.beemdevelopment.aegis.R.id.layout_basic);
layoutBasic.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
layoutBasic = findViewById(com.beemdevelopment.aegis.R.id.layout_basic);
break;
}
}
android.widget.LinearLayout layoutAdvanced;
switch(MUID_STATIC) {
// EditEntryActivity_83_FindViewByIdReturnsNullOperatorMutator
case 83175: {
layoutAdvanced = null;
break;
}
// EditEntryActivity_84_InvalidIDFindViewOperatorMutator
case 84175: {
layoutAdvanced = findViewById(732221);
break;
}
// EditEntryActivity_85_InvalidViewFocusOperatorMutator
case 85175: {
/**
* Inserted by Kadabra
*/
layoutAdvanced = findViewById(com.beemdevelopment.aegis.R.id.layout_advanced);
layoutAdvanced.requestFocus();
break;
}
// EditEntryActivity_86_ViewComponentNotVisibleOperatorMutator
case 86175: {
/**
* Inserted by Kadabra
*/
layoutAdvanced = findViewById(com.beemdevelopment.aegis.R.id.layout_advanced);
layoutAdvanced.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
layoutAdvanced = findViewById(com.beemdevelopment.aegis.R.id.layout_advanced);
break;
}
}
layoutBasic.removeView(layoutSecret);
if (!_isNew) {
secretIndex = 1;
layoutBasic.removeView(_textPinLayout);
layoutAdvanced.addView(_textPinLayout, 0);
((android.widget.LinearLayout.LayoutParams) (_textPinLayout.getLayoutParams())).topMargin = 0;
} else {
((android.widget.LinearLayout.LayoutParams) (layoutSecret.getLayoutParams())).topMargin = 0;
}
layoutAdvanced.addView(layoutSecret, secretIndex);
if (_isNew && (!_isManual)) {
com.beemdevelopment.aegis.ui.EditEntryActivity.setViewEnabled(layoutAdvanced, false);
}
} else {
android.widget.LinearLayout layoutTypeAlgo;
switch(MUID_STATIC) {
// EditEntryActivity_87_FindViewByIdReturnsNullOperatorMutator
case 87175: {
layoutTypeAlgo = null;
break;
}
// EditEntryActivity_88_InvalidIDFindViewOperatorMutator
case 88175: {
layoutTypeAlgo = findViewById(732221);
break;
}
// EditEntryActivity_89_InvalidViewFocusOperatorMutator
case 89175: {
/**
* Inserted by Kadabra
*/
layoutTypeAlgo = findViewById(com.beemdevelopment.aegis.R.id.layout_type_algo);
layoutTypeAlgo.requestFocus();
break;
}
// EditEntryActivity_90_ViewComponentNotVisibleOperatorMutator
case 90175: {
/**
* Inserted by Kadabra
*/
layoutTypeAlgo = findViewById(com.beemdevelopment.aegis.R.id.layout_type_algo);
layoutTypeAlgo.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
layoutTypeAlgo = findViewById(com.beemdevelopment.aegis.R.id.layout_type_algo);
break;
}
}
((android.widget.LinearLayout.LayoutParams) (layoutTypeAlgo.getLayoutParams())).topMargin = 0;
}
switch(MUID_STATIC) {
// EditEntryActivity_91_FindViewByIdReturnsNullOperatorMutator
case 91175: {
_advancedSettingsHeader = null;
break;
}
// EditEntryActivity_92_InvalidIDFindViewOperatorMutator
case 92175: {
_advancedSettingsHeader = findViewById(732221);
break;
}
// EditEntryActivity_93_InvalidViewFocusOperatorMutator
case 93175: {
/**
* Inserted by Kadabra
*/
_advancedSettingsHeader = findViewById(com.beemdevelopment.aegis.R.id.accordian_header);
_advancedSettingsHeader.requestFocus();
break;
}
// EditEntryActivity_94_ViewComponentNotVisibleOperatorMutator
case 94175: {
/**
* Inserted by Kadabra
*/
_advancedSettingsHeader = findViewById(com.beemdevelopment.aegis.R.id.accordian_header);
_advancedSettingsHeader.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_advancedSettingsHeader = findViewById(com.beemdevelopment.aegis.R.id.accordian_header);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_95_BuggyGUIListenerOperatorMutator
case 95175: {
_advancedSettingsHeader.setOnClickListener(null);
break;
}
default: {
_advancedSettingsHeader.setOnClickListener((android.view.View v) -> openAdvancedSettings());
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_96_FindViewByIdReturnsNullOperatorMutator
case 96175: {
_advancedSettings = null;
break;
}
// EditEntryActivity_97_InvalidIDFindViewOperatorMutator
case 97175: {
_advancedSettings = findViewById(732221);
break;
}
// EditEntryActivity_98_InvalidViewFocusOperatorMutator
case 98175: {
/**
* Inserted by Kadabra
*/
_advancedSettings = findViewById(com.beemdevelopment.aegis.R.id.expandableLayout);
_advancedSettings.requestFocus();
break;
}
// EditEntryActivity_99_ViewComponentNotVisibleOperatorMutator
case 99175: {
/**
* Inserted by Kadabra
*/
_advancedSettings = findViewById(com.beemdevelopment.aegis.R.id.expandableLayout);
_advancedSettings.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_advancedSettings = findViewById(com.beemdevelopment.aegis.R.id.expandableLayout);
break;
}
}
// fill the fields with values if possible
if (_origEntry.hasIcon()) {
com.beemdevelopment.aegis.helpers.IconViewHelper.setLayerType(_iconView, _origEntry.getIconType());
com.bumptech.glide.Glide.with(this).asDrawable().load(_origEntry).set(com.beemdevelopment.aegis.ui.glide.IconLoader.ICON_TYPE, _origEntry.getIconType()).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).skipMemoryCache(false).into(_iconView);
_hasCustomIcon = true;
} else {
com.amulyakhare.textdrawable.TextDrawable drawable;
drawable = com.beemdevelopment.aegis.helpers.TextDrawableHelper.generate(_origEntry.getIssuer(), _origEntry.getName(), _iconView);
_iconView.setImageDrawable(drawable);
}
_textName.setText(_origEntry.getName());
_textIssuer.setText(_origEntry.getIssuer());
_textNote.setText(_origEntry.getNote());
com.beemdevelopment.aegis.otp.OtpInfo info;
info = _origEntry.getInfo();
if (info instanceof com.beemdevelopment.aegis.otp.TotpInfo) {
_textPeriodCounterLayout.setHint(com.beemdevelopment.aegis.R.string.period_hint);
_textPeriodCounter.setText(java.lang.Integer.toString(((com.beemdevelopment.aegis.otp.TotpInfo) (info)).getPeriod()));
} else if (info instanceof com.beemdevelopment.aegis.otp.HotpInfo) {
_textPeriodCounterLayout.setHint(com.beemdevelopment.aegis.R.string.counter);
_textPeriodCounter.setText(java.lang.Long.toString(((com.beemdevelopment.aegis.otp.HotpInfo) (info)).getCounter()));
} else {
throw new java.lang.RuntimeException(java.lang.String.format("Unsupported OtpInfo type: %s", info.getClass()));
}
_textDigits.setText(java.lang.Integer.toString(info.getDigits()));
byte[] secretBytes;
secretBytes = _origEntry.getInfo().getSecret();
if (secretBytes != null) {
java.lang.String secretString;
secretString = (info instanceof com.beemdevelopment.aegis.otp.MotpInfo) ? com.beemdevelopment.aegis.encoding.Hex.encode(secretBytes) : com.beemdevelopment.aegis.encoding.Base32.encode(secretBytes);
_textSecret.setText(secretString);
}
_dropdownType.setText(_origEntry.getInfo().getType(), false);
_dropdownAlgo.setText(_origEntry.getInfo().getAlgorithm(false), false);
if (info instanceof com.beemdevelopment.aegis.otp.YandexInfo) {
_textPin.setText(((com.beemdevelopment.aegis.otp.YandexInfo) (info)).getPin());
} else if (info instanceof com.beemdevelopment.aegis.otp.MotpInfo) {
_textPin.setText(((com.beemdevelopment.aegis.otp.MotpInfo) (info)).getPin());
}
updateAdvancedFieldStatus(_origEntry.getInfo().getTypeId());
updatePinFieldVisibility(_origEntry.getInfo().getTypeId());
java.lang.String group;
group = _origEntry.getGroup();
setGroup(group);
// Update the icon if the issuer or name has changed
_textIssuer.addTextChangedListener(_nameChangeListener);
_textName.addTextChangedListener(_nameChangeListener);
// Register listeners to trigger validation
_textIssuer.addTextChangedListener(_validationListener);
_textName.addTextChangedListener(_validationListener);
_textNote.addTextChangedListener(_validationListener);
_textSecret.addTextChangedListener(_validationListener);
_dropdownType.addTextChangedListener(_validationListener);
_dropdownGroup.addTextChangedListener(_validationListener);
_dropdownAlgo.addTextChangedListener(_validationListener);
_textPeriodCounter.addTextChangedListener(_validationListener);
_textDigits.addTextChangedListener(_validationListener);
_textPin.addTextChangedListener(_validationListener);
// show/hide period and counter fields on type change
_dropdownType.setOnItemClickListener((android.widget.AdapterView<?> parent,android.view.View view,int position,long id) -> {
java.lang.String type;
type = _dropdownType.getText().toString().toLowerCase(java.util.Locale.ROOT);
switch (type) {
case com.beemdevelopment.aegis.otp.SteamInfo.ID :
_dropdownAlgo.setText(com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, false);
_textPeriodCounterLayout.setHint(com.beemdevelopment.aegis.R.string.period_hint);
_textPeriodCounter.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD));
_textDigits.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.SteamInfo.DIGITS));
break;
case com.beemdevelopment.aegis.otp.TotpInfo.ID :
_dropdownAlgo.setText(com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, false);
_textPeriodCounterLayout.setHint(com.beemdevelopment.aegis.R.string.period_hint);
_textPeriodCounter.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD));
_textDigits.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_DIGITS));
break;
case com.beemdevelopment.aegis.otp.HotpInfo.ID :
_dropdownAlgo.setText(com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, false);
_textPeriodCounterLayout.setHint(com.beemdevelopment.aegis.R.string.counter);
_textPeriodCounter.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.HotpInfo.DEFAULT_COUNTER));
_textDigits.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_DIGITS));
break;
case com.beemdevelopment.aegis.otp.YandexInfo.ID :
_dropdownAlgo.setText(com.beemdevelopment.aegis.otp.YandexInfo.DEFAULT_ALGORITHM, false);
_textPeriodCounterLayout.setHint(com.beemdevelopment.aegis.R.string.period_hint);
_textPeriodCounter.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD));
_textDigits.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.YandexInfo.DIGITS));
break;
case com.beemdevelopment.aegis.otp.MotpInfo.ID :
_dropdownAlgo.setText(com.beemdevelopment.aegis.otp.MotpInfo.ALGORITHM, false);
_textPeriodCounterLayout.setHint(com.beemdevelopment.aegis.R.string.period_hint);
_textPeriodCounter.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.MotpInfo.PERIOD));
_textDigits.setText(java.lang.String.valueOf(com.beemdevelopment.aegis.otp.MotpInfo.DIGITS));
break;
default :
throw new java.lang.RuntimeException(java.lang.String.format("Unsupported OTP type: %s", type));
}
updateAdvancedFieldStatus(type);
updatePinFieldVisibility(type);
});
switch(MUID_STATIC) {
// EditEntryActivity_100_BuggyGUIListenerOperatorMutator
case 100175: {
_iconView.setOnClickListener(null);
break;
}
default: {
_iconView.setOnClickListener((android.view.View v) -> {
startIconSelection();
});
break;
}
}
_dropdownGroup.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
private int prevPosition = _dropdownGroupList.indexOf(_dropdownGroup.getText().toString());

@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
switch(MUID_STATIC) {
// EditEntryActivity_101_BinaryMutator
case 101175: {
if (position == (_dropdownGroupList.size() + 1)) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(com.beemdevelopment.aegis.ui.EditEntryActivity.this, com.beemdevelopment.aegis.R.string.set_group, com.beemdevelopment.aegis.R.string.group_name_hint, ( text) -> {
java.lang.String groupName;
groupName = new java.lang.String(text);
if (!groupName.isEmpty()) {
_groups.add(groupName);
updateGroupDropdownList();
_dropdownGroup.setText(groupName, false);
}
});
_dropdownGroup.setText(_dropdownGroupList.get(prevPosition), false);
} else {
prevPosition = position;
}
break;
}
default: {
if (position == (_dropdownGroupList.size() - 1)) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(com.beemdevelopment.aegis.ui.EditEntryActivity.this, com.beemdevelopment.aegis.R.string.set_group, com.beemdevelopment.aegis.R.string.group_name_hint, ( text) -> {
java.lang.String groupName;
groupName = new java.lang.String(text);
if (!groupName.isEmpty()) {
_groups.add(groupName);
updateGroupDropdownList();
_dropdownGroup.setText(groupName, false);
}
});
_dropdownGroup.setText(_dropdownGroupList.get(prevPosition), false);
} else {
prevPosition = position;
}
break;
}
}
}

});
_textUsageCount.setText(_prefs.getUsageCount(entryUUID).toString());
}


private void updateAdvancedFieldStatus(java.lang.String otpType) {
boolean enabled;
enabled = (((!otpType.equals(com.beemdevelopment.aegis.otp.SteamInfo.ID)) && (!otpType.equals(com.beemdevelopment.aegis.otp.YandexInfo.ID))) && (!otpType.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID))) && ((!_isNew) || _isManual);
_textDigitsLayout.setEnabled(enabled);
_textPeriodCounterLayout.setEnabled(enabled);
_dropdownAlgoLayout.setEnabled(enabled);
}


private void updatePinFieldVisibility(java.lang.String otpType) {
boolean visible;
visible = otpType.equals(com.beemdevelopment.aegis.otp.YandexInfo.ID) || otpType.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID);
_textPinLayout.setVisibility(visible ? android.view.View.VISIBLE : android.view.View.GONE);
_textPin.setHint(otpType.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID) ? com.beemdevelopment.aegis.R.string.motp_pin : com.beemdevelopment.aegis.R.string.yandex_pin);
}


private void setGroup(java.lang.String groupName) {
int pos;
pos = 0;
if (groupName != null) {
switch(MUID_STATIC) {
// EditEntryActivity_102_BinaryMutator
case 102175: {
pos = (_groups.contains(groupName)) ? _groups.headSet(groupName).size() - 1 : 0;
break;
}
default: {
pos = (_groups.contains(groupName)) ? _groups.headSet(groupName).size() + 1 : 0;
break;
}
}
}
_dropdownGroup.setText(_dropdownGroupList.get(pos), false);
}


private void openAdvancedSettings() {
android.view.animation.Animation fadeOut;
fadeOut = new android.view.animation.AlphaAnimation(1, 0);
fadeOut.setInterpolator(new android.view.animation.AccelerateInterpolator());
fadeOut.setDuration(220);
_advancedSettingsHeader.startAnimation(fadeOut);
android.view.animation.Animation fadeIn;
fadeIn = new android.view.animation.AlphaAnimation(0, 1);
fadeIn.setInterpolator(new android.view.animation.AccelerateInterpolator());
fadeIn.setDuration(250);
fadeOut.setAnimationListener(new com.beemdevelopment.aegis.helpers.SimpleAnimationEndListener((android.view.animation.Animation a) -> {
_advancedSettingsHeader.setVisibility(android.view.View.GONE);
_advancedSettings.startAnimation(fadeIn);
}));
fadeIn.setAnimationListener(new com.beemdevelopment.aegis.helpers.SimpleAnimationEndListener((android.view.animation.Animation a) -> {
_advancedSettings.setVisibility(android.view.View.VISIBLE);
}));
}


private void updateGroupDropdownList() {
android.content.res.Resources res;
res = getResources();
_dropdownGroupList.clear();
_dropdownGroupList.add(res.getString(com.beemdevelopment.aegis.R.string.no_group));
_dropdownGroupList.addAll(_groups);
_dropdownGroupList.add(res.getString(com.beemdevelopment.aegis.R.string.new_group));
}


private boolean hasUnsavedChanges(com.beemdevelopment.aegis.vault.VaultEntry newEntry) {
return _hasChangedIcon || (!_origEntry.equals(newEntry));
}


private void discardAndFinish() {
java.util.concurrent.atomic.AtomicReference<java.lang.String> msg;
msg = new java.util.concurrent.atomic.AtomicReference<>();
java.util.concurrent.atomic.AtomicReference<com.beemdevelopment.aegis.vault.VaultEntry> entry;
entry = new java.util.concurrent.atomic.AtomicReference<>();
try {
entry.set(parseEntry());
} catch (com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException e) {
msg.set(e.getMessage());
}
if (!hasUnsavedChanges(entry.get())) {
finish();
return;
}
switch(MUID_STATIC) {
// EditEntryActivity_103_BuggyGUIListenerOperatorMutator
case 103175: {
// ask for confirmation if the entry has been changed
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDiscardDialog(this, null, (android.content.DialogInterface dialog,int which) -> finish());
break;
}
default: {
switch(MUID_STATIC) {
// EditEntryActivity_104_BuggyGUIListenerOperatorMutator
case 104175: {
// ask for confirmation if the entry has been changed
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDiscardDialog(this, (android.content.DialogInterface dialog,int which) -> {
// if the entry couldn't be parsed, we show an error dialog
if (msg.get() != null) {
onSaveError(msg.get());
return;
}
addAndFinish(entry.get());
}, null);
break;
}
default: {
// ask for confirmation if the entry has been changed
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDiscardDialog(this, (android.content.DialogInterface dialog,int which) -> {
// if the entry couldn't be parsed, we show an error dialog
if (msg.get() != null) {
onSaveError(msg.get());
return;
}
addAndFinish(entry.get());
}, (android.content.DialogInterface dialog,int which) -> finish());
break;
}
}
break;
}
}
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case android.R.id.home :
discardAndFinish();
break;
case com.beemdevelopment.aegis.R.id.action_save :
onSave();
break;
case com.beemdevelopment.aegis.R.id.action_delete :
switch(MUID_STATIC) {
// EditEntryActivity_105_BuggyGUIListenerOperatorMutator
case 105175: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDeleteEntriesDialog(this, java.util.Collections.singletonList(_origEntry), null);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDeleteEntriesDialog(this, java.util.Collections.singletonList(_origEntry), (android.content.DialogInterface dialog,int which) -> {
deleteAndFinish(_origEntry);
});
break;
}
}
break;
case com.beemdevelopment.aegis.R.id.action_edit_icon :
startIconSelection();
break;
case com.beemdevelopment.aegis.R.id.action_reset_usage_count :
switch(MUID_STATIC) {
// EditEntryActivity_106_BuggyGUIListenerOperatorMutator
case 106175: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.action_reset_usage_count).setMessage(com.beemdevelopment.aegis.R.string.action_reset_usage_count_dialog).setPositiveButton(android.R.string.yes, null).setNegativeButton(android.R.string.no, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.action_reset_usage_count).setMessage(com.beemdevelopment.aegis.R.string.action_reset_usage_count_dialog).setPositiveButton(android.R.string.yes, (android.content.DialogInterface dialog,int which) -> resetUsageCount()).setNegativeButton(android.R.string.no, null).create());
break;
}
}
break;
case com.beemdevelopment.aegis.R.id.action_default_icon :
com.amulyakhare.textdrawable.TextDrawable drawable;
drawable = com.beemdevelopment.aegis.helpers.TextDrawableHelper.generate(_origEntry.getIssuer(), _origEntry.getName(), _iconView);
_iconView.setImageDrawable(drawable);
_selectedIcon = null;
_hasCustomIcon = false;
_hasChangedIcon = true;
default :
return super.onOptionsItemSelected(item);
}
return true;
}


private void startImageSelectionActivity() {
android.content.Intent galleryIntent;
switch(MUID_STATIC) {
// EditEntryActivity_107_NullIntentOperatorMutator
case 107175: {
galleryIntent = null;
break;
}
// EditEntryActivity_108_InvalidKeyIntentOperatorMutator
case 108175: {
galleryIntent = new android.content.Intent((String) null);
break;
}
// EditEntryActivity_109_RandomActionIntentDefinitionOperatorMutator
case 109175: {
galleryIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
galleryIntent = new android.content.Intent(android.content.Intent.ACTION_PICK);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_110_RandomActionIntentDefinitionOperatorMutator
case 110175: {
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
galleryIntent.setDataAndType(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
break;
}
}
android.content.Intent fileIntent;
switch(MUID_STATIC) {
// EditEntryActivity_111_NullIntentOperatorMutator
case 111175: {
fileIntent = null;
break;
}
// EditEntryActivity_112_InvalidKeyIntentOperatorMutator
case 112175: {
fileIntent = new android.content.Intent((String) null);
break;
}
// EditEntryActivity_113_RandomActionIntentDefinitionOperatorMutator
case 113175: {
fileIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
fileIntent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_114_RandomActionIntentDefinitionOperatorMutator
case 114175: {
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
fileIntent.setType("image/*");
break;
}
}
android.content.Intent chooserIntent;
switch(MUID_STATIC) {
// EditEntryActivity_115_RandomActionIntentDefinitionOperatorMutator
case 115175: {
chooserIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
chooserIntent = android.content.Intent.createChooser(galleryIntent, getString(com.beemdevelopment.aegis.R.string.select_icon));
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_116_NullValueIntentPutExtraOperatorMutator
case 116175: {
chooserIntent.putExtra(android.content.Intent.EXTRA_INITIAL_INTENTS, new Parcelable[0]);
break;
}
// EditEntryActivity_117_IntentPayloadReplacementOperatorMutator
case 117175: {
chooserIntent.putExtra(android.content.Intent.EXTRA_INITIAL_INTENTS, (android.content.Intent[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// EditEntryActivity_118_RandomActionIntentDefinitionOperatorMutator
case 118175: {
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
chooserIntent.putExtra(android.content.Intent.EXTRA_INITIAL_INTENTS, new android.content.Intent[]{ fileIntent });
break;
}
}
break;
}
}
_vaultManager.startActivityForResult(this, chooserIntent, com.beemdevelopment.aegis.ui.EditEntryActivity.PICK_IMAGE_REQUEST);
}


private void resetUsageCount() {
_prefs.resetUsageCount(_origEntry.getUUID());
_textUsageCount.setText("0");
}


private void startIconSelection() {
java.util.List<com.beemdevelopment.aegis.icons.IconPack> iconPacks;
iconPacks = _iconPackManager.getIconPacks().stream().sorted(java.util.Comparator.comparing(com.beemdevelopment.aegis.icons.IconPack::getName)).collect(java.util.stream.Collectors.toList());
if (iconPacks.size() == 0) {
startImageSelectionActivity();
return;
}
com.google.android.material.bottomsheet.BottomSheetDialog dialog;
dialog = com.beemdevelopment.aegis.ui.dialogs.IconPickerDialog.create(this, iconPacks, _textIssuer.getText().toString(), new com.beemdevelopment.aegis.ui.views.IconAdapter.Listener() {
@java.lang.Override
public void onIconSelected(com.beemdevelopment.aegis.icons.IconPack.Icon icon) {
selectIcon(icon);
}


@java.lang.Override
public void onCustomSelected() {
startImageSelectionActivity();
}

});
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


private void selectIcon(com.beemdevelopment.aegis.icons.IconPack.Icon icon) {
_selectedIcon = icon;
_hasCustomIcon = true;
_hasChangedIcon = true;
com.beemdevelopment.aegis.helpers.IconViewHelper.setLayerType(_iconView, icon.getIconType());
com.bumptech.glide.Glide.with(this).asDrawable().load(icon.getFile()).set(com.beemdevelopment.aegis.ui.glide.IconLoader.ICON_TYPE, icon.getIconType()).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).skipMemoryCache(false).into(_iconView);
}


private void startEditingIcon(android.net.Uri data) {
com.bumptech.glide.Glide.with(this).asBitmap().load(data).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).skipMemoryCache(false).into(new com.bumptech.glide.request.target.CustomTarget<android.graphics.Bitmap>() {
@java.lang.Override
public void onResourceReady(@androidx.annotation.NonNull
android.graphics.Bitmap resource, @androidx.annotation.Nullable
com.bumptech.glide.request.transition.Transition<? super android.graphics.Bitmap> transition) {
_kropView.setBitmap(resource);
}


@java.lang.Override
public void onLoadCleared(@androidx.annotation.Nullable
android.graphics.drawable.Drawable placeholder) {
}

});
_iconView.setVisibility(android.view.View.GONE);
_kropView.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// EditEntryActivity_119_BuggyGUIListenerOperatorMutator
case 119175: {
_saveImageButton.setOnClickListener(null);
break;
}
default: {
_saveImageButton.setOnClickListener((android.view.View v) -> {
stopEditingIcon(true);
});
break;
}
}
_iconBackPressHandler.setEnabled(true);
}


private void stopEditingIcon(boolean save) {
if (save && (_selectedIcon == null)) {
_iconView.setImageBitmap(_kropView.getCroppedBitmap());
}
_iconView.setVisibility(android.view.View.VISIBLE);
_kropView.setVisibility(android.view.View.GONE);
_hasCustomIcon = _hasCustomIcon || save;
_hasChangedIcon = save;
_iconBackPressHandler.setEnabled(false);
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
getMenuInflater().inflate(com.beemdevelopment.aegis.R.menu.menu_edit, menu);
if (_isNew) {
menu.findItem(com.beemdevelopment.aegis.R.id.action_delete).setVisible(false);
}
if (!_hasCustomIcon) {
menu.findItem(com.beemdevelopment.aegis.R.id.action_default_icon).setVisible(false);
}
return true;
}


private void addAndFinish(com.beemdevelopment.aegis.vault.VaultEntry entry) {
// It's possible that the new entry was already added to the vault, but writing the
// vault to disk failed, causing the user to tap 'Save' again. Calling addEntry
// again would cause a crash in that case, so the isEntryDuplicate check prevents
// that.
com.beemdevelopment.aegis.vault.VaultRepository vault;
vault = _vaultManager.getVault();
if (_isNew && (!vault.isEntryDuplicate(entry))) {
vault.addEntry(entry);
} else {
vault.replaceEntry(entry);
}
saveAndFinish(entry, false);
}


private void deleteAndFinish(com.beemdevelopment.aegis.vault.VaultEntry entry) {
_vaultManager.getVault().removeEntry(entry);
saveAndFinish(entry, true);
}


private void saveAndFinish(com.beemdevelopment.aegis.vault.VaultEntry entry, boolean delete) {
android.content.Intent intent;
switch(MUID_STATIC) {
// EditEntryActivity_120_NullIntentOperatorMutator
case 120175: {
intent = null;
break;
}
// EditEntryActivity_121_RandomActionIntentDefinitionOperatorMutator
case 121175: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_122_NullValueIntentPutExtraOperatorMutator
case 122175: {
intent.putExtra("entryUUID", new Parcelable[0]);
break;
}
// EditEntryActivity_123_IntentPayloadReplacementOperatorMutator
case 123175: {
intent.putExtra("entryUUID", (UUID) null);
break;
}
default: {
switch(MUID_STATIC) {
// EditEntryActivity_124_RandomActionIntentDefinitionOperatorMutator
case 124175: {
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
intent.putExtra("entryUUID", entry.getUUID());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// EditEntryActivity_125_NullValueIntentPutExtraOperatorMutator
case 125175: {
intent.putExtra("delete", new Parcelable[0]);
break;
}
// EditEntryActivity_126_IntentPayloadReplacementOperatorMutator
case 126175: {
intent.putExtra("delete", true);
break;
}
default: {
switch(MUID_STATIC) {
// EditEntryActivity_127_RandomActionIntentDefinitionOperatorMutator
case 127175: {
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
intent.putExtra("delete", delete);
break;
}
}
break;
}
}
if (saveAndBackupVault()) {
setResult(android.app.Activity.RESULT_OK, intent);
finish();
}
}


@java.lang.Override
protected void onActivityResult(int requestCode, final int resultCode, android.content.Intent data) {
if ((((requestCode == com.beemdevelopment.aegis.ui.EditEntryActivity.PICK_IMAGE_REQUEST) && (resultCode == android.app.Activity.RESULT_OK)) && (data != null)) && (data.getData() != null)) {
java.lang.String fileType;
fileType = com.beemdevelopment.aegis.helpers.SafHelper.getMimeType(this, data.getData());
if ((fileType != null) && fileType.equals(com.beemdevelopment.aegis.icons.IconType.SVG.toMimeType())) {
com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params params;
params = new com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params(data.getData(), "icon", null);
com.beemdevelopment.aegis.ui.tasks.ImportFileTask task;
task = new com.beemdevelopment.aegis.ui.tasks.ImportFileTask(this, (com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result result) -> {
if (result.getError() == null) {
com.beemdevelopment.aegis.ui.EditEntryActivity.CustomSvgIcon icon;
icon = new com.beemdevelopment.aegis.ui.EditEntryActivity.CustomSvgIcon(result.getFile());
selectIcon(icon);
} else {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.reading_file_error, result.getError());
}
});
task.execute(getLifecycle(), params);
} else {
startEditingIcon(data.getData());
}
}
super.onActivityResult(requestCode, resultCode, data);
}


private int parsePeriod() throws com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException {
try {
return java.lang.Integer.parseInt(_textPeriodCounter.getText().toString());
} catch (java.lang.NumberFormatException e) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("Period is not an integer.");
}
}


private com.beemdevelopment.aegis.vault.VaultEntry parseEntry() throws com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException {
if (_textSecret.length() == 0) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("Secret is a required field.");
}
java.lang.String type;
type = _dropdownType.getText().toString();
java.lang.String algo;
algo = _dropdownAlgo.getText().toString();
java.lang.String lowerCasedType;
lowerCasedType = type.toLowerCase(java.util.Locale.ROOT);
if (lowerCasedType.equals(com.beemdevelopment.aegis.otp.YandexInfo.ID) || lowerCasedType.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID)) {
int pinLength;
pinLength = _textPin.length();
if (pinLength < 4) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("PIN is a required field. Must have a minimum length of 4 digits.");
}
if ((pinLength != 4) && lowerCasedType.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID)) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("PIN must have a length of 4 digits.");
}
}
int digits;
try {
digits = java.lang.Integer.parseInt(_textDigits.getText().toString());
} catch (java.lang.NumberFormatException e) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("Digits is not an integer.");
}
byte[] secret;
try {
java.lang.String secretString;
secretString = new java.lang.String(com.beemdevelopment.aegis.helpers.EditTextHelper.getEditTextChars(_textSecret));
secret = (lowerCasedType.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID)) ? com.beemdevelopment.aegis.encoding.Hex.decode(secretString) : com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseSecret(secretString);
if (secret.length == 0) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("Secret cannot be empty");
}
} catch (com.beemdevelopment.aegis.encoding.EncodingException e) {
java.lang.String exceptionMessage;
exceptionMessage = (lowerCasedType.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID)) ? "Secret is not valid hexadecimal" : "Secret is not valid base32.";
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException(exceptionMessage);
}
com.beemdevelopment.aegis.otp.OtpInfo info;
try {
switch (type.toLowerCase(java.util.Locale.ROOT)) {
case com.beemdevelopment.aegis.otp.TotpInfo.ID :
info = new com.beemdevelopment.aegis.otp.TotpInfo(secret, algo, digits, parsePeriod());
break;
case com.beemdevelopment.aegis.otp.SteamInfo.ID :
info = new com.beemdevelopment.aegis.otp.SteamInfo(secret, algo, digits, parsePeriod());
break;
case com.beemdevelopment.aegis.otp.HotpInfo.ID :
long counter;
try {
counter = java.lang.Long.parseLong(_textPeriodCounter.getText().toString());
} catch (java.lang.NumberFormatException e) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("Counter is not an integer.");
}
info = new com.beemdevelopment.aegis.otp.HotpInfo(secret, algo, digits, counter);
break;
case com.beemdevelopment.aegis.otp.YandexInfo.ID :
info = new com.beemdevelopment.aegis.otp.YandexInfo(secret, _textPin.getText().toString());
break;
case com.beemdevelopment.aegis.otp.MotpInfo.ID :
info = new com.beemdevelopment.aegis.otp.MotpInfo(secret, _textPin.getText().toString());
break;
default :
throw new java.lang.RuntimeException(java.lang.String.format("Unsupported OTP type: %s", type));
}
info.setDigits(digits);
info.setAlgorithm(algo);
} catch (com.beemdevelopment.aegis.otp.OtpInfoException e) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException("The entered info is incorrect: " + e.getMessage());
}
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = com.beemdevelopment.aegis.util.Cloner.clone(_origEntry);
entry.setInfo(info);
entry.setIssuer(_textIssuer.getText().toString());
entry.setName(_textName.getText().toString());
entry.setNote(_textNote.getText().toString());
int groupPos;
groupPos = _dropdownGroupList.indexOf(_dropdownGroup.getText().toString());
if (groupPos != 0) {
java.lang.String group;
group = _dropdownGroupList.get(groupPos);
entry.setGroup(group);
} else {
entry.setGroup(null);
}
if (_hasChangedIcon) {
if (_hasCustomIcon) {
if (_selectedIcon == null) {
android.graphics.Bitmap bitmap;
bitmap = ((android.graphics.drawable.BitmapDrawable) (_iconView.getDrawable())).getBitmap();
java.io.ByteArrayOutputStream stream;
stream = new java.io.ByteArrayOutputStream();
// the quality parameter is ignored for PNG
bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream);
byte[] data;
data = stream.toByteArray();
entry.setIcon(data, com.beemdevelopment.aegis.icons.IconType.PNG);
} else {
byte[] iconBytes;
try (java.io.FileInputStream inStream = new java.io.FileInputStream(_selectedIcon.getFile())) {
iconBytes = com.beemdevelopment.aegis.util.IOUtils.readFile(inStream);
} catch (java.io.IOException e) {
throw new com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException(e.getMessage());
}
entry.setIcon(iconBytes, _selectedIcon.getIconType());
}
} else {
entry.setIcon(null, com.beemdevelopment.aegis.icons.IconType.INVALID);
}
}
return entry;
}


private void onSaveError(java.lang.String msg) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(getString(com.beemdevelopment.aegis.R.string.saving_profile_error)).setMessage(msg).setPositiveButton(android.R.string.ok, null).create());
}


private boolean onSave() {
if (_iconBackPressHandler.isEnabled()) {
stopEditingIcon(true);
}
com.beemdevelopment.aegis.vault.VaultEntry entry;
try {
entry = parseEntry();
} catch (com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException e) {
onSaveError(e.getMessage());
return false;
}
addAndFinish(entry);
return true;
}


private static void setViewEnabled(android.view.View view, boolean enabled) {
view.setEnabled(enabled);
if (view instanceof android.view.ViewGroup) {
android.view.ViewGroup group;
group = ((android.view.ViewGroup) (view));
for (int i = 0; i < group.getChildCount(); i++) {
com.beemdevelopment.aegis.ui.EditEntryActivity.setViewEnabled(group.getChildAt(i), enabled);
}
}
}


private final android.text.TextWatcher _validationListener = new com.beemdevelopment.aegis.helpers.SimpleTextWatcher((android.text.Editable s) -> {
updateBackPressHandlerState();
});

private final android.text.TextWatcher _nameChangeListener = new com.beemdevelopment.aegis.helpers.SimpleTextWatcher((android.text.Editable s) -> {
if (!_hasCustomIcon) {
com.amulyakhare.textdrawable.TextDrawable drawable;
drawable = com.beemdevelopment.aegis.helpers.TextDrawableHelper.generate(_textIssuer.getText().toString(), _textName.getText().toString(), _iconView);
_iconView.setImageDrawable(drawable);
}
});

private void updateBackPressHandlerState() {
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = null;
try {
entry = parseEntry();
} catch (com.beemdevelopment.aegis.ui.EditEntryActivity.ParseException ignored) {
}
boolean backEnabled;
backEnabled = hasUnsavedChanges(entry);
_backPressHandler.setEnabled(backEnabled);
}


private class BackPressHandler extends androidx.activity.OnBackPressedCallback {
public BackPressHandler() {
super(false);
}


@java.lang.Override
public void handleOnBackPressed() {
discardAndFinish();
}

}

private class IconBackPressHandler extends androidx.activity.OnBackPressedCallback {
public IconBackPressHandler() {
super(false);
}


@java.lang.Override
public void handleOnBackPressed() {
stopEditingIcon(false);
}

}

private static class ParseException extends java.lang.Exception {
public ParseException(java.lang.String message) {
super(message);
}

}

private static class CustomSvgIcon extends com.beemdevelopment.aegis.icons.IconPack.Icon {
private final java.io.File _file;

protected CustomSvgIcon(java.io.File file) {
super(file.getAbsolutePath(), null, null);
_file = file;
}


@androidx.annotation.Nullable
public java.io.File getFile() {
return _file;
}


@java.lang.Override
public com.beemdevelopment.aegis.icons.IconType getIconType() {
return com.beemdevelopment.aegis.icons.IconType.SVG;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
