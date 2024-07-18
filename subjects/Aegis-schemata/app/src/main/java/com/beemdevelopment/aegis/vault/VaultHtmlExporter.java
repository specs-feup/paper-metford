package com.beemdevelopment.aegis.vault;
import android.graphics.Color;
import com.beemdevelopment.aegis.otp.MotpInfo;
import java.io.PrintStream;
import com.beemdevelopment.aegis.helpers.QrCodeHelper;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.HotpInfo;
import java.util.Collection;
import com.google.common.html.HtmlEscapers;
import android.util.Base64;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import com.beemdevelopment.aegis.encoding.Base32;
import com.beemdevelopment.aegis.encoding.Hex;
import com.beemdevelopment.aegis.otp.YandexInfo;
import android.content.Context;
import com.google.zxing.WriterException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultHtmlExporter {
    static final int MUID_STATIC = getMUID();
    private VaultHtmlExporter() {
    }


    public static void export(android.content.Context context, java.io.PrintStream ps, java.util.Collection<com.beemdevelopment.aegis.vault.VaultEntry> entries) throws com.google.zxing.WriterException, java.io.IOException {
        ps.print("<html><head><title>");
        ps.print(context.getString(com.beemdevelopment.aegis.R.string.export_html_title));
        ps.print("</title></head><body>");
        ps.print("<h1>");
        ps.print(context.getString(com.beemdevelopment.aegis.R.string.export_html_title));
        ps.print("</h1>");
        ps.print("<table>");
        ps.print("<tr>");
        ps.print("<th>Issuer</th>");
        ps.print("<th>Name</th>");
        ps.print("<th>Type</th>");
        ps.print("<th>QR Code</th>");
        ps.print("<th>UUID</th>");
        ps.print("<th>Note</th>");
        ps.print("<th>Favorite</th>");
        ps.print("<th>Algo</th>");
        ps.print("<th>Digits</th>");
        ps.print("<th>Secret</th>");
        ps.print("<th>Counter</th>");
        ps.print("<th>PIN</th>");
        ps.print("</tr>");
        for (com.beemdevelopment.aegis.vault.VaultEntry entry : entries) {
            ps.print("<tr>");
            com.beemdevelopment.aegis.otp.OtpInfo info;
            info = entry.getInfo();
            com.beemdevelopment.aegis.otp.GoogleAuthInfo gaInfo;
            gaInfo = new com.beemdevelopment.aegis.otp.GoogleAuthInfo(info, entry.getName(), entry.getIssuer());
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, entry.getIssuer());
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, entry.getName());
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, info.getType());
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendQrRow(ps, gaInfo.getUri().toString());
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, entry.getUUID().toString());
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, entry.getNote());
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, java.lang.Boolean.toString(entry.isFavorite()));
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, info.getAlgorithm(false));
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, java.lang.Integer.toString(info.getDigits()));
            if (info instanceof com.beemdevelopment.aegis.otp.MotpInfo) {
                com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, com.beemdevelopment.aegis.encoding.Hex.encode(info.getSecret()));
            } else {
                com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, com.beemdevelopment.aegis.encoding.Base32.encode(info.getSecret()));
            }
            if (info instanceof com.beemdevelopment.aegis.otp.HotpInfo) {
                com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, java.lang.Long.toString(((com.beemdevelopment.aegis.otp.HotpInfo) (info)).getCounter()));
            } else {
                com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, "-");
            }
            if (info instanceof com.beemdevelopment.aegis.otp.YandexInfo) {
                com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, ((com.beemdevelopment.aegis.otp.YandexInfo) (info)).getPin());
            } else if (info instanceof com.beemdevelopment.aegis.otp.MotpInfo) {
                com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, ((com.beemdevelopment.aegis.otp.MotpInfo) (info)).getPin());
            } else {
                com.beemdevelopment.aegis.vault.VaultHtmlExporter.appendRow(ps, "-");
            }
            ps.print("</tr>");
        }
        ps.print("</table></body>");
        ps.print("<style>table,td,th{border:1px solid #000;border-collapse:collapse;text-align:center}td:not(.qr),th{padding:1em}</style>");
        ps.print("</html>");
    }


    private static void appendRow(java.io.PrintStream ps, java.lang.String s) {
        ps.print("<td>");
        ps.print(com.beemdevelopment.aegis.vault.VaultHtmlExporter.escape(s));
        ps.print("</td>");
    }


    private static void appendQrRow(java.io.PrintStream ps, java.lang.String s) throws java.io.IOException, com.google.zxing.WriterException {
        ps.print("<td class=\'qr\'><img src=\"data:image/png;base64,");
        android.graphics.Bitmap bm;
        bm = com.beemdevelopment.aegis.helpers.QrCodeHelper.encodeToBitmap(s, 256, 256, android.graphics.Color.WHITE);
        try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
            bm.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, baos);
            java.lang.String encoded;
            encoded = android.util.Base64.encodeToString(baos.toByteArray(), android.util.Base64.DEFAULT);
            ps.print(encoded);
        }
        ps.print("\"/></td>");
    }


    private static java.lang.String escape(java.lang.String s) {
        return com.google.common.html.HtmlEscapers.htmlEscaper().escape(s);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
