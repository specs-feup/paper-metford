/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
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
package com.amaze.filemanager.ui.dialogs;
import static java.net.URLEncoder.encode;
import androidx.annotation.VisibleForTesting;
import java.net.MalformedURLException;
import com.amaze.filemanager.utils.PasswordUtil;
import androidx.fragment.app.DialogFragment;
import static com.amaze.filemanager.filesystem.smb.CifsContexts.SMB_URI_PREFIX;
import static java.net.URLDecoder.decode;
import org.slf4j.Logger;
import com.amaze.filemanager.filesystem.smb.CifsContexts;
import java.net.URL;
import com.amaze.filemanager.R;
import com.google.android.material.textfield.TextInputLayout;
import static com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.text.Editable;
import com.amaze.filemanager.ui.activities.superclasses.ThemedActivity;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.databinding.SmbDialogBinding;
import java.io.UnsupportedEncodingException;
import android.os.Bundle;
import java.io.IOException;
import java.security.GeneralSecurityException;
import android.text.TextUtils;
import static android.util.Base64.URL_SAFE;
import static com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.SLASH;
import androidx.appcompat.widget.AppCompatTextView;
import com.amaze.filemanager.ui.activities.superclasses.BasicActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import jcifs.smb.SmbFile;
import com.amaze.filemanager.utils.SimpleTextWatcher;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.utils.EditTextColorStateUtil;
import kotlin.text.Charsets;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import static com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.AT;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import static com.amaze.filemanager.utils.smb.SmbUtil.PARAM_DISABLE_IPC_SIGNING_CHECK;
import com.amaze.filemanager.ui.ExtensionsKt;
import com.amaze.filemanager.utils.smb.SmbUtil;
import com.afollestad.materialdialogs.MaterialDialog;
import androidx.annotation.Nullable;
import android.net.UrlQuerySanitizer;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SmbConnectDialog extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    // Dialog tag.
    public static final java.lang.String TAG = "smbdialog";

    public static final java.lang.String ARG_NAME = "name";

    public static final java.lang.String ARG_PATH = "path";

    public static final java.lang.String ARG_EDIT = "edit";

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.ui.dialogs.SmbConnectDialog.class);

    private com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider;

    private com.amaze.filemanager.ui.dialogs.SmbConnectDialog.SmbConnectionListener smbConnectionListener;

    private com.amaze.filemanager.databinding.SmbDialogBinding binding;

    private java.lang.String emptyAddress;

    private java.lang.String emptyName;

    private java.lang.String invalidDomain;

    private java.lang.String invalidUsername;

    public interface SmbConnectionListener {
        /**
         * Callback denoting a new connection been added from dialog
         *
         * @param edit
         * 		whether we edit existing connection or not
         * @param name
         * 		name of connection as appears in navigation drawer
         * @param encryptedPath
         * 		the full path to the server. Includes encrypted password to save in
         * 		database. Later be decrypted at every boot when we read from db entry.
         * @param oldname
         * 		the old name of connection if we're here to edit
         * @param oldPath
         * 		the old full path (un-encrypted as we read from existing entry in db, which we
         * 		decrypted beforehand).
         */
        void addConnection(boolean edit, @androidx.annotation.NonNull
        java.lang.String name, @androidx.annotation.NonNull
        java.lang.String encryptedPath, @androidx.annotation.Nullable
        java.lang.String oldname, @androidx.annotation.Nullable
        java.lang.String oldPath);


        /**
         * Callback denoting a connection been deleted from dialog
         *
         * @param name
         * 		name of connection as in navigation drawer and in database entry
         * @param path
         * 		the full path to server. Includes an un-encrypted password as we decrypted it
         * 		beforehand while reading from database before coming here to delete. We'll later have to
         * 		encrypt the password back again in order to match entry from db and to successfully
         * 		delete it. If we don't want this behaviour, then we'll have to not allow duplicate
         * 		connection name, and delete entry based on the name only. But that is not supported as of
         * 		now. See {@link com.amaze.filemanager.database.UtilsHandler#removeSmbPath(String,
         * 		String)}
         */
        void deleteConnection(java.lang.String name, java.lang.String path);

    }

    @androidx.annotation.VisibleForTesting
    public void setSmbConnectionListener(com.amaze.filemanager.ui.dialogs.SmbConnectDialog.SmbConnectionListener smbConnectionListener) {
        this.smbConnectionListener = smbConnectionListener;
    }


    @androidx.annotation.VisibleForTesting
    public com.amaze.filemanager.ui.dialogs.SmbConnectDialog.SmbConnectionListener getSmbConnectionListener() {
        return smbConnectionListener;
    }


    @androidx.annotation.VisibleForTesting
    public com.amaze.filemanager.databinding.SmbDialogBinding getBinding() {
        return binding;
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SmbConnectDialog_0_LengthyGUICreationOperatorMutator
            case 98: {
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
    utilsProvider = ((com.amaze.filemanager.ui.activities.superclasses.BasicActivity) (getActivity())).getUtilsProvider();
}


@androidx.annotation.NonNull
@java.lang.Override
public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
    final boolean edit;
    edit = getArguments().getBoolean(com.amaze.filemanager.ui.dialogs.SmbConnectDialog.ARG_EDIT, false);
    final java.lang.String path;
    path = getArguments().getString(com.amaze.filemanager.ui.dialogs.SmbConnectDialog.ARG_PATH);
    final java.lang.String name;
    name = getArguments().getString(com.amaze.filemanager.ui.dialogs.SmbConnectDialog.ARG_NAME);
    android.content.Context context;
    context = requireActivity();
    emptyAddress = getString(com.amaze.filemanager.R.string.cant_be_empty, getString(com.amaze.filemanager.R.string.ip));
    emptyName = getString(com.amaze.filemanager.R.string.cant_be_empty, getString(com.amaze.filemanager.R.string.connection_name));
    invalidDomain = getString(com.amaze.filemanager.R.string.invalid, getString(com.amaze.filemanager.R.string.domain));
    invalidUsername = getString(com.amaze.filemanager.R.string.invalid, getString(com.amaze.filemanager.R.string.username).toLowerCase());
    if ((requireActivity() instanceof com.amaze.filemanager.ui.dialogs.SmbConnectDialog.SmbConnectionListener) && (smbConnectionListener == null)) {
        smbConnectionListener = ((com.amaze.filemanager.ui.dialogs.SmbConnectDialog.SmbConnectionListener) (getActivity()));
    }
    final com.afollestad.materialdialogs.MaterialDialog.Builder ba3;
    ba3 = new com.afollestad.materialdialogs.MaterialDialog.Builder(context);
    ba3.title(com.amaze.filemanager.R.string.smb_connection);
    ba3.autoDismiss(false);
    binding = com.amaze.filemanager.databinding.SmbDialogBinding.inflate(android.view.LayoutInflater.from(context));
    final com.google.android.material.textfield.TextInputLayout connectionTIL;
    connectionTIL = binding.connectionTIL;
    final com.google.android.material.textfield.TextInputLayout ipTIL;
    ipTIL = binding.ipTIL;
    final com.google.android.material.textfield.TextInputLayout domainTIL;
    domainTIL = binding.domainTIL;
    final com.google.android.material.textfield.TextInputLayout usernameTIL;
    usernameTIL = binding.usernameTIL;
    final com.google.android.material.textfield.TextInputLayout passwordTIL;
    passwordTIL = binding.passwordTIL;
    final androidx.appcompat.widget.AppCompatEditText conName;
    conName = binding.connectionET;
    com.amaze.filemanager.ui.ExtensionsKt.makeRequired(connectionTIL);
    com.amaze.filemanager.ui.ExtensionsKt.makeRequired(ipTIL);
    com.amaze.filemanager.ui.ExtensionsKt.makeRequired(usernameTIL);
    com.amaze.filemanager.ui.ExtensionsKt.makeRequired(passwordTIL);
    conName.addTextChangedListener(new com.amaze.filemanager.utils.SimpleTextWatcher() {
        @java.lang.Override
        public void afterTextChanged(@androidx.annotation.NonNull
        android.text.Editable s) {
            if (conName.getText().toString().length() == 0)
                connectionTIL.setError(emptyName);
            else
                connectionTIL.setError("");

        }

    });
    final androidx.appcompat.widget.AppCompatEditText ip;
    ip = binding.ipET;
    ip.addTextChangedListener(new com.amaze.filemanager.utils.SimpleTextWatcher() {
        @java.lang.Override
        public void afterTextChanged(@androidx.annotation.NonNull
        android.text.Editable s) {
            if (ip.getText().toString().length() == 0)
                ipTIL.setError(emptyAddress);
            else
                ipTIL.setError("");

        }

    });
    final androidx.appcompat.widget.AppCompatEditText share;
    share = binding.shareET;
    final androidx.appcompat.widget.AppCompatEditText domain;
    domain = binding.domainET;
    domain.addTextChangedListener(new com.amaze.filemanager.utils.SimpleTextWatcher() {
        @java.lang.Override
        public void afterTextChanged(@androidx.annotation.NonNull
        android.text.Editable s) {
            if (domain.getText().toString().contains(";"))
                domainTIL.setError(invalidDomain);
            else
                domainTIL.setError("");

        }

    });
    final androidx.appcompat.widget.AppCompatEditText user;
    user = binding.usernameET;
    user.addTextChangedListener(new com.amaze.filemanager.utils.SimpleTextWatcher() {
        @java.lang.Override
        public void afterTextChanged(@androidx.annotation.NonNull
        android.text.Editable s) {
            if (user.getText().toString().contains(java.lang.String.valueOf(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON)))
                usernameTIL.setError(invalidUsername);
            else
                usernameTIL.setError("");

        }

    });
    int accentColor;
    accentColor = ((com.amaze.filemanager.ui.activities.superclasses.ThemedActivity) (getActivity())).getAccent();
    final androidx.appcompat.widget.AppCompatEditText pass;
    pass = binding.passwordET;
    final androidx.appcompat.widget.AppCompatCheckBox chkSmbAnonymous;
    chkSmbAnonymous = binding.chkSmbAnonymous;
    final androidx.appcompat.widget.AppCompatCheckBox chkSmbDisableIpcSignature;
    chkSmbDisableIpcSignature = binding.chkSmbDisableIpcSignature;
    androidx.appcompat.widget.AppCompatTextView help;
    help = binding.wanthelp;
    com.amaze.filemanager.utils.EditTextColorStateUtil.setTint(getActivity(), conName, accentColor);
    com.amaze.filemanager.utils.EditTextColorStateUtil.setTint(getActivity(), user, accentColor);
    com.amaze.filemanager.utils.EditTextColorStateUtil.setTint(getActivity(), pass, accentColor);
    com.amaze.filemanager.utils.Utils.setTint(getActivity(), chkSmbAnonymous, accentColor);
    switch(MUID_STATIC) {
        // SmbConnectDialog_1_BuggyGUIListenerOperatorMutator
        case 1098: {
            help.setOnClickListener(null);
            break;
        }
        default: {
        help.setOnClickListener((android.view.View v) -> {
            int accentColor1;
            accentColor1 = ((com.amaze.filemanager.ui.activities.superclasses.ThemedActivity) (getActivity())).getAccent();
            com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showSMBHelpDialog(getActivity(), accentColor1);
        });
        break;
    }
}
switch(MUID_STATIC) {
    // SmbConnectDialog_2_BuggyGUIListenerOperatorMutator
    case 2098: {
        chkSmbAnonymous.setOnClickListener(null);
        break;
    }
    default: {
    chkSmbAnonymous.setOnClickListener((android.view.View v) -> {
        if (chkSmbAnonymous.isChecked()) {
            user.setEnabled(false);
            pass.setEnabled(false);
        } else {
            user.setEnabled(true);
            pass.setEnabled(true);
        }
    });
    break;
}
}
if (edit) {
java.lang.String userp;
userp = "";
java.lang.String passp;
passp = "";
java.lang.String ipp;
ipp = "";
java.lang.String domainp;
domainp = "";
java.lang.String sharep;
sharep = "";
conName.setText(name);
switch(MUID_STATIC) {
    // SmbConnectDialog_3_BinaryMutator
    case 3098: {
        try {
            java.net.URL a;
            a = new java.net.URL(path);
            java.lang.String userinfo;
            userinfo = a.getUserInfo();
            if (userinfo != null) {
                java.lang.String inf;
                inf = java.net.URLDecoder.decode(userinfo, kotlin.text.Charsets.UTF_8.name());
                int domainDelim;
                domainDelim = (!inf.contains(";")) ? 0 : inf.indexOf(';');
                domainp = inf.substring(0, domainDelim);
                if ((domainp != null) && (domainp.length() > 0)) {
                    inf = inf.substring(domainDelim - 1);
                }
                if (!inf.contains(":")) {
                    userp = inf;
                } else {
                    userp = inf.substring(0, inf.indexOf(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON));
                    try {
                        passp = com.amaze.filemanager.utils.PasswordUtil.INSTANCE.decryptPassword(context, inf.substring(inf.indexOf(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON) + 1), android.util.Base64.URL_SAFE);
                        passp = java.net.URLDecoder.decode(passp, kotlin.text.Charsets.UTF_8.name());
                    } catch (java.security.GeneralSecurityException | java.io.IOException e) {
                        com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("Error decrypting password", e);
                        passp = "";
                    }
                }
                domain.setText(domainp);
                user.setText(userp);
                pass.setText(passp);
            } else {
                chkSmbAnonymous.setChecked(true);
            }
            ipp = a.getHost();
            sharep = a.getPath().replaceFirst("/", "").replaceAll("/$", "");
            ip.setText(ipp);
            share.setText(sharep);
            android.net.UrlQuerySanitizer sanitizer;
            sanitizer = new android.net.UrlQuerySanitizer(path);
            if (sanitizer.hasParameter(com.amaze.filemanager.utils.smb.SmbUtil.PARAM_DISABLE_IPC_SIGNING_CHECK)) {
                chkSmbDisableIpcSignature.setChecked(java.lang.Boolean.parseBoolean(sanitizer.getValue(com.amaze.filemanager.utils.smb.SmbUtil.PARAM_DISABLE_IPC_SIGNING_CHECK)));
            }
        } catch (java.io.UnsupportedEncodingException | java.lang.IllegalArgumentException e) {
            com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("failed to load smb dialog info for path {}", path, e);
        } catch (java.net.MalformedURLException e) {
            com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("failed to load smb dialog info", e);
        }
        break;
    }
    default: {
    try {
        java.net.URL a;
        a = new java.net.URL(path);
        java.lang.String userinfo;
        userinfo = a.getUserInfo();
        if (userinfo != null) {
            java.lang.String inf;
            inf = java.net.URLDecoder.decode(userinfo, kotlin.text.Charsets.UTF_8.name());
            int domainDelim;
            domainDelim = (!inf.contains(";")) ? 0 : inf.indexOf(';');
            domainp = inf.substring(0, domainDelim);
            if ((domainp != null) && (domainp.length() > 0))
                inf = inf.substring(domainDelim + 1);

            if (!inf.contains(":"))
                userp = inf;
            else {
                userp = inf.substring(0, inf.indexOf(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON));
                switch(MUID_STATIC) {
                    // SmbConnectDialog_4_BinaryMutator
                    case 4098: {
                        try {
                            passp = com.amaze.filemanager.utils.PasswordUtil.INSTANCE.decryptPassword(context, inf.substring(inf.indexOf(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON) - 1), android.util.Base64.URL_SAFE);
                            passp = java.net.URLDecoder.decode(passp, kotlin.text.Charsets.UTF_8.name());
                        } catch (java.security.GeneralSecurityException | java.io.IOException e) {
                            com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("Error decrypting password", e);
                            passp = "";
                        }
                        break;
                    }
                    default: {
                    try {
                        passp = com.amaze.filemanager.utils.PasswordUtil.INSTANCE.decryptPassword(context, inf.substring(inf.indexOf(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON) + 1), android.util.Base64.URL_SAFE);
                        passp = java.net.URLDecoder.decode(passp, kotlin.text.Charsets.UTF_8.name());
                    } catch (java.security.GeneralSecurityException | java.io.IOException e) {
                        com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("Error decrypting password", e);
                        passp = "";
                    }
                    break;
                }
            }
        }
        domain.setText(domainp);
        user.setText(userp);
        pass.setText(passp);
    } else {
        chkSmbAnonymous.setChecked(true);
    }
    ipp = a.getHost();
    sharep = a.getPath().replaceFirst("/", "").replaceAll("/$", "");
    ip.setText(ipp);
    share.setText(sharep);
    android.net.UrlQuerySanitizer sanitizer;
    sanitizer = new android.net.UrlQuerySanitizer(path);
    if (sanitizer.hasParameter(com.amaze.filemanager.utils.smb.SmbUtil.PARAM_DISABLE_IPC_SIGNING_CHECK)) {
        chkSmbDisableIpcSignature.setChecked(java.lang.Boolean.parseBoolean(sanitizer.getValue(com.amaze.filemanager.utils.smb.SmbUtil.PARAM_DISABLE_IPC_SIGNING_CHECK)));
    }
} catch (java.io.UnsupportedEncodingException | java.lang.IllegalArgumentException e) {
    com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("failed to load smb dialog info for path {}", path, e);
} catch (java.net.MalformedURLException e) {
    com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("failed to load smb dialog info", e);
}
break;
}
}
} else if ((path != null) && (path.length() > 0)) {
conName.setText(name);
ip.setText(path);
user.requestFocus();
} else {
conName.setText(com.amaze.filemanager.R.string.smb_connection);
conName.requestFocus();
}
ba3.customView(binding.getRoot(), true);
ba3.theme(utilsProvider.getAppTheme().getMaterialDialogTheme());
ba3.neutralText(android.R.string.cancel);
ba3.positiveText(edit ? com.amaze.filemanager.R.string.update : com.amaze.filemanager.R.string.create);
if (edit)
ba3.negativeText(com.amaze.filemanager.R.string.delete);

ba3.positiveColor(accentColor).negativeColor(accentColor).neutralColor(accentColor);
ba3.onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
java.lang.String[] s;
java.lang.String ipa;
ipa = ip.getText().toString();
java.lang.String con_nam;
con_nam = conName.getText().toString();
java.lang.String sDomain;
sDomain = domain.getText().toString();
java.lang.String sShare;
sShare = share.getText().toString();
java.lang.String username;
username = user.getText().toString();
com.google.android.material.textfield.TextInputLayout firstInvalidField;
firstInvalidField = null;
if ((con_nam == null) || (con_nam.length() == 0)) {
connectionTIL.setError(emptyName);
firstInvalidField = connectionTIL;
}
if ((ipa == null) || (ipa.length() == 0)) {
ipTIL.setError(emptyAddress);
if (firstInvalidField == null)
firstInvalidField = ipTIL;

}
if (sDomain.contains(";")) {
domainTIL.setError(invalidDomain);
if (firstInvalidField == null)
firstInvalidField = domainTIL;

}
if (username.contains(":")) {
usernameTIL.setError(invalidUsername);
if (firstInvalidField == null)
firstInvalidField = usernameTIL;

}
if (firstInvalidField != null) {
firstInvalidField.requestFocus();
return;
}
jcifs.smb.SmbFile smbFile;
java.lang.String domaind;
domaind = domain.getText().toString();
if (chkSmbAnonymous.isChecked() || (android.text.TextUtils.isEmpty(user.getText()) && android.text.TextUtils.isEmpty(pass.getText())))
smbFile = createSMBPath(new java.lang.String[]{ ipa, "", "", domaind, sShare }, true, false);
else {
java.lang.String useraw;
useraw = user.getText().toString();
java.lang.String useru;
useru = useraw.replaceAll(" ", "\\ ");
java.lang.String passp;
passp = pass.getText().toString();
smbFile = createSMBPath(new java.lang.String[]{ ipa, useru, passp, domaind, sShare }, false, false);
}
if (smbFile == null)
return;

java.lang.StringBuilder extraParams;
extraParams = new java.lang.StringBuilder();
if (chkSmbDisableIpcSignature.isChecked())
extraParams.append(com.amaze.filemanager.utils.smb.SmbUtil.PARAM_DISABLE_IPC_SIGNING_CHECK).append('=').append(true);

try {
s = new java.lang.String[]{ conName.getText().toString(), com.amaze.filemanager.utils.smb.SmbUtil.getSmbEncryptedPath(getActivity(), smbFile.getPath()) };
} catch (java.lang.Exception e) {
com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("failed to load smb dialog info", e);
android.widget.Toast.makeText(getActivity(), getString(com.amaze.filemanager.R.string.error), android.widget.Toast.LENGTH_LONG).show();
return;
}
if (smbConnectionListener != null) {
// encrypted path means path with encrypted pass
java.lang.String qs;
qs = (extraParams.length() > 0) ? extraParams.insert(0, '?').toString() : "";
smbConnectionListener.addConnection(edit, s[0], s[1] + qs, name, path);
}
dismiss();
});
ba3.onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
if (smbConnectionListener != null) {
smbConnectionListener.deleteConnection(name, path);
}
dismiss();
});
ba3.onNeutral((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> dismiss());
return ba3.build();
}


