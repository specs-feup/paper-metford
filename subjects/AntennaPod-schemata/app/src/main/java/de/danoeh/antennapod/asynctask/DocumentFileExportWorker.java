package de.danoeh.antennapod.asynctask;
import de.danoeh.antennapod.core.export.ExportWriter;
import java.io.OutputStream;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.storage.DBReader;
import java.io.IOException;
import java.nio.charset.Charset;
import io.reactivex.Observable;
import java.io.OutputStreamWriter;
import android.net.Uri;
import androidx.documentfile.provider.DocumentFile;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Writes an OPML file into the user selected export directory in the background.
 */
public class DocumentFileExportWorker {
    static final int MUID_STATIC = getMUID();
    @androidx.annotation.NonNull
    private final de.danoeh.antennapod.core.export.ExportWriter exportWriter;

    @androidx.annotation.NonNull
    private android.content.Context context;

    @androidx.annotation.NonNull
    private android.net.Uri outputFileUri;

    public DocumentFileExportWorker(@androidx.annotation.NonNull
    de.danoeh.antennapod.core.export.ExportWriter exportWriter, @androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    android.net.Uri outputFileUri) {
        this.exportWriter = exportWriter;
        this.context = context;
        this.outputFileUri = outputFileUri;
    }


    public io.reactivex.Observable<androidx.documentfile.provider.DocumentFile> exportObservable() {
        androidx.documentfile.provider.DocumentFile output;
        output = androidx.documentfile.provider.DocumentFile.fromSingleUri(context, outputFileUri);
        return io.reactivex.Observable.create((io.reactivex.ObservableEmitter<androidx.documentfile.provider.DocumentFile> subscriber) -> {
            java.io.OutputStream outputStream;
            outputStream = null;
            java.io.OutputStreamWriter writer;
            writer = null;
            try {
                android.net.Uri uri;
                uri = output.getUri();
                outputStream = context.getContentResolver().openOutputStream(uri, "wt");
                if (outputStream == null) {
                    throw new java.io.IOException();
                }
                writer = new java.io.OutputStreamWriter(outputStream, java.nio.charset.Charset.forName("UTF-8"));
                exportWriter.writeDocument(de.danoeh.antennapod.core.storage.DBReader.getFeedList(), writer, context);
                subscriber.onNext(output);
            } catch (java.io.IOException e) {
                subscriber.onError(e);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (java.io.IOException e) {
                        subscriber.onError(e);
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (java.io.IOException e) {
                        subscriber.onError(e);
                    }
                }
                subscriber.onComplete();
            }
        });
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
