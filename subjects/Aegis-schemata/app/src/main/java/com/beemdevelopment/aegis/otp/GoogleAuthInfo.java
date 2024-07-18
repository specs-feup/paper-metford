package com.beemdevelopment.aegis.otp;
import java.util.stream.Collectors;
import java.util.Set;
import com.beemdevelopment.aegis.encoding.EncodingException;
import com.google.protobuf.ByteString;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.google.protobuf.InvalidProtocolBufferException;
import android.net.Uri;
import com.beemdevelopment.aegis.GoogleAuthProtos;
import androidx.annotation.NonNull;
import java.util.List;
import com.beemdevelopment.aegis.encoding.Base64;
import com.beemdevelopment.aegis.encoding.Base32;
import com.beemdevelopment.aegis.encoding.Hex;
import java.io.Serializable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GoogleAuthInfo implements com.beemdevelopment.aegis.otp.Transferable , java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String SCHEME = "otpauth";

    public static final java.lang.String SCHEME_EXPORT = "otpauth-migration";

    private com.beemdevelopment.aegis.otp.OtpInfo _info;

    private java.lang.String _accountName;

    private java.lang.String _issuer;

    public GoogleAuthInfo(com.beemdevelopment.aegis.otp.OtpInfo info, java.lang.String accountName, java.lang.String issuer) {
        _info = info;
        _accountName = accountName;
        _issuer = issuer;
    }


    public static com.beemdevelopment.aegis.otp.GoogleAuthInfo parseUri(java.lang.String s) throws com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
        android.net.Uri uri;
        uri = android.net.Uri.parse(s);
        if (uri == null) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, java.lang.String.format("Bad URI format: %s", s));
        }
        return com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseUri(uri);
    }


    public static com.beemdevelopment.aegis.otp.GoogleAuthInfo parseUri(android.net.Uri uri) throws com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
        java.lang.String scheme;
        scheme = uri.getScheme();
        if ((scheme == null) || (!(scheme.equals(com.beemdevelopment.aegis.otp.GoogleAuthInfo.SCHEME) || scheme.equals(com.beemdevelopment.aegis.otp.MotpInfo.SCHEME)))) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, java.lang.String.format("Unsupported protocol: %s", scheme));
        }
        // 'secret' is a required parameter
        java.lang.String encodedSecret;
        encodedSecret = uri.getQueryParameter("secret");
        if (encodedSecret == null) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Parameter 'secret' is not present");
        }
        byte[] secret;
        try {
            secret = (scheme.equals(com.beemdevelopment.aegis.otp.MotpInfo.SCHEME)) ? com.beemdevelopment.aegis.encoding.Hex.decode(encodedSecret) : com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseSecret(encodedSecret);
        } catch (com.beemdevelopment.aegis.encoding.EncodingException e) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Bad secret", e);
        }
        if (secret.length == 0) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Secret is empty");
        }
        com.beemdevelopment.aegis.otp.OtpInfo info;
        java.lang.String issuer;
        issuer = "";
        try {
            java.lang.String type;
            type = (scheme.equals(com.beemdevelopment.aegis.otp.MotpInfo.SCHEME)) ? com.beemdevelopment.aegis.otp.MotpInfo.ID : uri.getHost();
            if (type == null) {
                throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, java.lang.String.format("Host not present in URI: %s", uri.toString()));
            }
            switch (type) {
                case "totp" :
                    com.beemdevelopment.aegis.otp.TotpInfo totpInfo;
                    totpInfo = new com.beemdevelopment.aegis.otp.TotpInfo(secret);
                    java.lang.String period;
                    period = uri.getQueryParameter("period");
                    if (period != null) {
                        totpInfo.setPeriod(java.lang.Integer.parseInt(period));
                    }
                    info = totpInfo;
                    break;
                case "steam" :
                    com.beemdevelopment.aegis.otp.SteamInfo steamInfo;
                    steamInfo = new com.beemdevelopment.aegis.otp.SteamInfo(secret);
                    period = uri.getQueryParameter("period");
                    if (period != null) {
                        steamInfo.setPeriod(java.lang.Integer.parseInt(period));
                    }
                    info = steamInfo;
                    break;
                case "hotp" :
                    com.beemdevelopment.aegis.otp.HotpInfo hotpInfo;
                    hotpInfo = new com.beemdevelopment.aegis.otp.HotpInfo(secret);
                    java.lang.String counter;
                    counter = uri.getQueryParameter("counter");
                    if (counter == null) {
                        throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Parameter 'counter' is not present");
                    }
                    hotpInfo.setCounter(java.lang.Long.parseLong(counter));
                    info = hotpInfo;
                    break;
                case com.beemdevelopment.aegis.otp.YandexInfo.HOST_ID :
                    java.lang.String pin;
                    pin = uri.getQueryParameter("pin");
                    if (pin != null) {
                        pin = new java.lang.String(com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseSecret(pin), java.nio.charset.StandardCharsets.UTF_8);
                    }
                    info = new com.beemdevelopment.aegis.otp.YandexInfo(secret, pin);
                    issuer = info.getType();
                    break;
                case com.beemdevelopment.aegis.otp.MotpInfo.ID :
                    info = new com.beemdevelopment.aegis.otp.MotpInfo(secret);
                    break;
                default :
                    throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, java.lang.String.format("Unsupported OTP type: %s", type));
            }
        } catch (com.beemdevelopment.aegis.otp.OtpInfoException | java.lang.NumberFormatException | com.beemdevelopment.aegis.encoding.EncodingException e) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, e);
        }
        // provider info used to disambiguate accounts
        java.lang.String path;
        path = uri.getPath();
        java.lang.String label;
        label = ((path != null) && (path.length() > 0)) ? path.substring(1) : "";
        java.lang.String accountName;
        accountName = "";
        if (label.contains(":")) {
            // a label can only contain one colon
            // it's ok to fail if that's not the case
            java.lang.String[] strings;
            strings = label.split(":");
            if (strings.length == 2) {
                issuer = strings[0];
                accountName = strings[1];
            } else {
                // at this point, just dump the whole thing into the accountName
                accountName = label;
            }
        } else {
            // label only contains the account name
            // grab the issuer's info from the 'issuer' parameter if it's present
            java.lang.String issuerParam;
            issuerParam = uri.getQueryParameter("issuer");
            if (issuer.isEmpty()) {
                issuer = (issuerParam != null) ? issuerParam : "";
            }
            accountName = label;
        }
        // just use the defaults if these parameters aren't set
        try {
            java.lang.String algorithm;
            algorithm = uri.getQueryParameter("algorithm");
            if (algorithm != null) {
                info.setAlgorithm(algorithm);
            }
            java.lang.String digits;
            digits = uri.getQueryParameter("digits");
            if (digits != null) {
                info.setDigits(java.lang.Integer.parseInt(digits));
            }
        } catch (com.beemdevelopment.aegis.otp.OtpInfoException | java.lang.NumberFormatException e) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, e);
        }
        return new com.beemdevelopment.aegis.otp.GoogleAuthInfo(info, accountName, issuer);
    }


    /**
     * Decodes the given base 32 secret, while being tolerant of whitespace and dashes.
     */
    public static byte[] parseSecret(java.lang.String s) throws com.beemdevelopment.aegis.encoding.EncodingException {
        s = s.trim().replace("-", "").replace(" ", "");
        return com.beemdevelopment.aegis.encoding.Base32.decode(s);
    }


    public static com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export parseExportUri(java.lang.String s) throws com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
        android.net.Uri uri;
        uri = android.net.Uri.parse(s);
        if (uri == null) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Bad URI format");
        }
        return com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseExportUri(uri);
    }


    public static com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export parseExportUri(android.net.Uri uri) throws com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
        java.lang.String scheme;
        scheme = uri.getScheme();
        if ((scheme == null) || (!scheme.equals(com.beemdevelopment.aegis.otp.GoogleAuthInfo.SCHEME_EXPORT))) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Unsupported protocol");
        }
        java.lang.String host;
        host = uri.getHost();
        if ((host == null) || (!host.equals("offline"))) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Unsupported host");
        }
        java.lang.String data;
        data = uri.getQueryParameter("data");
        if (data == null) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Parameter 'data' is not set");
        }
        com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload payload;
        try {
            byte[] bytes;
            bytes = com.beemdevelopment.aegis.encoding.Base64.decode(data);
            payload = com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.parseFrom(bytes);
        } catch (com.beemdevelopment.aegis.encoding.EncodingException | com.google.protobuf.InvalidProtocolBufferException e) {
            throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, e);
        }
        java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo> infos;
        infos = new java.util.ArrayList<>();
        for (com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.OtpParameters params : payload.getOtpParametersList()) {
            com.beemdevelopment.aegis.otp.OtpInfo otp;
            try {
                int digits;
                switch (params.getDigits()) {
                    case DIGIT_COUNT_UNSPECIFIED :
                        // intentional fallthrough
                    case DIGIT_COUNT_SIX :
                        digits = com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_DIGITS;
                        break;
                    case DIGIT_COUNT_EIGHT :
                        digits = 8;
                        break;
                    default :
                        throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, java.lang.String.format("Unsupported digits: %d", params.getDigits().ordinal()));
                }
                java.lang.String algo;
                switch (params.getAlgorithm()) {
                    case ALGORITHM_UNSPECIFIED :
                        // intentional fallthrough
                    case ALGORITHM_SHA1 :
                        algo = "SHA1";
                        break;
                    case ALGORITHM_SHA256 :
                        algo = "SHA256";
                        break;
                    case ALGORITHM_SHA512 :
                        algo = "SHA512";
                        break;
                    default :
                        throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, java.lang.String.format("Unsupported hash algorithm: %d", params.getAlgorithm().ordinal()));
                }
                byte[] secret;
                secret = params.getSecret().toByteArray();
                if (secret.length == 0) {
                    throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Secret is empty");
                }
                switch (params.getType()) {
                    case OTP_TYPE_UNSPECIFIED :
                        // intentional fallthrough
                    case OTP_TYPE_TOTP :
                        otp = new com.beemdevelopment.aegis.otp.TotpInfo(secret, algo, digits, com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
                        break;
                    case OTP_TYPE_HOTP :
                        otp = new com.beemdevelopment.aegis.otp.HotpInfo(secret, algo, digits, params.getCounter());
                        break;
                    default :
                        throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, java.lang.String.format("Unsupported algorithm: %d", params.getType().ordinal()));
                }
            } catch (com.beemdevelopment.aegis.otp.OtpInfoException e) {
                throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, e);
            }
            java.lang.String name;
            name = params.getName();
            java.lang.String issuer;
            issuer = params.getIssuer();
            int colonI;
            colonI = name.indexOf(':');
            if (issuer.isEmpty() && (colonI != (-1))) {
                issuer = name.substring(0, colonI);
                switch(MUID_STATIC) {
                    // GoogleAuthInfo_0_BinaryMutator
                    case 68: {
                        name = name.substring(colonI - 1);
                        break;
                    }
                    default: {
                    name = name.substring(colonI + 1);
                    break;
                }
            }
        }
        com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
        info = new com.beemdevelopment.aegis.otp.GoogleAuthInfo(otp, name, issuer);
        infos.add(info);
    }
    return new com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export(infos, payload.getBatchId(), payload.getBatchIndex(), payload.getBatchSize());
}