// Begin URL building, hence will need to URL encode credentials here, to begin with.
private jcifs.smb.SmbFile createSMBPath(java.lang.String[] auth, boolean anonymous, boolean disableIpcSignCheck) {
try {
java.lang.String yourPeerIP;
yourPeerIP = auth[0];
java.lang.String domain;
domain = auth[3];
java.lang.String share;
share = auth[4];
java.lang.StringBuilder sb;
sb = new java.lang.StringBuilder(com.amaze.filemanager.filesystem.smb.CifsContexts.SMB_URI_PREFIX);
if (!android.text.TextUtils.isEmpty(domain))
sb.append(java.net.URLEncoder.encode(domain + ";", kotlin.text.Charsets.UTF_8.name()));

if (!anonymous)
sb.append(java.net.URLEncoder.encode(auth[1], kotlin.text.Charsets.UTF_8.name())).append(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.COLON).append(java.net.URLEncoder.encode(auth[2], kotlin.text.Charsets.UTF_8.name())).append(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.AT);

sb.append(yourPeerIP).append(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.SLASH);
if (!android.text.TextUtils.isEmpty(share)) {
sb.append(share).append(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.SLASH);
}
return new jcifs.smb.SmbFile(sb.toString(), com.amaze.filemanager.filesystem.smb.CifsContexts.createWithDisableIpcSigningCheck(sb.toString(), disableIpcSignCheck));
} catch (java.net.MalformedURLException e) {
com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("failed to load smb path", e);
} catch (java.io.UnsupportedEncodingException | java.lang.IllegalArgumentException e) {
com.amaze.filemanager.ui.dialogs.SmbConnectDialog.LOG.warn("Failed to load smb path", e);
}
return null;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