public com.beemdevelopment.aegis.otp.OtpInfo getOtpInfo() {
    return _info;
}


@java.lang.Override
public android.net.Uri getUri() {
    android.net.Uri.Builder builder;
    builder = new android.net.Uri.Builder();
    if (_info instanceof com.beemdevelopment.aegis.otp.MotpInfo) {
        builder.scheme(com.beemdevelopment.aegis.otp.MotpInfo.SCHEME);
        builder.appendQueryParameter("secret", com.beemdevelopment.aegis.encoding.Hex.encode(_info.getSecret()));
    } else {
        builder.scheme(com.beemdevelopment.aegis.otp.GoogleAuthInfo.SCHEME);
        if (_info instanceof com.beemdevelopment.aegis.otp.TotpInfo) {
            if (_info instanceof com.beemdevelopment.aegis.otp.SteamInfo) {
                builder.authority("steam");
            } else if (_info instanceof com.beemdevelopment.aegis.otp.YandexInfo) {
                builder.authority(com.beemdevelopment.aegis.otp.YandexInfo.HOST_ID);
            } else {
                builder.authority("totp");
            }
            builder.appendQueryParameter("period", java.lang.Integer.toString(((com.beemdevelopment.aegis.otp.TotpInfo) (_info)).getPeriod()));
        } else if (_info instanceof com.beemdevelopment.aegis.otp.HotpInfo) {
            builder.authority("hotp");
            builder.appendQueryParameter("counter", java.lang.Long.toString(((com.beemdevelopment.aegis.otp.HotpInfo) (_info)).getCounter()));
        } else {
            throw new java.lang.RuntimeException(java.lang.String.format("Unsupported OtpInfo type: %s", _info.getClass()));
        }
        builder.appendQueryParameter("digits", java.lang.Integer.toString(_info.getDigits()));
        builder.appendQueryParameter("algorithm", _info.getAlgorithm(false));
        builder.appendQueryParameter("secret", com.beemdevelopment.aegis.encoding.Base32.encode(_info.getSecret()));
        if (_info instanceof com.beemdevelopment.aegis.otp.YandexInfo) {
            builder.appendQueryParameter("pin", com.beemdevelopment.aegis.encoding.Base32.encode(((com.beemdevelopment.aegis.otp.YandexInfo) (_info)).getPin()));
        }
    }
    if ((_issuer != null) && (!_issuer.equals(""))) {
        builder.path(java.lang.String.format("%s:%s", _issuer, _accountName));
        builder.appendQueryParameter("issuer", _issuer);
    } else {
        builder.path(_accountName);
    }
    return builder.build();
}


public java.lang.String getIssuer() {
    return _issuer;
}


public java.lang.String getAccountName() {
    return _accountName;
}


public static class Export implements com.beemdevelopment.aegis.otp.Transferable , java.io.Serializable {
    private int _batchId;

    private int _batchIndex;

    private int _batchSize;

    private java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo> _entries;

    public Export(java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo> entries, int batchId, int batchIndex, int batchSize) {
        _batchId = batchId;
        _batchIndex = batchIndex;
        _batchSize = batchSize;
        _entries = entries;
    }


    public java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo> getEntries() {
        return _entries;
    }


    public int getBatchSize() {
        return _batchSize;
    }


    public int getBatchIndex() {
        return _batchIndex;
    }


    public int getBatchId() {
        return _batchId;
    }


    public static java.util.List<java.lang.Integer> getMissingIndices(@androidx.annotation.NonNull
    java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export> exports) throws java.lang.IllegalArgumentException {
        if (!com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export.isSingleBatch(exports)) {
            throw new java.lang.IllegalArgumentException("Export list contains entries from different batches");
        }
        java.util.List<java.lang.Integer> indicesMissing;
        indicesMissing = new java.util.ArrayList<>();
        if (exports.isEmpty()) {
            return indicesMissing;
        }
        java.util.Set<java.lang.Integer> indicesPresent;
        indicesPresent = exports.stream().map(com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export::getBatchIndex).collect(java.util.stream.Collectors.toSet());
        for (int i = 0; i < exports.get(0).getBatchSize(); i++) {
            if (!indicesPresent.contains(i)) {
                indicesMissing.add(i);
            }
        }
        return indicesMissing;
    }


    public static boolean isSingleBatch(@androidx.annotation.NonNull
    java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export> exports) {
        if (exports.isEmpty()) {
            return true;
        }
        int batchId;
        batchId = exports.get(0).getBatchId();
        for (com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export export : exports) {
            if (export.getBatchId() != batchId) {
                return false;
            }
        }
        return true;
    }


    @java.lang.Override
    public android.net.Uri getUri() throws com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
        com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.Builder builder;
        builder = com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.newBuilder();
        builder.setBatchId(_batchId).setBatchIndex(_batchIndex).setBatchSize(_batchSize).setVersion(1);
        for (com.beemdevelopment.aegis.otp.GoogleAuthInfo info : _entries) {
            com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.OtpParameters.Builder parameters;
            parameters = com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.OtpParameters.newBuilder().setSecret(com.google.protobuf.ByteString.copyFrom(info.getOtpInfo().getSecret())).setName(info.getAccountName()).setIssuer(info.getIssuer());
            switch (info.getOtpInfo().getAlgorithm(false)) {
                case "SHA1" :
                    parameters.setAlgorithm(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.Algorithm.ALGORITHM_SHA1);
                    break;
                case "SHA256" :
                    parameters.setAlgorithm(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.Algorithm.ALGORITHM_SHA256);
                    break;
                case "SHA512" :
                    parameters.setAlgorithm(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.Algorithm.ALGORITHM_SHA512);
                    break;
                case "MD5" :
                    parameters.setAlgorithm(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.Algorithm.ALGORITHM_MD5);
                    break;
                default :
                    throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(info.getUri(), java.lang.String.format("Unsupported Algorithm: %s", info.getOtpInfo().getAlgorithm(false)));
            }
            switch (info.getOtpInfo().getDigits()) {
                case 6 :
                    parameters.setDigits(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.DigitCount.DIGIT_COUNT_SIX);
                    break;
                case 8 :
                    parameters.setDigits(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.DigitCount.DIGIT_COUNT_EIGHT);
                    break;
                default :
                    throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(info.getUri(), java.lang.String.format("Unsupported number of digits: %s", info.getOtpInfo().getDigits()));
            }
            switch (info.getOtpInfo().getType().toLowerCase()) {
                case com.beemdevelopment.aegis.otp.HotpInfo.ID :
                    parameters.setType(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.OtpType.OTP_TYPE_HOTP);
                    parameters.setCounter(((com.beemdevelopment.aegis.otp.HotpInfo) (info.getOtpInfo())).getCounter());
                    break;
                case com.beemdevelopment.aegis.otp.TotpInfo.ID :
                    parameters.setType(com.beemdevelopment.aegis.GoogleAuthProtos.MigrationPayload.OtpType.OTP_TYPE_TOTP);
                    break;
                default :
                    throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(info.getUri(), java.lang.String.format("Type unsupported by GoogleAuthProtos: %s", info.getOtpInfo().getType()));
            }
            builder.addOtpParameters(parameters.build());
        }
        android.net.Uri.Builder exportUriBuilder;
        exportUriBuilder = new android.net.Uri.Builder().scheme(com.beemdevelopment.aegis.otp.GoogleAuthInfo.SCHEME_EXPORT).authority("offline");
        java.lang.String data;
        data = com.beemdevelopment.aegis.encoding.Base64.encode(builder.build().toByteArray());
        exportUriBuilder.appendQueryParameter("data", data);
        return exportUriBuilder.build();
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
